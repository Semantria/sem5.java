package com.lexalytics.semantria;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class CaptureOutput {

    private ByteArrayOutputStream copy;
    private CaptureOutputStream captureOut;
    private CaptureOutputStream captureErr;

    void start() {
        this.copy = new ByteArrayOutputStream();
        this.captureOut = new CaptureOutputStream(System.out, copy);
        this.captureErr = new CaptureOutputStream(System.err, copy);
        System.setOut(new PrintStream(captureOut));
        System.setErr(new PrintStream(captureErr));
    }

    void release() {
        if (copy == null) {
            return;
        }
        System.setOut(captureOut.getOriginal());
        System.setErr(captureErr.getOriginal());
        copy = null;
    }

    private void flush() {
        try {
            captureOut.flush();
            captureErr.flush();
        } catch (IOException ex) {
            // ignore
        }
    }

    @Override
    public String toString() {
        flush();
        return copy.toString();
    }

    private class CaptureOutputStream extends OutputStream {
        private final PrintStream original;
        private final OutputStream copy;

        public CaptureOutputStream(PrintStream original, OutputStream copy) {
            this.original = original;
            this.copy = copy;
        }

        PrintStream getOriginal() {
            return original;
        }

        @Override
        public void write(int b) throws IOException {
            copy.write(b);
            original.write(b);
            original.flush();
        }

        @Override
        public void write(byte[] b) throws IOException {
            write(b, 0, b.length);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            copy.write(b, off, len);
            original.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            copy.flush();
            original.flush();
        }
    }
}
