# Semantria SDK Examples

This example shows a very simple method of using the Semantria SDK. The example reads a file containing one document per line, sends it to Semantria, polls for the results, and, when all the results have been received, prints out some of the analysis details for each document.

Remember, this example is not intended to demonstrate the best practices for processing large volumes of data. Instead, it's a simple demonstration of some of the SDK features. Please contact Lexalytics for guidance if you plan to build your own application.

## Build the Example

These instructions show how to build the example using maven. You should be able to build and run the examples in your Java IDE as well.

First, clone or download the SDK from [https://github.com/Semantria/sem5.java](https://github.com/Semantria/sem5.java). We're only interested in the `examples` subdirectory, but it's easier to simply get the whole SDK.

Change directory into the `examples` directory. Then compile and package the example:

    $ mvn compile package

## Run the example

To run the example:

    $ java -cp 'target/sem5-sdk-examples-0.5.5-SNAPSHOT.jar;target/lib/*' com.lexalytics.semantria.example.DetailedModeExample --credentials <username>:<password> ../example-data/tweets.txt
    Sending 100 docs...
    Retrieving processed results...

    ---- Document c6932cd9-3ce5-4139-b038-fffc2552e10e -----
    Sentiment polarity: negative
    Sentiment score: -0.72
    ...

To see the options supported:

    $ java -cp 'target/sem5-sdk-examples-0.5.5-SNAPSHOT.jar;target/lib/*' com.lexalytics.semantria.example.DetailedModeExample --help

    Semantria 5.0 detailed mode example

    USAGE:
      DetailedModeExample [options] <file-or-text>
      DetailedModeExample -h | --help | --version

    OPTIONS:
      --credentials CREDS    User credentials user:pass
      --access-token TOKEN   Session token to reuse
      --config-file FILE     Load configuration from this file (default uses SEM5_CONFIG env var)
      --api-endpoint URL     Semantria API endpoint [default: api5.semantria.com]
      --using CONFIG         Language or configuration to be used when processing documents
      --job-id S             Identifier for a job
      --batch-size N         Number of document to submit in a batch [default: 100]
      -h --help              Show this help text
      --version              Print version
      -v --verbose           More messages
      --debug                Even more messages

    DESCRIPTION
    
      This example reads data from a file, one document per line, processes
      it through the Semantria API, and prints some of the results.
    
      There are two options for authentication:
    
        * Use command line option --credentials or --access-token
        * Use a sem5 config file
