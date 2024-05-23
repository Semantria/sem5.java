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
import feign.okhttp.OkHttpClient;

import java.io.File;
import java.io.InputStream;
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
        return connect(config.getApiEndpoint(), config.getAccessToken(),
                config.getApiVersion(), config.getAppName(), config.getLogfile());
    }

    static SemantriaSDK connect(String accessToken, String apiVersion, String appName) {
        return connect("https://api5.semantria.com", accessToken, apiVersion, appName, null);
    }

    static SemantriaSDK connect(String apiEndpoint, String accessToken, String apiVersion, String appName, String logFile) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new StdDateFormat());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        SemantriaDecoder decoder = new SemantriaDecoder(mapper);
        SemantriaEncoder encoder = new SemantriaEncoder(mapper);
        SemantriaRequestInterceptor requestInterceptor =
                new SemantriaRequestInterceptor(accessToken, apiVersion, appName);
        Feign.Builder builder = Feign.builder()
                .client(new OkHttpClient())
                .queryMapEncoder(new QueryMapEncoder())
                .requestInterceptor(requestInterceptor)
                .errorDecoder(new SemantriaErrorDecoder())
                .encoder(encoder)
                .decoder(decoder);
        if (logFile != null) {
            Logger.JavaLogger javaLogger = new Logger.JavaLogger().appendToFile(logFile);
            builder = builder.logger(javaLogger).logLevel(Logger.Level.FULL);
        }

        return builder.target(SemantriaSDK.class, apiEndpoint);
    }

    // *********** AUTH **************

    @RequestLine("POST /auth/impersonate-sessions/{userId}")
    @Headers("Content-Type: application/json")
    AccessToken createImpersonationSession(@Param("userId") String userId);

    @RequestLine("POST /auth/impersonate-sessions/{userId}?expiration={expiration}&expire_after_minutes={expire-after-minutes}&renewal_type={renewal-type}&notes={notes}")
    @Headers("Content-Type: application/json")
    AccessToken createImpersonationSession(@Param("userId") String userId,
                                           @Param("expiration") String expirationPolicy,
                                           @Param("expire-after-minutes") int expireAfterMinutes,
                                           @Param("renewal-type") String renewalType,
                                           @Param("notes") String notes);

    @RequestLine("POST /auth/sessions/")
    @Headers("Content-Type: application/json")
    AccessToken createSession(UserCredentials userCredentials);

    @RequestLine("POST /auth/sessions/?expiration={expiration}&expire_after_minutes={expire-after-minutes}")
    @Headers("Content-Type: application/json")
    AccessToken createSession(UserCredentials userCredentials,
                              @Param("expiration") String expirationPolicy,
                              @Param("expire-after-minutes") int expireAfterMinutes);

    @RequestLine("POST /auth/sessions/?expiration={expiration}&expire_after_minutes={expire-after-minutes}&renewal_type={renewal-type}&notes={notes}")
    @Headers("Content-Type: application/json")
    AccessToken createSession(UserCredentials userCredentials,
                              @Param("expiration") String expirationPolicy,
                              @Param("expire-after-minutes") int expireAfterMinutes,
                              @Param("renewal-type") String renewalType,
                              @Param("notes") String notes);

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

    @RequestLine("GET /limit-values/")
    JsonNode getAccountLimitValues();

    @RequestLine("GET /limit-values/{limitType}")
    JsonNode getAccountLimitValue(@Param("limitType") String limitType);

    @RequestLine("GET /balance-values/")
    JsonNode getAccountBalanceValues();

    @RequestLine("GET /balance-values/{balanceType}")
    JsonNode getAccountBalanceValue(@Param("balanceType") String balanceType);

    @RequestLine("GET /balance-refresh/")
    JsonNode getAccountBalanceRefreshes();

    @RequestLine("GET /balance-refresh/{balanceType}")
    JsonNode getAccountBalanceRefresh(@Param("balanceType") String balanceType);

    // *********** CONFIGS *************

    @RequestLine("GET /configs/?group_id={groupId}")
    List<Configuration> getAllConfigurations(@Param("groupId") String groupId);

    @RequestLine("GET /configs/{configurationId}")
    Configuration getConfiguration(@Param("configurationId") String configurationId);

    @RequestLine("GET /configs/{configurationId}/info")
    ConfigurationInfo getConfigurationInfo(@Param("configurationId") String configurationId);

    @RequestLine("POST /configs/?group_id={groupId}")
    @Headers("Content-Type: application/json")
    JsonNode createConfiguration(@Param("groupId") String groupId, Map<String, Object> configuration);

    @RequestLine("POST /configs/?group_id={groupId}")
    @Headers("Content-Type: application/json")
    JsonNode createConfiguration(@Param("groupId") String groupId, Configuration configuration);

    @RequestLine("PUT /configs/{configurationId}")
    @Headers("Content-Type: application/json")
    JsonNode updateConfiguration(@Param("configurationId") String configurationId,
                                 Map<String, Object> configuration);

    @RequestLine("PUT /configs/{configurationId}")
    @Headers("Content-Type: application/json")
    JsonNode updateConfiguration(@Param("configurationId") String configurationId,
                                 Configuration configuration);

    @RequestLine("PUT /configs/{configurationId}/tags/")
    @Headers("Content-Type: application/json")
    JsonNode setConfigTags(@Param("configurationId") String configurationId,
                           List<String> tags);

    @RequestLine("PATCH /configs/{configurationId}/tags?action=add")
    @Headers("Content-Type: application/json")
    JsonNode addConfigTags(@Param("configurationId") String configurationId,
                           List<String> tags);

    @RequestLine("PATCH /configs/{configurationId}/tags?action=delete")
    @Headers("Content-Type: application/json")
    JsonNode removeConfigTags(@Param("configurationId") String configurationId,
                              List<String> tags);

    @RequestLine("DELETE /configs/{configurationId}/tags/")
    @Headers("Content-Type: application/json")
    JsonNode removeAllConfigTags(@Param("configurationId") String configurationId);

    @RequestLine("DELETE /configs/{configurationId}")
    void deleteConfiguration(@Param("configurationId") String configurationId);

    // *********** Configuration Routes *************
    @RequestLine("GET /routes/{routeId}")
    ConfigurationRoute getConfigurationRoute(@Param("routeId") String routeId);

    @RequestLine("GET /routes/?group_id={groupId}")
    List<ConfigurationRoute> getConfigurationRoutes(@Param("groupId") String groupId);

    @RequestLine("POST /routes/?group_id={groupId}")
    @Headers("Content-Type: application/json")
    JsonNode configsRoutesCreate(@Param("groupId") String groupId, Map<String, Object> route);

    @RequestLine("POST /routes/?group_id={groupId}")
    @Headers("Content-Type: application/json")
    JsonNode configsRoutesCreate(@Param("groupId") String groupId, ConfigurationRoute route);

    @RequestLine("DELETE /routes/{routeId}")
    @Headers("Content-Type: application/json")
    JsonNode deleteConfigurationRoute(@Param("routeId") String routeId);

    @RequestLine("PUT /routes/{routeId}")
    @Headers("Content-Type: application/json")
    JsonNode updateConfigurationRoute(@Param("routeId") String routeId,
                                      Map<String, Object> route);

    @RequestLine("PUT /routes/{routeId}")
    @Headers("Content-Type: application/json")
    JsonNode updateConfigurationRoute(@Param("routeId") String routeId,
                                      ConfigurationRoute route);

    @RequestLine("PATCH /routes/{routeId}/configs?action=add")
    @Headers("Content-Type: application/json")
    JsonNode addConfigsToRoute(@Param("routeId") String routeId,
                               List<String> configs);

    @RequestLine("PATCH /routes/{routeId}/configs?action=delete")
    @Headers("Content-Type: application/json")
    JsonNode removeConfigsFromRoute(@Param("routeId") String routeId,
                                    List<String> configs);



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

    @RequestLine("GET /language-templates/")
    JsonNode getLanguageTemplates();

    @RequestLine("GET /industry-packs/")
    JsonNode getIndustryPacks();

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

    @RequestLine("GET /languages/")
    JsonNode getLanguages();

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
    JsonNode createAccountGroupPermissions(Map<String, Object> permission, @Param("groupId") String groupId);

    @RequestLine("DELETE /groups/{groupId}/permissions/{permissionId}")
    void deleteAccountGroupPermission(@Param("groupId") String groupId,
                                      @Param("permissionId") String permissionId);

    @RequestLine("GET /groups/?include_users={includeUsers}&include_permissions={includePermissions}")
    JsonNode getAllAccountGroups(@Param("includeUsers") boolean includeUsers,
                                 @Param("includePermissions") boolean includePermissions);

    @RequestLine("POST /groups/")
    @Headers("Content-Type: application/json")
    JsonNode createAccountGroup(Map<String, Object> accountGroup);

    @RequestLine("GET /groups/{groupId}?include_users={includeUsers}&include_permissions={includePermissions}")
    JsonNode getAccountGroup(@Param("groupId") String groupId,
                             @Param("includeUsers") boolean includeUsers,
                             @Param("includePermissions") boolean includePermissions);

    @RequestLine("PUT /groups/{groupId}")
    @Headers("Content-Type: application/json")
    JsonNode updateAccountGroup(Map<String, Object> accountGroup, @Param("groupId") String groupId);

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
    List<UserResponse> getAllUsers();

    @RequestLine("POST /users/")
    @Headers("Content-Type: application/json")
    UserResponse createUser(User user);

    @RequestLine("GET /users/{userId}")
    UserResponse getUser(@Param("userId") String userId);

    @RequestLine("PUT /users/{userId}")
    @Headers("Content-Type: application/json")
    JsonNode updateUser(Map<String, Object> user, @Param("userId") String userId);

    @RequestLine("PUT /users/{userId}")
    @Headers("Content-Type: application/json")
    JsonNode updateUser(User user, @Param("userId") String userId);

    @RequestLine("DELETE /users/{userId}")
    void deleteUser(@Param("userId") String userId);

    @RequestLine("PUT /users/{userId}/password/")
    @Headers("Content-Type: application/json")
    JsonNode updateUserPassword(Map<String, Object> password, @Param("userId") String userId);

    @RequestLine("POST /service-users/")
    @Headers("Content-Type: application/json")
    JsonNode createServiceUser(Map<String, Object> user);

    @RequestLine("POST /service-users/")
    @Headers("Content-Type: application/json")
    JsonNode createServiceUser(User user);

    // *********** ASSEMBLER *************

    @RequestLine("GET /job-templates/?page_number={pageNumber}&page_size={pageSize}")
    Paged<JobTemplate> listJobTemplates(@Param("pageNumber") int pageNumber,
                                        @Param("pageSize") int pageSize);

    @RequestLine("GET /job-templates/{jobTemplateId}/")
    JobTemplate getJobTemplate(@Param("pageNumber") String jobTemplateId);

    @RequestLine("POST /directories/?path={path}")
    @Headers("Content-Type: application/json")
    FileMetadata createDirectory(@Param("path") String path,
                                 FileMetadata metadata);

    @RequestLine("GET /directories/?path={path}&page_number={pageNumber}&page_size={pageSize}")
    Paged<FileMetadata> listDirectory(@Param("path") String path,
                                      @Param("pageNumber") int pageNumber,
                                      @Param("pageSize") int pageSize);

    @RequestLine("PUT /directories/?path={path}")
    @Headers("Content-Type: application/json")
    FileMetadata updateDirectory(@Param("path") String path,
                                 FileMetadata metadata);

    @RequestLine("DELETE /directories/?path={path}")
    void deleteDirectory(@Param("path") String path);

    @RequestLine("POST /files/?path={path}")
    @Headers("Content-Type: application/octet-stream")
    FileMetadata createFile(@Param("path") String path,
                            InputStream inputStream);

    @RequestLine("GET /files/?path={path}&metadata=true")
    FileMetadata getFileMetadata(@Param("path") String path);

    @RequestLine("GET /files/?path={path}&metadata=false")
    InputStream getFileContent(@Param("path") String path);

    @RequestLine("PUT /files/?path={path}&metadata=true")
    @Headers("Content-Type: application/json")
    FileMetadata updateFileMetadata(@Param("path") String path,
                                    FileMetadata metadata);

    @RequestLine("PUT /files/?path={path}&metadata=false")
    @Headers("Content-Type: application/octet-stream")
    FileMetadata updateFileContent(@Param("path") String path,
                                   InputStream inputStream);

    @RequestLine("DELETE /files/?path={path}")
    void deleteFile(@Param("path") String path);

    @RequestLine("POST /projects/")
    @Headers("Content-Type: application/json")
    Project createProject(Project project);

    @RequestLine("GET /projects/?page_number={pageNumber}&page_size={pageSize}")
    Paged<Project> listProjects(@Param("pageNumber") int pageNumber,
                                @Param("pageSize") int pageSize);

    @RequestLine("GET /projects/?name={nameFilter}&page_number={pageNumber}&page_size={pageSize}")
    Paged<Project> listProjects(@Param("nameFilter") String nameFilter,
                                @Param("pageNumber") int pageNumber,
                                @Param("pageSize") int pageSize);

    @RequestLine("GET /projects/{projectId}/")
    Project getProject(@Param("projectId") String projectId);

    @RequestLine("PUT /projects/{projectId}/")
    @Headers("Content-Type: application/json")
    Project updateProject(@Param("projectId") String projectId,
                          Project project);

    @RequestLine("DELETE /projects/{projectId}/")
    void deleteProject(@Param("projectId") String projectId);

    @RequestLine("POST /projects/{projectId}/jobs/")
    @Headers("Content-Type: application/json")
    Job createJob(@Param("projectId") String projectId,
                  Job job);

    @RequestLine("GET /projects/{projectId}/jobs/?page_number={pageNumber}&page_size={pageSize}")
    Paged<JobBrief> listJobs(@Param("projectId") String projectId,
                             @Param("pageNumber") int pageNumber,
                             @Param("pageSize") int pageSize);

    @RequestLine("GET /projects/{projectId}/jobs/?name={nameFilter}&page_number={pageNumber}&page_size={pageSize}")
    Paged<JobBrief> listJobs(@Param("projectId") String projectId,
                        @Param("nameFilter") String nameFilter,
                        @Param("pageNumber") int pageNumber,
                        @Param("pageSize") int pageSize);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/?brief={brief}")
    Job getJob(@Param("projectId") String projectId,
               @Param("jobId") String jobId,
               @Param("brief") boolean brief);

    @RequestLine("DELETE /projects/{projectId}/jobs/{jobId}/")
    void deleteJob(@Param("projectId") String projectId,
                   @Param("jobId") String jobId);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/?page_number={pageNumber}&page_size={pageSize}")
    Paged<JsonNode> listDatasourceProjects(@Param("projectId") String projectId,
                                           @Param("jobId") String jobId,
                                           @Param("pageNumber") int pageNumber,
                                           @Param("pageSize") int pageSize);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/{datasourceProjectId}/")
    JsonNode getDatasourceProject(@Param("projectId") String projectId,
                                  @Param("jobId") String jobId,
                                  @Param("datasourceProjectId") String datasourceProjectId);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/{datasourceProjectId}/results/?path={path}")
    Paged<FileMetadata> listDatasourceProjectResults(@Param("projectId") String projectId,
                                                     @Param("jobId") String jobId,
                                                     @Param("datasourceProjectId") String datasourceProjectId,
                                                     @Param("path") String path);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/{datasourceProjectId}/results/download/?path={path}&compress={compress}")
    InputStream downloadDatasourceProjectResults(@Param("projectId") String projectId,
                                            @Param("jobId") String jobId,
                                            @Param("datasourceProjectId") String datasourceProjectId,
                                            @Param("path") String path,
                                            @Param("compress") boolean compress);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/{datasourceProjectId}/experiments/?page_number={pageNumber}&page_size={pageSize}")
    Paged<JsonNode> listExperiments(@Param("projectId") String projectId,
                                    @Param("jobId") String jobId,
                                    @Param("datasourceProjectId") String datasourceProjectId,
                                    @Param("pageNumber") int pageNumber,
                                    @Param("pageSize") int pageSize);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/{datasourceProjectId}/experiments/{experimentId}/")
    JsonNode getExperiment(@Param("projectId") String projectId,
                           @Param("jobId") String jobId,
                           @Param("datasourceProjectId") String datasourceProjectId,
                           @Param("experimentId") String experimentId);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/{datasourceProjectId}/experiments/{experimentId}/results/?path={path}")
    Paged<FileMetadata> listExperimentResults(@Param("projectId") String projectId,
                                              @Param("jobId") String jobId,
                                              @Param("datasourceProjectId") String datasourceProjectId,
                                              @Param("experimentId") String experimentId,
                                              @Param("path") String path);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/{datasourceProjectId}/experiments/{experimentId}/results/download/?path={path}&compress={compress}")
    InputStream downloadExperimentResults(@Param("projectId") String projectId,
                                          @Param("jobId") String jobId,
                                          @Param("datasourceProjectId") String datasourceProjectId,
                                          @Param("experimentId") String experimentId,
                                          @Param("path") String path,
                                          @Param("compress") boolean compress);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/{datasourceProjectId}/experiments/{experimentId}/hpos/?page_number={pageNumber}&page_size={pageSize}")
    Paged<JsonNode> listHpos(@Param("projectId") String projectId,
                             @Param("jobId") String jobId,
                             @Param("datasourceProjectId") String datasourceProjectId,
                             @Param("experimentId") String experimentId,
                             @Param("pageNumber") int pageNumber,
                             @Param("pageSize") int pageSize);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/{datasourceProjectId}/experiments/{experimentId}/hpos/{hpoId}/")
    JsonNode getHpo(@Param("projectId") String projectId,
                    @Param("jobId") String jobId,
                    @Param("datasourceProjectId") String datasourceProjectId,
                    @Param("experimentId") String experimentId,
                    @Param("hpoId") String hpoId);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/{datasourceProjectId}/experiments/{experimentId}/hpos/{hpoId}/results/?path={path}")
    Paged<FileMetadata> listHpoResults(@Param("projectId") String projectId,
                                       @Param("jobId") String jobId,
                                       @Param("datasourceProjectId") String datasourceProjectId,
                                       @Param("experimentId") String experimentId,
                                       @Param("hpoId") String hpoId,
                                       @Param("path") String path);

    @RequestLine("GET /projects/{projectId}/jobs/{jobId}/datasource-projects/{datasourceProjectId}/experiments/{experimentId}/hpos/{hpoId}/results/download/?path={path}&compress={compress}")
    InputStream downloadHpoResults(@Param("projectId") String projectId,
                                   @Param("jobId") String jobId,
                                   @Param("datasourceProjectId") String datasourceProjectId,
                                   @Param("experimentId") String experimentId,
                                   @Param("hpoId") String hpoId,
                                   @Param("path") String path,
                                   @Param("compress") boolean compress);
}
