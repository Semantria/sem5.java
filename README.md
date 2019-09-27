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

