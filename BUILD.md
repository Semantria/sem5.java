# Sem5 Java SDK Build Notes

Goal is to build the java sdk and deploy it to the internal artifactory

    https://ci.semantria.com/artifactory/libs-release-local

First make sure you have [lexalytics-common](https://bitbucket.org/semantria/lexalytics-common/) checked out locally and add an environment variable called `$LXA_COMMON` that points to the directory

    # [ -f $LXA_COMMON/deploy_pom.py ] && echo OK
    OK

Make sure you have bumped the version in the `sem5.java/pom.xml`

Then to build and deploy from the [semantria-service-2.0](https://bitbucket.org/semantria/semantria-service-2.0/) repository:

    # cd semantria-service-2.0/clients/sem5.java
    # $LXA_COMMON/deploy_pom.py pom.xml
    Building package (mvn clean package source:jar)
    Loading pom.xml info
       {'version': u'0.4.1', 'groupId': u'com.lexalytics.semantria', 'artifactId': u'sem5-sdk'}
    Deploying to https://ci.semantria.com/artifactory/libs-release-local/com/lexalytics/semantria/sem5-sdk/0.4.1/sem5-sdk-0.4.1-sources.jar Success
    Deploying to https://ci.semantria.com/artifactory/libs-release-local/com/lexalytics/semantria/sem5-sdk/0.4.1/sem5-sdk-0.4.1.jar Success
    Deploying to https://ci.semantria.com/artifactory/libs-release-local/com/lexalytics/semantria/sem5-sdk/0.4.1/sem5-sdk-0.4.1.pom Success
