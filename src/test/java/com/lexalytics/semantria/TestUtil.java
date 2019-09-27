package com.lexalytics.semantria;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestUtil {
    public static final int MOCK_PORT = 9989;

    /**
     * Run the cli with supplied command line arguments in addition to adding
     * the mock api endpoint and a dummy access token
     *
     * @param args Command line arguments
     * @return String output from the commmand
     * @throws Exception
     */
    public static String runNoAuth(List<String> args, boolean expectSuccess) throws Exception {
        ArrayList<String> runArgs = new ArrayList<>(args);
        runArgs.add("--api-endpoint");
        runArgs.add("http://localhost:" + MOCK_PORT);
        CaptureOutput captureOutput = new CaptureOutput();
        captureOutput.start();
        boolean success = CommandLineInterface.run(runArgs);
        assertEquals(expectSuccess, success);
        return captureOutput.toString().trim();
    }

    public static String runNoAuth(List<String> args) throws Exception {
        return runNoAuth(args, true);
    }

    public static String run(List<String> args, boolean expectSuccess) throws Exception {
        ArrayList<String> runArgs = new ArrayList<>(args);
        runArgs.add("--access-token");
        runArgs.add("good");
        return runNoAuth(runArgs, expectSuccess);
    }

    public static String run(List<String> args) throws Exception {
        return run(args, true);
    }
}