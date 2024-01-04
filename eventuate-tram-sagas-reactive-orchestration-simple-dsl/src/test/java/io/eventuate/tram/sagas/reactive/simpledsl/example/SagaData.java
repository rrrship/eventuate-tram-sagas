package io.eventuate.tram.sagas.reactive.simpledsl.example;

public class SagaData {
    String failureReason;

    public SagaData() {
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
