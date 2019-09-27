#!/usr/bin/env bash

CP=$(echo target/lib/*.jar | tr ' ' ':'):target/sem5-sdk-0.5.2.jar
echo $CP
java -cp $CP com.lexalytics.semantria.CommandLineInterface
