package io.eventuate.tram.sagas.reactive.simpledsl.example;

import io.eventuate.tram.sagas.reactive.simpledsl.ReserveCreditCommand;
import org.junit.Test;

import static io.eventuate.tram.sagas.reactive.simpledsl.framework.ReactiveSagaUnitTestSupport.given;

public class ReactiveParticipantSagaTest {

    @Test
    public void whenRolledBackThenMessageShouldBePersisted() {
        given()
                .saga(new ReactiveSagaWithParticipant(), new SagaData())
                .expect()
                .command(new ReserveCreditCommand()).to("channel")
                .andGiven()
                .failureReply(new FailureReply())
                .expectRolledBack()
                .assertSagaData((sagaData) -> {
                    assert sagaData.failureReason.equals("just because");
                });
    }

    @Test
    public void whenSuccessThenMessagePersisted() {
        given()
                .saga(new ReactiveSagaWithParticipant(), new SagaData())
                .expect()
                .command(new ReserveCreditCommand()).to("channel")
                .andGiven()
                .successReply(new SuccessReply())
                .expectCompletedSuccessfully()
                .assertSagaData((sagaData) -> {
                    assert sagaData.failureReason.equals("n/a");
                });
    }
}
