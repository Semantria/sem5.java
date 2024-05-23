package com.lexalytics.semantria;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lexalytics.semantria.client.*;
import com.lexalytics.semantria.client.dto.Collection;
import com.lexalytics.semantria.client.dto.*;
import org.docopt.Docopt;
import org.docopt.DocoptExitException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.lexalytics.semantria.client.OptionHelper.*;

public class CommandLineInterface {
	private static ObjectMapper mapper = new ObjectMapper();
	private Map<String, Object> cmdOptions;
	private boolean verbose;
	private SemantriaSDK sdk;
	private SemantriaClientConfiguration config;
	private boolean shouldCleanupAuthToken = false;

	public CommandLineInterface(Map<String, Object> opts) throws IOException {
		this.cmdOptions = opts;
		verbose = hasAllOptions(cmdOptions, "--verbose");
		generateConfiguration();
	}

	public static void main(String[] args) throws Exception {
		boolean success = run(Arrays.asList(args));
		if (!success) {
			System.exit(1);
		}
	}

	public static boolean run(List<String> args) throws Exception {
		String version = readVersion();
		String doc = getTextFromResource("help.txt");
		Map<String, Object> opts;
		try {
			opts = new Docopt(doc).withVersion(version).withHelp(true).withExit(true).parse(args);
		} catch (DocoptExitException ex) {
			System.out.println(doc);
			return false;
		}

		if ((boolean) opts.get("--debugcli")) {
			output(opts);
			return true;
		}

		CommandLineInterface cli = new CommandLineInterface(opts);
		return cli.run();
	}

	private static void output(Object object) {
		output(object, true);
	}

	private static void output(Object object, boolean pretty) {
		prefixed(null, object, pretty);
	}

	private static void prefixed(String message, Object object, boolean pretty) {
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
			mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty-print
		}

