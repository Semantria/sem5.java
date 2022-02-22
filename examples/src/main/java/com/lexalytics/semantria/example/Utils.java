package com.lexalytics.semantria.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lexalytics.semantria.client.SemantriaClientConfiguration;
import com.lexalytics.semantria.client.dto.UserCredentials;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lexalytics.semantria.client.OptionHelper.getStringOption;
import static com.lexalytics.semantria.client.OptionHelper.hasAllOptions;

/**
 * Useful utility methods used in examples
 */
public class Utils {

    private static Logger log = LoggerFactory.getLogger(Utils.class);

    /**
     * Reads contents of {@code filename} and returns a list of lines.
     */
    public static List<String> readTextFile(String filename) {

        File file = new File(filename);
        if (!file.exists()) {
            log.error("Can't find data file: {}", filename);
            return Collections.emptyList();
        }

        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            log.error("Can't read data from file: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public static void sleep(float seconds) {
        try {
            Thread.sleep((int) (seconds * 1000));
        } catch (InterruptedException e) {
            // Restore the interrupted status
            Thread.currentThread().interrupt();
        }
    }

    public static String readVersion(Class cls) {
        return cls.getPackage().getImplementationVersion();
    }

    public static String getTextFromResource(Class cls, String path) throws IOException {
        ClassLoader classLoader = cls.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(path);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + path);
        }
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
            return buffer.lines().collect(Collectors.joining("\n"));
        }
    }

    public static void output(Object object) {
        output(object, true);
    }

    public static void output(Object object, boolean pretty) {
        prefixed(null, object, pretty);
    }

    public static void prefixed(String message, Object object, boolean pretty) {
        if (message != null) {
            System.out.print(message);
        }

        if (object instanceof String) {
            System.out.println((String) object);
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.setDateFormat(new StdDateFormat());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());

        if (pretty) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        try {
            System.out.println(mapper.writeValueAsString(object));
        } catch (JsonProcessingException e) {
            log.error("Can't print object {}. {}", e.getMessage(), object);
        }
    }

    public static SemantriaClientConfiguration generateConfiguration(Map<String, Object> options) throws IOException {
        SemantriaClientConfiguration config = SemantriaClientConfiguration.fromFile(getStringOption(options, "--config-file"));
        if (hasAllOptions(options, "--debug")) {
            config.setLogfile("http.log");
        }
        if (hasAllOptions(options, "--credentials", "--access-token")) {
            throw new CommandFailed("Cannot specify both " + "--credentials" + " and " + "--access-token");
        }
        if (hasAllOptions(options, "--credentials")) {
            UserCredentials userCredentials = parseCredentialsOption(getStringOption(options, "--credentials"));
            config.setCredentials(userCredentials);
            config.setAccessToken(null); // override anything in config
        }
        if (hasAllOptions(options, "--access-token")) {
            config.setAccessToken(getStringOption(options, "--access-token"));
            config.setCredentials(null); // override anything in config
        }
        if (hasAllOptions(options, "--api-endpoint")) {
            String endpoint = getStringOption(options, "--api-endpoint");
            if (! endpoint.startsWith("http")) {
                endpoint = "https://" + endpoint;
            }
            config.setApiEndpoint(endpoint);
        }
        if (! anyCredentialsSupplied(config)) {
            throw new CommandFailed("Missing credentials or access token. Use one of --config-file, --credentials, or --access-token");
        }
        return config;
    }

    private static UserCredentials parseCredentialsOption(String creds) {
        int idx = creds.indexOf(':');
        if (idx == -1) {
            throw new CommandFailed("--credentials" + " must be user:pass. Got " + creds);
        }
        String user = creds.substring(0, idx);
        String passwd = creds.substring(idx + 1);
        return new UserCredentials(user, passwd);
    }

    private static boolean anyCredentialsSupplied(SemantriaClientConfiguration config) {
        if (StringUtils.isNotEmpty(config.getAccessToken())) {
            return true;
        }
        if ((config.getCredentials() != null)
                && StringUtils.isNotEmpty(config.getCredentials().getLogin())
                && StringUtils.isNotEmpty(config.getCredentials().getPassword())) {
            return true;
        }
        return false;
    }

    public static class CommandFailed extends RuntimeException {
        public CommandFailed(String message) {
            super(message);
        }
    }

}
