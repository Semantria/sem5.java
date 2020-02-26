package com.lexalytics.semantria.example;

import ch.qos.logback.classic.Level;
import com.lexalytics.semantria.client.SemantriaClientConfiguration;
import com.lexalytics.semantria.client.SemantriaClientError;
import com.lexalytics.semantria.client.SemantriaSDK;
import com.lexalytics.semantria.client.dto.*;
import org.apache.commons.lang3.StringUtils;
import org.docopt.Docopt;
import org.docopt.DocoptExitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.lexalytics.semantria.client.OptionHelper.*;
import static com.lexalytics.semantria.example.Utils.*;

/**
 * Example application that shows using the Semantria API to run
 * detailed analysis on documents from a file.
 * <p>
 * Note that this is intended as a simple example of how to use the Java
 * SDK. It is <b>not</b> intended to show the best practice in building
 * a real application. Please contact Lexalytics for guidance if you
 * plan to build your own application.
 */
public class DetailedModeExample {

    private static Logger log = LoggerFactory.getLogger(DetailedModeExample.class);

    private Map<String, Object> options;
    private SemantriaSDK sdk;
    private SemantriaClientConfiguration config;
    private AccessToken tempToken = null;  // Only used if the user supplies username:password credentials

    // How long to wait between poll requests to Semantria API in seconds
    private static final float POLLING_INTERVAL = 0.5f;

    private int nDocsSent = 0;
    private List<DocumentResult> resultsReceived = new ArrayList<>();


    public static void main(String[] args) throws Exception {
        boolean success = run(Arrays.asList(args));
        if (!success) {
            System.exit(1);
        }
    }

    public static boolean run(List<String> args) throws IOException {
        String version = readVersion(DetailedModeExample.class);
        String doc = getTextFromResource(DetailedModeExample.class, "detailed-mode-help.txt");
        Map<String, Object> opts;
        try {
            opts = new Docopt(doc).withVersion(version).withHelp(true).withExit(true).parse(args);
        } catch (DocoptExitException ex) {
            System.err.println(doc);
            return false;
        }

        setLogLevelFromOptions(opts);

        List<String> data = Utils.readTextFile((String) opts.get("<file-or-text>"));
        if (data.isEmpty()) {
            System.err.format("Data file, %s, is empty or missing%n%nUsage:%n", opts.get("<file-or-text>"));
            System.err.println(doc);
            return false;
        }

        DetailedModeExample app = new DetailedModeExample(opts);
        boolean result = app.processDocs(data);

        app.deleteTempAccessToken();
        return result;
    }

    // control logging using command line options rather than a log config file
    private static void setLogLevelFromOptions(Map<String, Object> opts) {
        ch.qos.logback.classic.Logger logger
                = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.lexalytics.semantria.example");
        if ((boolean) opts.get("--debug")) {
            logger.setLevel(Level.DEBUG);
        } else if (hasAllOptions(opts, "--verbose")) {
            logger.setLevel(Level.INFO);
        } else {
            logger.setLevel(Level.WARN);
        }
    }

    public DetailedModeExample(Map<String, Object> opts) throws IOException {
        options = opts;
        config = generateConfiguration(opts);
        sdk = config.connect();
        if (StringUtils.isEmpty(config.getAccessToken())) {
            tempToken = sdk.createSession(config.getCredentials(), "custom", 5);
            log.debug("Created temp token: {}", tempToken.getAccessToken());
            config.setAccessToken(tempToken.getAccessToken());
            sdk = config.connect();  // Reconnect, now with access token
        }
    }

    private void deleteTempAccessToken() {
        // If we connected using username:password then delete the temp access token that we created.
        if (tempToken != null) {
            sdk.deleteSession(tempToken.getAccessToken());
            log.debug("Deleted temp token: {}", tempToken.getAccessToken());
            tempToken = null;
        }
    }

    boolean processDocs(List<String> data) {
        try {
            sendDocs(data);
            pollForResults();
            printResults();
            return true;
        } catch (SemantriaClientError e) {
            log.error("Error: {}", e.getReason());
            System.exit(1);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
            System.exit(1);
        }
        return false;
    }

    private void sendDocs(List<String> data) {
        System.out.format("Sending %d docs...%n", data.size());

        int batchSize = getIntOption(options, "--batch-size");
        List<Document> outgoingBatch = new ArrayList<>(batchSize);

        // Group docs into batchSize batches and send to Semantria
        for (String text : data) {
            String uid = UUID.randomUUID().toString();
            Document doc = new Document(uid, text);
            outgoingBatch.add(doc);
            if (outgoingBatch.size() == batchSize) {
                queueBatch(outgoingBatch);
            }
        }

        // Send last partial batch
        if (!outgoingBatch.isEmpty()) {
            queueBatch(outgoingBatch);
        }

        log.info("Sent {} docs", data.size());
    }