		try {
			System.out.println(mapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private static String readVersion() {
		Class<CommandLineInterface> klass = CommandLineInterface.class;
		return klass.getPackage().getImplementationVersion();
	}

	private static String getTextFromResource(String path) throws IOException {
		ClassLoader classLoader = CommandLineInterface.class.getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(path);
		if (inputStream == null) {
			throw new IOException("Not found: " + path);
		}
		try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
			return buffer.lines().collect(Collectors.joining("\n"));
		}
	}

	private boolean run() {
        if (hasAllOptions(cmdOptions, "searchResultsGenerator-config")) {
            output(config);
            return true;
        }

        Dispatcher<Map<String, Object>> dispatcher = new Dispatcher<>();
        dispatcher.add(this::accountsAccountPasswordRulesGet, "account", "password-rules");
        dispatcher.add(this::accountsAccountDetailsGet, "account", "details");
        dispatcher.add(this::accountsPasswordRulesGet, "password-rules", "<rule_id>");
        dispatcher.add(this::accountsPasswordRulesTypesGet, "password-rules-types");
        dispatcher.add(this::accountsLimitValuesGet, "limit-values");
        dispatcher.add(this::accountsLimitValueGet, "limit-values", "<limit_type>");
        dispatcher.add(this::accountsBalanceValuesGet, "balance-values");
        dispatcher.add(this::accountsBalanceValueGet, "balance-values", "<balance_type>");
        dispatcher.add(this::accountsBalanceRefreshesGet, "balance-refresh");
        dispatcher.add(this::accountsBalanceRefreshGet, "balance-refresh", "<balance_type>");
        dispatcher.add(this::authUsersSessionsClear, "auth", "users", "<user_id>", "clear");
        dispatcher.add(this::authUsersSessionsGet, "auth", "users", "<user_id>");
        dispatcher.add(this::authSessionDelete, "auth", "sessions", "<session_id>", "delete");
        dispatcher.add(this::authSessionGet, "auth", "sessions", "<session_id>");
        dispatcher.add(this::authSessionCreate, "auth", "sessions", "create");
        dispatcher.add(this::authSessionsList, "auth", "sessions");
        dispatcher.add(this::authSessionRenew, "renew-session");
        dispatcher.add(this::configsConfigurationList, "get-configs");
        dispatcher.add(this::configsConfigurationListFromGroup, "get-configs-from-group", "<group_id>");
		dispatcher.add(this::configsNlpFeatureCreate,    "configs", "<configuration_id>", "<feature_name>", "create");
		dispatcher.add(this::configsNlpFeatureDelete,    "configs", "<configuration_id>", "<feature_name>", "<feature_id>", "delete");
		dispatcher.add(this::configsNlpFeatureUpdate,    "configs", "<configuration_id>", "<feature_name>", "<feature_id>", "update");
		dispatcher.add(this::configsNlpFeatureGet,       "configs", "<configuration_id>", "<feature_name>", "<feature_id>");
		dispatcher.add(this::configsNlpFeatureList,      "configs", "<configuration_id>", "<feature_name>");
		dispatcher.add(this::configsConfigurationDelete, "configs", "<configuration_id>", "delete");
        dispatcher.add(this::configsConfigurationUpdate, "configs", "<configuration_id>", "update");
        dispatcher.add(this::configsConfigurationGetInfo, "configs", "<configuration_id>", "info");
        dispatcher.add(this::configsConfigurationGet, "configs", "<configuration_id>");
        dispatcher.add(this::configsConfigurationCreateInGroup, "configs", "<group_id>", "create-in-group");
		dispatcher.add(this::configsConfigurationCreate, "configs", "create");
        dispatcher.add(this::configsConfigurationSetTags, "config-tags", "<configuration_id>", "<tags>", "set");
        dispatcher.add(this::configsConfigurationAddTags, "config-tags", "<configuration_id>", "<tags>", "add");
        dispatcher.add(this::configsConfigurationRemoveTags, "config-tags", "<configuration_id>", "<tags>", "remove");
        dispatcher.add(this::configsConfigurationRemoveAllTags, "config-tags", "<configuration_id>", "remove-all");
        dispatcher.add(this::configsRoutesCreate,   "config-routes", "create");
        dispatcher.add(this::configsRoutesCreate,   "config-routes", "<group_id>", "create-in-group");
        dispatcher.add(this::configsRoutesUpdate,   "config-routes", "<route_id>", "update");
        dispatcher.add(this::configsRoutesDelete,   "config-routes", "<route_id>", "delete");
        dispatcher.add(this::configsRoutesAddConfigsTo, "config-routes", "<route_id>", "<configs>", "add-configs-to");
        dispatcher.add(this::configsRoutesRemoveConfigsFrom,    "config-routes", "<route_id>", "<configs>", "remove-configs-from");
        dispatcher.add(this::configsRoutesGet,  "config-routes", "<route_id>");
        dispatcher.add(this::configsRoutesGetAll,   "config-routes", "group", "<group_id>");
        dispatcher.add(this::configsIndustryPacksGet, "industry-packs");
        dispatcher.add(this::configsLanguageTemplatesGet, "language-templates");
		dispatcher.add(this::docsDocumentsSend, "docs", "send");
		dispatcher.add(this::docsDocumentsReceive, "docs", "receive");
		dispatcher.add(this::docsCollectionsSend, "collections", "send");
		dispatcher.add(this::docsCollectionsReceive, "collections", "receive");
		dispatcher.add(this::featuresAllFeaturesGet, "features", "all-features");
		dispatcher.add(this::featuresConfigurationSettingsGet, "features", "configuration-settings");
		dispatcher.add(this::featuresLimitTypesGet, "features", "limit-types");
		dispatcher.add(this::featuresLanguagesGet, "features", "languages");
		dispatcher.add(this::statsCustomStatisticsGet, "stats");
		dispatcher.add(this::usersGroupPermissionsDelete, "group-permissions", "group", "<group_id>", "permission", "<permission_id>", "delete");
		dispatcher.add(this::usersGroupPermissionsCreate, "group-permissions", "group", "<group_id>", "create");
		dispatcher.add(this::usersGroupPermissionsGet, "group-permissions", "group", "<group_id>");
		dispatcher.add(this::usersUserPermissionsDelete, "user-permissions", "user", "<user_id>", "permission", "<permission_id>", "delete");
		dispatcher.add(this::usersUserPermissionsCreate, "user-permissions", "user", "<user_id>", "create");
		dispatcher.add(this::usersUserPermissionsGet, "user-permissions", "user", "<user_id>");
		dispatcher.add(this::usersAllPermissionsGet, "all-permissions");
		dispatcher.add(this::usersGroupsUserDelete, "groups", "<group_id>", "members", "<user_ids>", "delete");
		dispatcher.add(this::usersGroupsUserAssign, "groups", "<group_id>", "members", "<user_ids>", "assign");
		dispatcher.add(this::usersGroupsUsersGet, "groups", "<group_id>", "members");
		dispatcher.add(this::usersGroupsUpdate,  "groups", "<group_id>", "update");
		dispatcher.add(this::usersGroupsDelete, "groups", "<group_id>", "delete");
		dispatcher.add(this::usersGroupsGet, "groups", "<group_id>");
		dispatcher.add(this::usersGroupsCreate, "groups", "create");
		dispatcher.add(this::usersAllGroupsGet, "all-groups");
		dispatcher.add(this::usersUserPasswordUpdate, "user", "<user_id>", "password", "update");
		dispatcher.add(this::usersUsersUpdate, "user", "<user_id>", "update");
		dispatcher.add(this::usersUsersDelete, "user", "<user_id>", "delete");
		dispatcher.add(this::usersUsersGet, "user", "<user_id>");
		dispatcher.add(this::usersUsersCreate, "user", "create");
		dispatcher.add(this::usersAllUsersGet, "all-users");
		dispatcher.add(this::dumpConfig, "generate-config");
		dispatcher.add(this::health, "health");

		try {
			sdk = config.connect();
			dispatcher.dispatch(this.cmdOptions, cmdOptions);
			return true;
		} catch (CommandFailed e) {
			error(e.getMessage());
		} catch (SemantriaClientError e) {
			error(e.getMessage() + " with status " + e.getStatus() + "\n" + e.getReason());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cleanupAuth();
		}
		return false;
	}

	private void health(Map<String, Object> cmdOptions) {
		throw new CommandFailed("Not implemented");
	}

	private void dumpConfig(Map<String, Object> cmdOptions) {
		output(config);
	}

	private void accountsHelp(Map<String, Object> cmdOptions) {
		doDocumentation("accounts");
	}

	private void accountsAccountPasswordRulesGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		output(sdk.getAccountPasswordRules());
	}

