# Semantria SDK for Java

The [Semantria](https://www.lexalytics.com/semantria) SDKs are a convenient way to integrate with the Semantria 5 API to build a continuous or high-volume application. The SDKs implement all available Semantria features and include some simple examples of their use. However, the examples are not intended to demonstrate the best practices for processing large volumes of data. Please contact Lexalytics for guidance if you plan to build your own application.

See [semantria.readme.io](https://semantria.readme.io/docs/) for complete API documentation.

If you find a bug or have suggestions let us know: support@lexaltyics.com. Or fork this repo, make your changes, and submit a pull request.

## Installation using Maven Central

The simplest way to use the SDK is import from Maven Central

        <dependency>
            <groupId>com.lexalytics.semantria</groupId>
            <artifactId>sem5-sdk</artifactId>
            <version>0.5.3</version>
        </dependency>

## Installation from source

These instructions show how to build the SDK using maven. You should be able to build and run the examples in your Java IDE as well.

First, clone or download the SDK from [https://github.com/Semantria/sem5.java](https://github.com/Semantria/sem5.java)

Change directory into the Java directory. Then compile and install the SDK and examples:

    $ mvn package install

### Testing

Then run the tests:

    $ mvn test


## Command Line Application

The SDK also includes a command line interface (CLI) that enables you to interact with the Semantria API from the command line.  The easiest way to use this is to create an executable jar using Gradle:

    $ ./gradlew installShadowDist
    ...
    BUILD SUCCESSFUL in 1s
    5 actionable tasks: 5 executed
    
    $ tree build/install/sem5-shadow
    build/install/sem5-shadow
    ├── bin
    │   ├── sem5
    │   └── sem5.bat
    └── lib
        └── sem5-0.5.2-all.jar
    
    $ ./build/install/sem5-shadow/bin/sem5 --help
    Semantria 5.0 Client.
    
    USAGE:
      sem5 [options] account details [get]
    ...
You can then relocate/install this by copying the contents of `build/install/sem5-shadow` elsewhere on your system.  (taking care to make sure the `bin` and `lib` directories remain in the same parent directory).  For example:

    $ cp -r build/install/sem5-shadow/* /usr/local/

## Configuration File

To use an environment variable (with both the SDK or CLI), define an environment variable `SEM5_CONFIG` with the path to the configuration file.  Configuration files can be in YAML or Json format.   The sem5 CLI can also be used to generate a config file, in this case with credentials:

    $ sem5 generate-config --credentials user-1:sekret
    {
      "apiEndpoint" : "https://api5.semantria.com",
      "credentials" : {
        "login" : "user-1",
        "password" : "sekret"
      },
      "accessToken" : null,
      "apiVersion" : "5.0",
      "appName" : "sem5-sdk",
      "logfile" : null
    }
 
Note that these credentials are stored in plain text.  Running the `sem5` CLI with this config file will result in a temporary access token being generated as needed and deleted after the command is complete.  For frequent, or scripted use it would be better to generate and then save an access token.  Here we will create one that won't expire and then save it to the config file instead of using credentials:

    $ sem5 auth sessions create --credentials user-1:sekret --expiration forever \
       | jq .access_token -r
    3346f82d-ecd1-4bd4-b7cd-4acd25360c81
    $ vi $SEM5_CONFIG # edit the file
    $ cat $SEM5_CONFIG
    {
      "apiEndpoint" : "https://api5.semantria.com",
      "accessToken": "3346f82d-ecd1-4bd4-b7cd-4acd25360c81",
      "apiVersion" : "5.0",
      "appName" : "sem5-sdk",
      "logfile" : null
    }

In YAML the config file would look like

    ---
    apiEndpoint: https://api5.semantria.com
    accessToken: 3346f82d-ecd1-4bd4-b7cd-4acd25360c81
    apiVersion: '5.0'
    appName: sem5-sdk
    logfile: 
