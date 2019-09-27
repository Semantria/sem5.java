package com.lexalytics.semantria.client;

public class SemantriaClientError extends RuntimeException {
    private int status = 0;
    private String reason = null;

    public SemantriaClientError(String message) {
        super(message);
    }

    public SemantriaClientError(String message, int status, String reason) {
        super(message);
        this.status = status;
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