	private void accountsAccountDetailsGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		output(sdk.getAccountDetails());
	}

    private void accountsPasswordRulesGet(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String ruleId = getStringOption(cmdOptions, "<rule_id>");
        output(sdk.getPasswordRules(ruleId));
    }

    private void accountsPasswordRulesTypesGet(Map<String, Object> cmdOptions) {
        connectWithAuth();
        output(sdk.getPasswordRuleTypes());
    }

    private void accountsLimitValuesGet(Map<String, Object> cmdOptions) {
        connectWithAuth();
        output(sdk.getAccountLimitValues());
    }

    private void accountsLimitValueGet(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String limitType = getStringOption(cmdOptions, "<limit_type>");
        output(sdk.getAccountLimitValue(limitType));
    }

    private void accountsBalanceValuesGet(Map<String, Object> cmdOptions) {
        connectWithAuth();
        output(sdk.getAccountBalanceValues());
    }

    private void accountsBalanceValueGet(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String balanceType = getStringOption(cmdOptions, "<balance_type>");
        output(sdk.getAccountBalanceValue(balanceType));
    }

    private void accountsBalanceRefreshesGet(Map<String, Object> cmdOptions) {
        connectWithAuth();
        output(sdk.getAccountBalanceRefreshes());
    }

    private void accountsBalanceRefreshGet(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String balanceType = getStringOption(cmdOptions, "<balance_type>");
        output(sdk.getAccountBalanceRefresh(balanceType));
    }

    private void authHelp(Map<String, Object> cmdOptions) {
        doDocumentation("auth");
    }

    private void authSessionCreate(Map<String, Object> cmdOptions) {
        UserCredentials userCreds = getUserCredentials();
        String policy = getStringOption(cmdOptions, "--expiration");
        String renwalType = getStringOption(cmdOptions, "--renewal-type");
        String notes = getStringOption(cmdOptions, "--notes");
        int expirationMinutes = ((policy.toLowerCase().contentEquals("custom"))
                ? getIntOption(cmdOptions, "--expire-after-minutes")
                : 0);
        output(sdk.createSession(userCreds, policy, expirationMinutes, renwalType, notes));
	}

	private void authSessionRenew(Map<String, Object> cmdOptions) {
	    connectWithAuth();
	    output(sdk.renewSession());
    }

	private void authSessionsList(Map<String, Object> cmdOptions) {
		connectWithAuth();
        output(sdk.getSessions());
	}

	private void authSessionGet(Map<String, Object> cmdOptions) {
		connectWithAuth();
		String sessionId = getStringOption(cmdOptions, "<session_id>");
		output(sdk.getSession(sessionId));
	}

	private void authSessionDelete(Map<String, Object> cmdOptions) {
		connectWithAuth();
		String sessionId = getStringOption(cmdOptions, "<session_id>");
		sdk.deleteSession(sessionId);
	}

	private void authUsersSessionsGet(Map<String, Object> cmdOptions) {
		connectWithAuth();
		String userId = getStringOption(cmdOptions, "<user_id>");
		output(sdk.getUserSessions(userId));
	}

	private void authUsersSessionsClear(Map<String, Object> cmdOptions) {
		connectWithAuth();
		String userId = getStringOption(cmdOptions, "<user_id>");
		AllSessionInfo response = sdk.getUserSessions(userId);
		List<String> sessions = response.getUserSessions().get(userId);
		verbose("There are " + response.getLiveSessionCount() + " live sessions");
		verbose("Deleting...");
		for (String session : sessions) {
			if (session.contentEquals(config.getAccessToken())) {
				continue;
			}
			verbose("  " + session);
			sdk.deleteSession(session);
		}
	}

	private void configsHelp(Map<String, Object> cmdOptions) {
		doDocumentation("configs");
	}

    private void configsConfigurationList(Map<String, Object> cmdOptions) {
        connectWithAuth();
        output(sdk.getAllConfigurations(""));
    }

    private void configsConfigurationListFromGroup(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String groupId = getStringOption(cmdOptions, "<group_id>");
        output(sdk.getAllConfigurations(groupId));
    }

    private void configsConfigurationGetInfo(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String configurationId = getStringOption(cmdOptions, "<configuration_id>");
        output(sdk.getConfigurationInfo(configurationId));
    }

    private void configsConfigurationGet(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String configurationId = getStringOption(cmdOptions, "<configuration_id>");
        output(sdk.getConfiguration(configurationId));
    }

    private void configsConfigurationCreateInGroup(Map<String, Object> cmdOptions) {
        connectWithAuth();
        Map<String, Object> item = getDataFromOptions();
        String groupId = getStringOption(cmdOptions, "<group_id>");
        output(sdk.createConfiguration(groupId, item));
    }

	private void configsConfigurationCreate(Map<String, Object> cmdOptions) {
		connectWithAuth();
        Map<String,Object> item = getDataFromOptions();
		output(sdk.createConfiguration("", item));
	}

    private void configsConfigurationAddTags(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String configurationId = getStringOption(cmdOptions, "<configuration_id>");
        String tagString = getStringOption(cmdOptions, "<tags>");
        List<String> tags = Arrays.asList(tagString.split(","));
        output(sdk.addConfigTags(configurationId, tags));
    }

    private void configsConfigurationSetTags(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String configurationId = getStringOption(cmdOptions, "<configuration_id>");
        String tagString = getStringOption(cmdOptions, "<tags>");
        List<String> tags = Arrays.asList(tagString.split(","));
        output(sdk.setConfigTags(configurationId, tags));
    }

    private void configsConfigurationRemoveTags(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String configurationId = getStringOption(cmdOptions, "<configuration_id>");
        String tagString = getStringOption(cmdOptions, "<tags>");
        List<String> tags = Arrays.asList(tagString.split(","));
        output(sdk.removeConfigTags(configurationId, tags));
    }

    private void configsConfigurationRemoveAllTags(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String configurationId = getStringOption(cmdOptions, "<configuration_id>");
        output(sdk.removeAllConfigTags(configurationId));
    }

	private void configsConfigurationUpdate(Map<String, Object> cmdOptions) {
		connectWithAuth();
		String configurationId = getStringOption(cmdOptions, "<configuration_id>");
		Map<String, Object> item = getDataFromOptions();
		output(sdk.updateConfiguration(configurationId, item));
	}

	private void configsConfigurationDelete(Map<String, Object> cmdOptions) {
		connectWithAuth();
		String configurationId = getStringOption(cmdOptions, "<configuration_id>");
		sdk.deleteConfiguration(configurationId);
	}

    private void configsRoutesGet(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String routeId = getStringOption(cmdOptions, "<route_id>");
        output(sdk.getConfigurationRoute(routeId));
    }

    private void configsRoutesGetAll(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String groupId = getStringOption(cmdOptions, "<group_id>");
        output(sdk.getConfigurationRoutes(groupId));
    }

    private void configsRoutesCreate(Map<String, Object> cmdOptions) {
        connectWithAuth();
        Map<String,Object> item = getDataFromOptions();
        output(sdk.configsRoutesCreate("", item));
    }

    private void configsRoutesCreateInGroup(Map<String, Object> cmdOptions) {
        connectWithAuth();
        Map<String,Object> item = getDataFromOptions();
        String groupId = getStringOption(cmdOptions, "<group_id>");
        output(sdk.configsRoutesCreate(groupId, item));
    }

    private void configsRoutesDelete(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String routeId = getStringOption(cmdOptions, "<route_id>");
        sdk.deleteConfigurationRoute(routeId);
    }

    private void configsRoutesUpdate(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String routeId = getStringOption(cmdOptions, "<route_id>");
        Map<String, Object> item = getDataFromOptions();
        output(sdk.updateConfigurationRoute(routeId, item));
    }

    private void configsRoutesAddConfigsTo(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String routeId = getStringOption(cmdOptions, "<route_id>");
        String configsString = getStringOption(cmdOptions, "<configs>");
        List<String> configs = Arrays.asList(configsString.split(","));
        output(sdk.addConfigsToRoute(routeId, configs));
    }

    private void configsRoutesRemoveConfigsFrom(Map<String, Object> cmdOptions) {
        connectWithAuth();
        String routeId = getStringOption(cmdOptions, "<route_id>");
        String configsString = getStringOption(cmdOptions, "<configs>");
        List<String> configs = Arrays.asList(configsString.split(","));
        output(sdk.removeConfigsFromRoute(routeId, configs));
    }

    private void configsNlpFeatureList(Map<String, Object> cmdOptions) {
		connectWithAuth();
		String configId = getStringOption(cmdOptions, "<configuration_id>");
		String featureName = getStringOption(cmdOptions, "<feature_name>");
		switch(featureName) {
			case "query-topics":
				new Lister<QueryTopic>().go(
						(pageNumber) -> sdk.listConfigurationQueryTopics(configId, pageNumber),
						CommandLineInterface::output
				);
				break;

			case "concept-topics":
				new Lister<ConceptTopic>().go(
						(pageNumber) -> sdk.listConfigurationConceptTopics(configId, pageNumber),
						CommandLineInterface::output
				);
				break;

			case "blacklist":
				new Lister<BlacklistItem>().go(
						(pageNumber) -> sdk.listConfigurationBlacklists(configId, pageNumber),
						CommandLineInterface::output
				);
				break;

			case "sentiment-phrases":
				new Lister<SentimentPhrase>().go(
						(pageNumber) -> sdk.listConfigurationSentimentPhrases(configId, pageNumber),
						CommandLineInterface::output
				);
				break;

			case "user-entities":
				new Lister<UserEntity>().go(
						(pageNumber) -> sdk.listConfigurationUserEntities(configId, pageNumber),
						CommandLineInterface::output
				);
				break;

			case "taxonomy":
				new Lister<TaxonomyNode>().go(
						(pageNumber) -> sdk.listConfigurationTaxonomies(configId, pageNumber),
						CommandLineInterface::output
				);
				break;

			default:
				throw new CommandFailed("Unknown feature name: " + featureName);
		}
	}

	private void configsNlpFeatureDelete(Map<String, Object> cmdOptions) {
		connectWithAuth();
		String configurationId = getStringOption(cmdOptions, "<configuration_id>");
		String featureName = getStringOption(cmdOptions, "<feature_name>");
		String featureId = getStringOption(cmdOptions, "<feature_id>");
		switch(featureName) {
			case "query-topics":
				sdk.deleteConfigurationQueryTopic(configurationId, featureId);
				break;

			case "concept-topics":
				sdk.deleteConfigurationConceptTopic(configurationId, featureId);
				break;

			case "blacklist":
				sdk.deleteConfigurationBlacklist(configurationId, featureId);
				break;

			case "sentiment-phrases":
				sdk.deleteConfigurationSentimentPhrase(configurationId, featureId);
				break;

			case "user-entities":
				sdk.deleteConfigurationUserEntity(configurationId, featureId);
				break;

			case "taxonomy":
				sdk.deleteConfigurationTaxonomy(configurationId, featureId);
				break;

			default:
				throw new CommandFailed("Unknown feature name: " + featureName);
		}
	}

	private void configsNlpFeatureGet(Map<String, Object> cmdOptions) {
		connectWithAuth();
		String configurationId = getStringOption(cmdOptions, "<configuration_id>");
		String featureName = getStringOption(cmdOptions, "<feature_name>");
		String featureId = getStringOption(cmdOptions, "<feature_id>");
		Object data;
		switch(featureName) {
			case "query-topics":
				data = sdk.getConfigurationQueryTopic(configurationId, featureId);
				break;

			case "concept-topics":
				data = sdk.getConfigurationConceptTopic(configurationId, featureId);
				break;

			case "blacklist":
				data = sdk.getConfigurationBlacklist(configurationId, featureId);
				break;

			case "sentiment-phrases":
				data = sdk.getConfigurationSentimentPhrase(configurationId, featureId);
				break;

			case "user-entities":
				data = sdk.getConfigurationUserEntity(configurationId, featureId);
				break;

			case "taxonomy":
				data = sdk.getConfigurationTaxonomy(configurationId, featureId);
				break;

			default:
				throw new CommandFailed("Unknown feature name: " + featureName);
		}
		output(data);
	}

	private void configsNlpFeatureUpdate(Map<String, Object> cmdOptions) {
		connectWithAuth();
		String configurationId = getStringOption(cmdOptions, "<configuration_id>");
		String featureName = getStringOption(cmdOptions, "<feature_name>");
		String featureId = getStringOption(cmdOptions, "<feature_id>");
		String inputData = getDataOption();
		Object data;
		switch(featureName) {
			case "query-topics":
				data = sdk.updateConfigurationQueryTopic(configurationId, featureId,
						new DataConverter<QueryTopic>().convert(inputData, QueryTopic.class));
				break;

			case "concept-topics":
				data = sdk.updateConfigurationConceptTopic(configurationId, featureId,
						new DataConverter<ConceptTopic>().convert(inputData, ConceptTopic.class));
				break;

			case "blacklist":
				data = sdk.updateConfigurationBlacklist(configurationId, featureId,
						new DataConverter<BlacklistItem>().convert(inputData, BlacklistItem.class));
				break;

			case "sentiment-phrases":
				data = sdk.updateConfigurationSentimentPhrase(configurationId, featureId,
						new DataConverter<SentimentPhrase>().convert(inputData, SentimentPhrase.class));
				break;

			case "user-entities":
				data = sdk.updateConfigurationUserEntity(configurationId, featureId,
						new DataConverter<UserEntity>().convert(inputData, UserEntity.class));
				break;

			case "taxonomy":
				data = sdk.updateConfigurationTaxonomy(configurationId, featureId,
						new DataConverter<TaxonomyNode>().convert(inputData, TaxonomyNode.class));
				break;

			default:
				throw new CommandFailed("Unknown feature name: " + featureName);
		}
		output(data);
	}

	private void configsNlpFeatureCreate(Map<String, Object> cmdOptions) {
		connectWithAuth();
		String configurationId = getStringOption(cmdOptions, "<configuration_id>");
		String featureName = getStringOption(cmdOptions, "<feature_name>");
		String inputData = getDataOption();
		Object data;
		switch(featureName) {
			case "query-topics":
				data = sdk.createConfigurationQueryTopic(configurationId,
						new DataConverter<QueryTopic>().convert(inputData, QueryTopic.class));
				break;

			case "concept-topics":
				data = sdk.createConfigurationConceptTopic(configurationId,
						new DataConverter<ConceptTopic>().convert(inputData, ConceptTopic.class));
				break;

			case "blacklist":
				data = sdk.createConfigurationBlacklist(configurationId,
						new DataConverter<BlacklistItem>().convert(inputData, BlacklistItem.class));
				break;

			case "sentiment-phrases":
				data = sdk.createConfigurationSentimentPhrase(configurationId,
						new DataConverter<SentimentPhrase>().convert(inputData, SentimentPhrase.class));
				break;

			case "user-entities":
				data = sdk.createConfigurationUserEntity(configurationId,
						new DataConverter<UserEntity>().convert(inputData, UserEntity.class));
				break;

			case "taxonomy":
				data = sdk.createConfigurationTaxonomy(configurationId,
						new DataConverter<TaxonomyNode>().convert(inputData, TaxonomyNode.class));
				break;

			default:
				throw new CommandFailed("Unknown feature name: " + featureName);
		}
		output(data);
	}

    private void configsIndustryPacksGet(Map<String, Object> cmdOptions){
        connectWithAuth();
        output(sdk.getIndustryPacks());
    }

    private void configsLanguageTemplatesGet(Map<String, Object> cmdOptions){
        connectWithAuth();
        output(sdk.getLanguageTemplates());
    }

	private void docsHelp(Map<String, Object> cmdOptions) {
		doDocumentation("docs");
	}

	private void docsDocumentsSend(Map<String, Object> cmdOptions) {
	    connectWithAuth();
	    List<Document> documents = getDocumentsFromOptions();
		DocumentsRequest docRequest = new DocumentsRequest();
		if (hasAllOptions(cmdOptions, "--configuration-id")) {
			String configuration = getStringOption(cmdOptions, "--configuration-id");
			docRequest.setUsing(configuration);
		}
		if (hasAllOptions(cmdOptions, "--job-id")) {
			docRequest.setJobId(getStringOption(cmdOptions, "--job-id"));
		}
		if (hasAllOptions(cmdOptions, "--request-limit")) {
			docRequest.setRequestLimit(getIntOption(cmdOptions, "--request-limit"));
		}
	    output(sdk.sendDocumentsBatch(documents, docRequest));
	}

	private void docsDocumentsReceive(Map<String, Object> cmdOptions){
		connectWithAuth();
		DocumentsRequest docRequest = new DocumentsRequest();
		if (hasAllOptions(cmdOptions, "--configuration-id")) {
			docRequest.setUsing(getStringOption(cmdOptions, "--configuration-id"));
		}
		if (hasAllOptions(cmdOptions, "--job-id")) {
			docRequest.setJobId(getStringOption(cmdOptions, "--job-id"));
		}
		if (hasAllOptions(cmdOptions, "--request-limit")) {
			docRequest.setRequestLimit(getIntOption(cmdOptions, "--request-limit"));
		}

		boolean foundSomething = false;
		while(true){
			List<DocumentResult> response = sdk.getDocumentsBatch(docRequest);
			if(response.size() > 0){
				output(response);
				foundSomething = true;
			}
			else if (foundSomething) {
				break;
			}
		}
	}

	private void docsCollectionsSend(Map<String, Object> cmdOptions) {
		connectWithAuth();
		List<String> documents = getCollectionTextFromOptions();
		Collection collection = new Collection();
		collection.setDocuments(documents);
		CollectionsRequest request = new CollectionsRequest();
		if (hasAllOptions(cmdOptions, "--configuration-id")) {
			String configuration = getStringOption(cmdOptions, "--configuration-id");
			request.setUsing(configuration);
		}
		if (hasAllOptions(cmdOptions, "--job-id")) {
			request.setJobId(getStringOption(cmdOptions, "--job-id"));
		}
		output(sdk.sendCollections(collection, request));
	}

	private void docsCollectionsReceive(Map<String, Object> cmdOptions){
		connectWithAuth();
		boolean flag = false;
		CollectionsRequest request = new CollectionsRequest();
		if (hasAllOptions(cmdOptions, "--configuration-id")) {
			request.setUsing(getStringOption(cmdOptions, "--configuration-id"));
		}
		if (hasAllOptions(cmdOptions, "--job-id")) {
			request.setJobId(getStringOption(cmdOptions, "--job-id"));
		}

		while(!flag){
			output("Retreiving collection...");
			JsonNode response = sdk.getCollections(request);
			if(response.size() > 0){
				output(response);
				flag = true;
			}
		}
	}

	private void featuresAllFeaturesGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		output(sdk.getAllFeatures());
	}

	private void featuresConfigurationSettingsGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		output(sdk.getConfigurationSettings());
	}

	private void featuresLimitTypesGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		output(sdk.getLimitTypes());
	}

	private void featuresLanguagesGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		output(sdk.getLanguages());
	}

	private void statsHelp(Map<String, Object> cmdOptions) {
		doDocumentation("stats");
	}

	private void statsCustomStatisticsGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		StatisticsRequest statsRequest = getStatisticsRequestForOptions();
		output(sdk.getCustomStatistics(statsRequest));
	}

	private void usersHelp(Map<String, Object> cmdOptions) {
		doDocumentation("users");
	}

	private void usersGroupPermissionsDelete(Map<String, Object> cmdOptions){
		connectWithAuth();
		String groupId = getStringOption(cmdOptions, "<group_id>");
		String permissionId = getStringOption(cmdOptions, "<permission_id>");
		sdk.deleteAccountGroupPermission(groupId, permissionId);
	}

	private void usersGroupPermissionsCreate(Map<String, Object> cmdOptions){
		connectWithAuth();
		String groupId = getStringOption(cmdOptions, "<group_id>");
		Map<String, Object> item = getDataFromOptions();
		output(sdk.createAccountGroupPermissions(item, groupId));
	}

	private void usersGroupPermissionsGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		String groupId = getStringOption(cmdOptions, "<group_id>");
		output(sdk.getAccountGroupPermissions(groupId));
	}

	private void usersUserPermissionsDelete(Map<String, Object> cmdOptions){
		connectWithAuth();
		String userId = getStringOption(cmdOptions, "<user_id>");
		String permissionId = getStringOption(cmdOptions, "<permission_id>");
		sdk.deleteUserPermissions(userId, permissionId);
	}

	private void usersUserPermissionsCreate(Map<String, Object> cmdOptions){
		connectWithAuth();
		String userId = getStringOption(cmdOptions, "<user_id>");
		String data = getDataOption();
		Permission permission = new DataConverter<Permission>().convert(data, Permission.class);
		output(sdk.addUserPermission(permission, userId));
	}

	private void usersUserPermissionsGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		String userId = getStringOption(cmdOptions, "<user_id>");
		output(sdk.getUserPermissions(userId));
	}

	private void usersAllPermissionsGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		output(sdk.getAllPermissions());
	}

	private void usersGroupsUserDelete(Map<String, Object> cmdOptions){
		connectWithAuth();
		String groupId = getStringOption(cmdOptions, "<group_id>");
		String userId = getStringOption(cmdOptions, "<user_id>");
		sdk.deleteUserFromGroup(groupId, userId);
	}

	private void usersGroupsUserAssign(Map<String, Object> cmdOptions){
		connectWithAuth();
		String groupId = getStringOption(cmdOptions, "<group_id>");
		String userId = getStringOption(cmdOptions, "<user_id>");
		output(sdk.assignUserToGroup(groupId, userId));
	}

	private void usersGroupsUsersGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		String groupId = getStringOption(cmdOptions, "<group_id>");
		output(sdk.getAllAccountMembers(groupId));
	}

	private void usersGroupsUpdate(Map<String, Object> cmdOptions){
		connectWithAuth();
		Map<String, Object> item = getDataFromOptions();
		String groupId = getStringOption(cmdOptions, "<group_id>");
		output(sdk.updateAccountGroup(item, groupId));
	}

	private void usersGroupsDelete(Map<String, Object> cmdOptions){
		connectWithAuth();
		String groupId = getStringOption(cmdOptions, "<group_id>");
		sdk.deleteAccountGroup(groupId);
	}

	private void usersGroupsGet(Map<String, Object> cmdOptions){
        connectWithAuth();
        String groupId = getStringOption(cmdOptions, "<group_id>");
        boolean users = true;
        boolean permissions = true;
        if (cmdOptions.containsKey("--include-users")) {
            users = getBooleanOption(cmdOptions, "--include-users");
        }
        if (cmdOptions.containsKey("--include-users")) {
            permissions = getBooleanOption(cmdOptions, "--include-permissions");
        }
        output(sdk.getAccountGroup(groupId, users, permissions));
    }

	private void usersGroupsCreate(Map<String, Object> cmdOptions){
		connectWithAuth();
		Map<String, Object> item = getDataFromOptions();
		output(sdk.createAccountGroup(item));
	}

	private void usersAllGroupsGet(Map<String, Object> cmdOptions) {
        connectWithAuth();
        boolean users = true;
        boolean permissions = true;
        if (cmdOptions.containsKey("--include-users")) {
            users = getBooleanOption(cmdOptions, "--include-users");
        }
        if (cmdOptions.containsKey("--include-permissions")) {
            permissions = getBooleanOption(cmdOptions, "--include-permissions");
        }

        output(sdk.getAllAccountGroups(users, permissions));
    }

	private void usersUserPasswordUpdate(Map<String, Object> cmdOptions){
		connectWithAuth();
		Map<String, Object> item = getDataFromOptions();
		String userId = getStringOption(cmdOptions, "<user_id>");
		output(sdk.updateUserPassword(item, userId));
	}

	private void usersUsersUpdate(Map<String, Object> cmdOptions){
		connectWithAuth();
		Map<String, Object> item = getDataFromOptions();
		String userId = getStringOption(cmdOptions, "<user_id>");
		output(sdk.updateUser(item,userId));
	}

	private void usersUsersDelete(Map<String, Object> cmdOptions){
		connectWithAuth();
		String userId = getStringOption(cmdOptions, "<user_id>");
		sdk.deleteUser(userId);
	}

	private void usersUsersGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		String userId = getStringOption(cmdOptions, "<user_id>");
		output(sdk.getUser(userId));
	}

	private void usersUsersCreate(Map<String, Object> cmdOptions){
		connectWithAuth();
		String data = getDataOption();
		User user = new DataConverter<User>().convert(data, User.class);
		output(sdk.createUser(user));
	}

	private void usersAllUsersGet(Map<String, Object> cmdOptions){
		connectWithAuth();
		output(sdk.getAllUsers());
	}

	private StatisticsRequest getStatisticsRequestForOptions() {
		StatisticsRequest statsRequest = new StatisticsRequest();
		if (hasAllOptions(cmdOptions, "--configuration-id")) {
			statsRequest.setConfigurationId(getStringOption(cmdOptions, "--configuration-id"));
		}
		if (hasAllOptions(cmdOptions, "--interval")) {
			statsRequest.setInterval(getStringOption(cmdOptions, "--interval"));
		}
		if (hasAllOptions(cmdOptions, "--from-date")) {
			statsRequest.setFromDate(getStringOption(cmdOptions, "--from-date"));
		}
		if (hasAllOptions(cmdOptions, "--to-date")) {
			statsRequest.setToDate(getStringOption(cmdOptions, "--to-date"));
		}
		if (hasAllOptions(cmdOptions, "--filter-by")) {
			String input = getStringOption(cmdOptions, "--filter-by");
			statsRequest.setFilterBy(getCommaSeparatedData(input));
		}
		if (hasAllOptions(cmdOptions, "--group-by")) {
			String input = getStringOption(cmdOptions, "--group-by");
			statsRequest.setGroupBy(getCommaSeparatedData(input));
		}
		return statsRequest;
	}

	private Map<String, String> getCommaSeparatedData(String input){
		Map<String, String> map = new HashMap<String, String>();
		String[] filters = input.split(",");
		for (String filter : filters){
			String[] keyValuePair = filter.split(":");
			map.put(keyValuePair[0], keyValuePair[1]);
		}
		return map;
	}

	private void doDocumentation(String service) {
		try {
			String text = getTextFromResource(service + ".txt");
			output(text);
		} catch (IOException e) {
			output("No help found");
		}
	}

	private void connectWithAuth() {
		if (config.getAccessToken() != null) {
			shouldCleanupAuthToken = false;
			sdk = config.connect();
		}
		else {
			shouldCleanupAuthToken = true;
			SemantriaSDK tmpSdk = config.connect();
			AccessToken accessToken = tmpSdk.createSession(getUserCredentials());
			config.setAccessToken(accessToken.getAccessToken());
			sdk = config.connect();
		}
	}

	private void cleanupAuth() {
		if (!shouldCleanupAuthToken || sdk == null) {
			return;
		}
		sdk.deleteSession(config.getAccessToken());
	}

	private UserCredentials getUserCredentials() {
		if (config.getCredentials() != null) {
			return config.getCredentials();
		}
		java.io.Console console = System.console();
		if (console == null) {
			throw new CommandFailed("Authentication is required");
		}
		String login = null;
		while (login == null || login.trim().length() == 0) {
			login = console.readLine("Login: ");
		}
		String password = null;
		while (password == null || password.trim().length() == 0) {
			password = new String(console.readPassword("Password: "));
		}
		return new UserCredentials(login, password);
	}

	private List<String> readTextFile(File file){
		if (!file.exists()) {
			return Collections.emptyList();
		}

		List<String> data = new ArrayList<String>();
		try {
			FileInputStream stream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(stream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null) {
				data.add(strLine);
			}
			in.close();
		} catch (Exception e) {
			throw new CommandFailed("Error reading documents from " + data);
		}

		return data;
	}

	private String getDataOption() {
		String data = getStringOption(cmdOptions, "--data");
		if(data.isEmpty()) {
			throw new CommandFailed("Value for --data value cannot be null.");
		}
		if (data.startsWith("@")) {
			File file = new File(data.substring(1));
			if (!file.isFile()) {
				throw new CommandFailed("Value for --data value is not a file.");
			}
			try {
				return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
			} catch (IOException e) {
				throw new CommandFailed("Error reading value from " + file.getAbsolutePath());
			}
		}
		return data;
	}

	private static class DataConverter<T> {
		public T convert(String data, Class<T> klass) {
			try {
				return mapper.readValue(data, klass);
			} catch (IOException e) {
				throw new CommandFailed("Failed to read a " + klass.getSimpleName() + " : " + e.getMessage());
			}
		}
	}

	private Map<String, Object> getDataFromOptions() {
		if (!hasAllOptions(cmdOptions, "--data")) {
			throw new CommandFailed("Need a valid json or file location for a configuration.");
		}
		String data = getDataOption();
		try {
			return mapper.readValue(data, new TypeReference<Map<String, Object>>() {});
		}
		catch (IOException e) {
			throw new CommandFailed("Error reading value from --data parameter\n" + e.getMessage());
		}
	}


	private List<Document> getDocumentsFromOptions() {
		if(hasAllOptions(cmdOptions, "--data")) {
			String data = getStringOption(cmdOptions, "--data");
			if(data.isEmpty()) {
				throw new CommandFailed("Value for --data value cannot be null.");
			}
			List<Document> documents = new ArrayList<Document>();
			if(data.startsWith("@")) {
				File file = new File(data.substring(1));
				try {
					List<String> docs = readTextFile(file);
					for (String text : docs) {
						String uid = UUID.randomUUID().toString();
						Document doc = new Document();
						doc.setId(uid);
						doc.setText(text);
						documents.add(doc);
					}
					return documents;
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new CommandFailed("Error reading configuration from " + file.getAbsolutePath());
				}
			}
			else
			{
				String uid = UUID.randomUUID().toString();
				Document doc = new Document();
				doc.setId(uid);
				doc.setText(data);
				documents.add(doc);
				return documents;
			}
		}
		else {
			throw new CommandFailed("Need a valid json or file location for a configuration.");
		}
	}

	private List<String> getCollectionTextFromOptions() {
		if(hasAllOptions(cmdOptions, "--data")) {
			String data = getStringOption(cmdOptions, "--data");
			if(data.isEmpty()) {
				throw new CommandFailed("Value for --data value cannot be null.");
			}
			if(data.startsWith("@")) {
				File file = new File(data.substring(1));
				try {
					return readTextFile(file);
				}
				catch (Exception e) {
					e.printStackTrace();
					throw new CommandFailed("Error reading configuration from " + file.getAbsolutePath());
				}
			}
			else
			{
				throw new CommandFailed("--data value needs to being with @ symbol.");
			}
		}
		else {
			throw new CommandFailed("Need a valid json or file location for a configuration.");
		}
	}

	private void generateConfiguration() throws IOException {
		config = SemantriaClientConfiguration.fromFile(getStringOption(cmdOptions, "--config-file"));
		if (hasAllOptions(cmdOptions, "--verbose")) {
			config.setLogfile("http.log");
		}

		if (hasAllOptions(cmdOptions, "--credentials", "--access-token")) {
			throw new CommandFailed("Cannot specify both " + "--credentials" + " and " + "--access-token");
		}

		if (hasAllOptions(cmdOptions, "--credentials")) {
			UserCredentials userCredentials = parseCredentialsOption(getStringOption(cmdOptions, "--credentials"));
			config.setCredentials(userCredentials);
			config.setAccessToken(null); // override anything in config
		}
		if (hasAllOptions(cmdOptions, "--access-token")) {
			config.setAccessToken(getStringOption(cmdOptions, "--access-token"));
			config.setCredentials(null); // override anything in config
		}
		if (hasAllOptions(cmdOptions, "--api-endpoint")) {
			config.setApiEndpoint(getStringOption(cmdOptions, "--api-endpoint"));
		}
	}

	private UserCredentials parseCredentialsOption(String creds) {
		int idx = creds.indexOf(':');
		if (idx == -1) {
			throw new CommandFailed("--credentials" + " must be user:pass. Got " + creds);
		}
		String user = creds.substring(0, idx);
		String passwd = creds.substring(idx + 1);
		return new UserCredentials(user, passwd);
	}

	private void verbose(String message) {
		if (!verbose) {
			return;
		}
		System.err.println(message);
	}

	private void error(String message) {
		System.err.println("ERROR: " + message);
	}

	public static class CommandFailed extends RuntimeException {
		public CommandFailed(String message) {
			super(message);
		}
	}
}