    private void pollForResults() {
        System.out.format("Retrieving processed results...%n");
        try {
            while (nDocsSent != resultsReceived.size()) {
                // Because Semantria isn't a real-time solution you should wait some time
                // before getting the processed results. A real application would likely be
                // implemented as two separate jobs, one for queuing source data another
                // one for retrieving results. Do not poll repeatedly without a wait. You
                // will exceed your polling limit.
                Utils.sleep(POLLING_INTERVAL);
                List<DocumentResult> processedDocs = getProcessedDocuments();
                log.debug("Received {} docs from polling", processedDocs.size());
                resultsReceived.addAll(processedDocs);
            }
        } catch (Exception e) {
            log.error("Error while polling for results: {}", e.getMessage());
        }
    }

    List<DocumentResult> getProcessedDocuments() {
        DocumentsRequest request = new DocumentsRequest();
        request.setUsing(getStringOption(options, "--using", null));
        request.setJobId(getStringOption(options, "--job-id", null));
        List<DocumentResult> result = sdk.getDocumentsBatch(request);
        log.debug("received {} docs, request: {}", result.size(), request);
        return result;
    }

    void queueBatch(List<Document> batch) {
        DocumentsRequest request = new DocumentsRequest();
        request.setUsing(getStringOption(options, "--using", null));
        request.setJobId(getStringOption(options, "--job-id", null));
        try {
            sdk.sendDocumentsBatch(batch, request);
        } catch (SemantriaClientError e) {
            log.error("Error sending docs. Status: {}, Reason: {}", e.getStatus(), e.getReason());
            throw e;
        }
        log.debug("sent {} docs, request: {}", batch.size(), request);
        nDocsSent += batch.size();
        log.info("{} documents queued successfully", batch.size());
        batch.clear();
    }

    private void printResults() {
        // There is a huge amount of info that you can get from your analysis.
        // This is just a small selection.
        System.out.println();
        for (DocumentResult doc : resultsReceived) {
            System.out.format("---- Document %s -----%n"
                            + "Sentiment polarity: %s%n"
                            + "Sentiment score: %.2f%n",
                    doc.getId(), doc.getSentimentPolarity(), doc.getScore());
            showMetaData(doc);
            showEntities(doc);
            showQueryTopics(doc);
            showThemes(doc);
            showAutoCategories(doc);
            System.out.println();
        }
    }

    private void showEntities(DocumentResult doc) {
        if (doc.getEntities() == null) {
            return;
        }
        System.out.println("Entities:");
        for (Entity entity : doc.getEntities()) {
            System.out.format("    %s\t  sentiment polarity: %s, sentiment: %.2f%n",
                    entity.getNormalized(), entity.getSentimentPolarity(), entity.getSentimentScore());
        }
    }

    private void showThemes(DocumentResult doc) {
        if (doc.getThemes() == null) {
            return;
        }
        System.out.println("Document themes:");
        for (Theme theme : doc.getThemes()) {
            System.out.format("    %s\t  normalized: %s, stemmed: %s, sentiment: %.2f%n",
                    theme.getTitle(), theme.getNormalized(), theme.getStemmed(), theme.getSentimentScore());
        }
    }

    private void showQueryTopics(DocumentResult doc) {
        if (doc.getTopics() == null) {
            return;
        }
        System.out.println("Query topics:");
        for (Topic topic : doc.getTopics()) {
            System.out.format("    %s\t  Sentiment score: %.2f%n",
                    topic.getTitle(), topic.getSentimentScore());
            if ((topic.getMentions() != null) && !topic.getMentions().isEmpty()) {
                System.out.format("        Mentions:%n");
                for (Mention mention : topic.getMentions()) {
                    System.out.format("            %s    locations: %s%n",
                            mention.getLabel(),
                            mention.getLocations().stream()
                                    .map(l -> Integer.toString(l.getCharOffset()))
                                    .collect(Collectors.joining(", ")));
                }
            }
        }
    }

    private void showAutoCategories(DocumentResult doc) {
        if (doc.getAutoCategories() != null) {
            System.out.println("Document categories:");
            for (Topic category : doc.getAutoCategories()) {
                System.out.format("    %s\t  Strength score: %.2f%n",
                        category.getTitle(), category.getStrengthScore());
            }
        }
    }

    private void showMetaData(DocumentResult doc) {
        if (doc.getMetadata() != null) {
            System.out.format("Metadata: %s%n", doc.getMetadata().toString());
        }
    }

}
