package com.lexalytics.semantria.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lexalytics.semantria.client.dto.*;
import feign.*;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface SemantriaSDK {

    int DEFAULT_PAGE_SIZE = 10;

    static SemantriaSDK connect() {
        return SemantriaSDK.connect(null);
    }

    static SemantriaSDK connect(SemantriaClientConfiguration config) {
        if (config == null) {
            config = SemantriaClientConfiguration.fromFile((File) null);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new StdDateFormat());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        JacksonDecoder decoder = new JacksonDecoder(mapper);
        JacksonEncoder encoder = new JacksonEncoder(mapper);
        SemantriaRequestInterceptor requestInterceptor = new SemantriaRequestInterceptor(
                config.getAccessToken(),
                config.getApiVersion(),
                config.getAppName());
        Feign.Builder builder = Feign.builder()
                .client(new OkHttpClient())
                .queryMapEncoder(new QueryMapEncoder())
                .requestInterceptor(requestInterceptor)
                .errorDecoder(new SemantriaErrorDecoder())
                .encoder(encoder)
                .decoder(decoder);
        if (config.getLogfile() != null) {
            Logger.JavaLogger javaLogger = new Logger.JavaLogger().appendToFile(config.getLogfile());
            builder = builder.logger(javaLogger).logLevel(Logger.Level.FULL);
        }

        return builder.target(SemantriaSDK.class, config.getApiEndpoint());
    }

    // *********** AUTH **************

    @RequestLine("POST /auth/impersonate-sessions/{userId}")
    @Headers("Content-Type: application/json")
    AccessToken createImpersonationSession(@Param("userId") String userId);

    @RequestLine("POST /auth/sessions/")
    @Headers("Content-Type: application/json")
    AccessToken createSession(UserCredentials userCredentials);

    @RequestLine("POST /auth/sessions/?expiration={expiration}&expire_after_minutes={expire-after-minutes}")
    @Headers("Content-Type: application/json")
    AccessToken createSession(UserCredentials userCredentials, @Param("expiration") String expirationPolicy,
                              @Param("expire-after-minutes") int expireAfterMinutes);

    @RequestLine("GET /auth/sessions/")
    AllSessionInfo getSessions();

    @RequestLine("GET /auth/sessions/{sessionId}")
    AccessToken getSession(@Param("sessionId") String sessionId);

    @RequestLine("DELETE /auth/sessions/{accessToken}")
    void deleteSession(@Param("accessToken") String accessToken);

    @RequestLine("GET /auth/users/{userId}/sessions/")
    AllSessionInfo getUserSessions(@Param("userId") String userId);

    @RequestLine("DELETE /auth/sessions/user/{userId}/")
    void deleteUserSessions(@Param("userId") String userId);

    @RequestLine("GET /auth/accounts/{accountId}/sessions/")
    AllSessionInfo getAccountSessions(@Param("accountId") String accountId);

    @RequestLine("DELETE /auth/sessions/account/{accountId}/")
    void deleteAccountSessions(@Param("accountId") String accountId);

    @RequestLine("POST /auth/renew")
    AccessToken renewSession();

    // *********** ACCOUNTS *************

    @RequestLine("GET /details/")
    Account getAccountDetails();

    @RequestLine("GET /password/password-rules/")
    JsonNode getAccountPasswordRules();

    @RequestLine("GET /password/password-rules/{ruleId}")
    JsonNode getPasswordRules(@Param("ruleId") String ruleId);

    @RequestLine("GET /password/password-rules/types/")
    JsonNode getPasswordRuleTypes();

    // *********** CONFIGS *************

    @RequestLine("GET /configs/")
    List<Configuration> getAllConfigurations();

    @RequestLine("GET /configs/{configurationId}")
    Configuration getConfiguration(@Param("configurationId") String configurationId);

    @RequestLine("POST /configs/")
    @Headers("Content-Type: application/json")
    JsonNode createConfiguration(Map<String,Object> configuration);

    @RequestLine("PUT /configs/{configurationId}")
    @Headers("Content-Type: application/json")
    JsonNode updateConfiguration(@Param("configurationId") String configurationId,
                                 Map<String,Object> configuration);

    @RequestLine("DELETE /configs/{configurationId}")
    void deleteConfiguration(@Param("configurationId") String configurationId);


    // query-topics
    @Headers("Content-Type: application/json")
    @RequestLine("GET /configs/{configurationId}/query-topics/?page_number={pageNumber}&page_size={pageSize}")
    Paged<QueryTopic> listConfigurationQueryTopics(@Param("configurationId") String configurationId,
                                                   @Param("pageNumber") int pageNumber,
                                                   @Param("pageSize") int pageSize);

    default Paged<QueryTopic> listConfigurationQueryTopics(String configurationId) {
        return listConfigurationQueryTopics(configurationId, 0, DEFAULT_PAGE_SIZE);
    }

    default Paged<QueryTopic> listConfigurationQueryTopics(String configurationId, int pageNumber) {
        return listConfigurationQueryTopics(configurationId, pageNumber, DEFAULT_PAGE_SIZE);
    }

    @RequestLine("POST /configs/{configurationId}/query-topics/")
    @Headers("Content-Type: application/json")
    QueryTopic createConfigurationQueryTopic(@Param("configurationId") String configurationId,
                                             QueryTopic queryTopic);

    @RequestLine("GET /configs/{configurationId}/query-topics/{queryTopicId}")
    @Headers("Content-Type: application/json")
    QueryTopic getConfigurationQueryTopic(@Param("configurationId") String configurationId,
                                          @Param("queryTopicId") String queryTopicId);

    @RequestLine("DELETE /configs/{configurationId}/query-topics/{queryTopicId}")
    void deleteConfigurationQueryTopic(@Param("configurationId") String configurationId,
                                       @Param("queryTopicId") String queryTopicId);

    @RequestLine("PUT /configs/{configurationId}/query-topics/{queryTopicId}")
    @Headers("Content-Type: application/json")
    QueryTopic updateConfigurationQueryTopic(@Param("configurationId") String configurationId,
                                             @Param("queryTopicId") String queryTopicId,
                                             QueryTopic queryTopic);


    // concept-topics
    @Headers("Content-Type: application/json")
    @RequestLine("GET /configs/{configurationId}/concept-topics/?page_number={pageNumber}&page_size={pageSize}")
    Paged<ConceptTopic> listConfigurationConceptTopics(@Param("configurationId") String configurationId,
                                                       @Param("pageNumber") int pageNumber,
                                                       @Param("pageSize") int pageSize);

    default Paged<ConceptTopic> listConfigurationConceptTopics(String configurationId) {
        return listConfigurationConceptTopics(configurationId, 0, DEFAULT_PAGE_SIZE);
    }

    default Paged<ConceptTopic> listConfigurationConceptTopics(String configurationId, int pageNumber) {
        return listConfigurationConceptTopics(configurationId, pageNumber, DEFAULT_PAGE_SIZE);
    }

    @RequestLine("POST /configs/{configurationId}/concept-topics/")
    @Headers("Content-Type: application/json")
    ConceptTopic createConfigurationConceptTopic(@Param("configurationId") String configurationId,
                                                 ConceptTopic conceptTopic);

    @RequestLine("GET /configs/{configurationId}/concept-topics/{conceptTopicId}")
    @Headers("Content-Type: application/json")
    ConceptTopic getConfigurationConceptTopic(@Param("configurationId") String configurationId,
                                              @Param("conceptTopicId") String conceptTopicId);

    @RequestLine("DELETE /configs/{configurationId}/concept-topics/{conceptTopicId}")
    void deleteConfigurationConceptTopic(@Param("configurationId") String configurationId,
                                         @Param("conceptTopicId") String conceptTopicId);

    @RequestLine("PUT /configs/{configurationId}/concept-topics/{conceptTopicId}")
    @Headers("Content-Type: application/json")
    ConceptTopic updateConfigurationConceptTopic(@Param("configurationId") String configurationId,
                                                 @Param("conceptTopicId") String conceptTopicId,
                                                 ConceptTopic conceptTopic);


    // blacklist
    @Headers("Content-Type: application/json")
    @RequestLine("GET /configs/{configurationId}/blacklist/?page_number={pageNumber}&page_size={pageSize}")
    Paged<BlacklistItem> listConfigurationBlacklists(@Param("configurationId") String configurationId,
                                                     @Param("pageNumber") int pageNumber,
                                                     @Param("pageSize") int pageSize);

    default Paged<BlacklistItem> listConfigurationBlacklists(String configurationId) {
        return listConfigurationBlacklists(configurationId, 0, DEFAULT_PAGE_SIZE);
    }

    default Paged<BlacklistItem> listConfigurationBlacklists(String configurationId, int pageNumber) {
        return listConfigurationBlacklists(configurationId, pageNumber, DEFAULT_PAGE_SIZE);
    }

    @RequestLine("POST /configs/{configurationId}/blacklist/")
    @Headers("Content-Type: application/json")
    BlacklistItem createConfigurationBlacklist(@Param("configurationId") String configurationId,
                                               BlacklistItem blacklistItem);

    @RequestLine("GET /configs/{configurationId}/blacklist/{blacklistItemId}")
    @Headers("Content-Type: application/json")
    BlacklistItem getConfigurationBlacklist(@Param("configurationId") String configurationId,
                                            @Param("blacklistItemId") String blacklistItemId);

    @RequestLine("DELETE /configs/{configurationId}/blacklist/{blacklistItemId}")
    void deleteConfigurationBlacklist(@Param("configurationId") String configurationId,
                                      @Param("blacklistItemId") String blacklistItemId);

    @RequestLine("PUT /configs/{configurationId}/blacklist/{blacklistItemId}")
    @Headers("Content-Type: application/json")
    BlacklistItem updateConfigurationBlacklist(@Param("configurationId") String configurationId,
                                               @Param("blacklistItemId") String blacklistItemId,
                                               BlacklistItem blacklistItem);


    // sentiment-phrases
    @Headers("Content-Type: application/json")
    @RequestLine("GET /configs/{configurationId}/sentiment-phrases/?page_number={pageNumber}&page_size={pageSize}")
    Paged<SentimentPhrase> listConfigurationSentimentPhrases(@Param("configurationId") String configurationId,
                                                             @Param("pageNumber") int pageNumber,
                                                             @Param("pageSize") int pageSize);

    default Paged<SentimentPhrase> listConfigurationSentimentPhrases(String configurationId) {
        return listConfigurationSentimentPhrases(configurationId, 0, DEFAULT_PAGE_SIZE);
    }

    default Paged<SentimentPhrase> listConfigurationSentimentPhrases(String configurationId, int pageNumber) {
        return listConfigurationSentimentPhrases(configurationId, pageNumber, DEFAULT_PAGE_SIZE);
    }

    @RequestLine("POST /configs/{configurationId}/sentiment-phrases/")
    @Headers("Content-Type: application/json")
    SentimentPhrase createConfigurationSentimentPhrase(@Param("configurationId") String configurationId,
                                                       SentimentPhrase sentimentPhrase);

    @RequestLine("GET /configs/{configurationId}/sentiment-phrases/{sentimentPhraseId}")
    @Headers("Content-Type: application/json")
    SentimentPhrase getConfigurationSentimentPhrase(@Param("configurationId") String configurationId,
                                                    @Param("sentimentPhraseId") String sentimentPhraseId);

    @RequestLine("DELETE /configs/{configurationId}/sentiment-phrases/{sentimentPhraseId}")
    void deleteConfigurationSentimentPhrase(@Param("configurationId") String configurationId,
                                            @Param("sentimentPhraseId") String sentimentPhraseId);

    @RequestLine("PUT /configs/{configurationId}/sentiment-phrases/{sentimentPhraseId}")
    @Headers("Content-Type: application/json")
    SentimentPhrase updateConfigurationSentimentPhrase(@Param("configurationId") String configurationId,
                                                       @Param("sentimentPhraseId") String sentimentPhraseId,
                                                       SentimentPhrase sentimentPhrase);


    // user-entities
    @Headers("Content-Type: application/json")
    @RequestLine("GET /configs/{configurationId}/user-entities/?page_number={pageNumber}&page_size={pageSize}")
    Paged<UserEntity> listConfigurationUserEntities(@Param("configurationId") String configurationId,
                                                    @Param("pageNumber") int pageNumber,
                                                    @Param("pageSize") int pageSize);

    default Paged<UserEntity> listConfigurationUserEntities(String configurationId) {
        return listConfigurationUserEntities(configurationId, 0, DEFAULT_PAGE_SIZE);
    }

    default Paged<UserEntity> listConfigurationUserEntities(String configurationId, int pageNumber) {
        return listConfigurationUserEntities(configurationId, pageNumber, DEFAULT_PAGE_SIZE);
    }

    @RequestLine("POST /configs/{configurationId}/user-entities/")
    @Headers("Content-Type: application/json")
    UserEntity createConfigurationUserEntity(@Param("configurationId") String configurationId,
                                             UserEntity userEntity);

    @RequestLine("GET /configs/{configurationId}/user-entities/{userEntityId}")
    @Headers("Content-Type: application/json")
    UserEntity getConfigurationUserEntity(@Param("configurationId") String configurationId,
                                          @Param("userEntityId") String userEntityId);

    @RequestLine("DELETE /configs/{configurationId}/user-entities/{userEntityId}")
    void deleteConfigurationUserEntity(@Param("configurationId") String configurationId,
                                       @Param("userEntityId") String userEntityId);

    @RequestLine("PUT /configs/{configurationId}/user-entities/{userEntityId}")
    @Headers("Content-Type: application/json")
    UserEntity updateConfigurationUserEntity(@Param("configurationId") String configurationId,
                                             @Param("userEntityId") String userEntityId,
                                             UserEntity userEntity);


    // taxonomy
    @Headers("Content-Type: application/json")
    @RequestLine("GET /configs/{configurationId}/taxonomy/?page_number={pageNumber}&page_size={pageSize}")
    Paged<TaxonomyNode> listConfigurationTaxonomies(@Param("configurationId") String configurationId,
                                                    @Param("pageNumber") int pageNumber,
                                                    @Param("pageSize") int pageSize);

    default Paged<TaxonomyNode> listConfigurationTaxonomies(String configurationId) {
        return listConfigurationTaxonomies(configurationId, 0, DEFAULT_PAGE_SIZE);
    }

    default Paged<TaxonomyNode> listConfigurationTaxonomies(String configurationId, int pageNumber) {
        return listConfigurationTaxonomies(configurationId, pageNumber, DEFAULT_PAGE_SIZE);
    }

    @RequestLine("POST /configs/{configurationId}/taxonomy/")
    @Headers("Content-Type: application/json")
    TaxonomyNode createConfigurationTaxonomy(@Param("configurationId") String configurationId,
                                             TaxonomyNode taxonomyNode);

    @RequestLine("GET /configs/{configurationId}/taxonomy/{taxonomyNodeId}")
    @Headers("Content-Type: application/json")
    TaxonomyNode getConfigurationTaxonomy(@Param("configurationId") String configurationId,
                                          @Param("taxonomyNodeId") String taxonomyNodeId);

    @RequestLine("DELETE /configs/{configurationId}/taxonomy/{taxonomyNodeId}")
    void deleteConfigurationTaxonomy(@Param("configurationId") String configurationId,
                                     @Param("taxonomyNodeId") String taxonomyNodeId);

    @RequestLine("PUT /configs/{configurationId}/taxonomy/{taxonomyNodeId}")
    @Headers("Content-Type: application/json")
    TaxonomyNode updateConfigurationTaxonomy(@Param("configurationId") String configurationId,
                                             @Param("taxonomyNodeId") String taxonomyNodeId,
                                             TaxonomyNode taxonomyNode);

    // *********** DOCS *************

    @RequestLine("POST /documents/")
    @Headers("Content-Type: application/json")
    JsonNode sendDocumentsBatch(List<Document> documents, @QueryMap DocumentsRequest documentsRequest);

    @RequestLine("GET /documents/")
    List<DocumentResult> getDocumentsBatch(@QueryMap DocumentsRequest documentsRequest);

    @RequestLine("GET /documents/")
    ArrayNode getDocumentsBatchJson(@QueryMap DocumentsRequest documentsRequest);

    @RequestLine("POST /collections/")
    @Headers("Content-Type: application/json")
    JsonNode sendCollections(Collection collection, @QueryMap CollectionsRequest collectionsRequest);

    @RequestLine("GET /collections/")
    JsonNode getCollections(@QueryMap CollectionsRequest collectionsRequest);

    // *********** FEATURES *************

    @RequestLine("GET /features/")
    JsonNode getAllFeatures();

    @RequestLine("GET /limit-types/")
    JsonNode getLimitTypes();

    @RequestLine("GET /industry-packs/")
    JsonNode getIndustryPacks();

    @RequestLine("GET /languages/")
    JsonNode getLanguages();

    @RequestLine("GET /language-templates/")
    JsonNode getLanguageTemplates();

    @RequestLine("GET /config-settings/")
    JsonNode getConfigurationSettings();


    // *********** STATISTICS *************

    @RequestLine("GET /statistics/")
    JsonNode getCustomStatistics(@QueryMap StatisticsRequest statisticsRequest);

    // *********** USERS *************

    @RequestLine("GET /permissions/")
    JsonNode getAllPermissions();


    @RequestLine("GET /groups/{groupId}/permissions/")
    JsonNode getAccountGroupPermissions(@Param("groupId") String groupId);


    @RequestLine("POST /groups/{groupId}/permissions/")
    @Headers("Content-Type: application/json")
    JsonNode createAccountGroupPermissions(Map<String,Object> permission , @Param("groupId") String groupId);

    @RequestLine("DELETE /groups/{groupId}/permissions/{permissionId}")
    void deleteAccountGroupPermission(@Param("groupId") String groupId,
                                      @Param("permissionId") String permissionId);

    @RequestLine("GET /groups/")
    JsonNode getAllAccountGroups();

    @RequestLine("POST /groups/")
    @Headers("Content-Type: application/json")
    JsonNode createAccountGroup(Map<String,Object> accountGroup);

    @RequestLine("GET /groups/{groupId}")
    JsonNode getAccountGroup(@Param("groupId") String groupId);

    @RequestLine("PUT /groups/{groupId}")
    @Headers("Content-Type: application/json")
    JsonNode updateAccountGroup(Map<String,Object> accountGroup, @Param("groupId") String groupId);

    @RequestLine("DELETE /groups/{groupId}")
    void deleteAccountGroup(@Param("groupId") String groupId);

    @RequestLine("GET /groups/{groupId}/members/")
    List<String> getAllAccountMembers(@Param("groupId") String groupId);

    @RequestLine("POST /groups/{groupId}/members/{userId}")
    @Headers("Content-Type: application/json")
    JsonNode assignUserToGroup(@Param("groupId") String groupId,
                               @Param("userId") String userId);

    @RequestLine("DELETE /groups/{groupId}/members/{userId}")
    void deleteUserFromGroup(@Param("groupId") String groupId,
                             @Param("userId") String userId);

    @RequestLine("GET /users/{userId}/permissions/")
    Permissions getUserPermissions(@Param("userId") String userId);

    @RequestLine("POST /users/{userId}/permissions/")
    @Headers("Content-Type: application/json")
    Permission addUserPermission(Permission permission, @Param("userId") String userId);

    @RequestLine("DELETE /users/{userId}/permissions/{permissionId}")
    void deleteUserPermissions(@Param("userId") String userId, @Param("permissionId") String permissionId);

    @RequestLine("GET /users/")
    List<User> getAllUsers();

    @RequestLine("POST /users/")
    @Headers("Content-Type: application/json")
    User createUser(User user);

    @RequestLine("GET /users/{userId}")
    User getUser(@Param("userId") String userId);

    @RequestLine("PUT /users/{userId}")
    @Headers("Content-Type: application/json")
    JsonNode updateUser(Map<String,Object> user, @Param("userId") String userId);

    @RequestLine("DELETE /users/{userId}")
    void deleteUser(@Param("userId") String userId);

    @RequestLine("PUT /users/{userId}/password/")
    @Headers("Content-Type: application/json")
    JsonNode updateUserPassword(Map<String,Object> password, @Param("userId") String userId);

    @RequestLine("POST /service-users/")
    @Headers("Content-Type: application/json")
    JsonNode createServiceUser(Map<String,Object> user);
}
