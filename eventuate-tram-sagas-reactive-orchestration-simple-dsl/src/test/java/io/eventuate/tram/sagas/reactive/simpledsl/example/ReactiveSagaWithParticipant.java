package io.eventuate.tram.sagas.reactive.simpledsl.example;

import io.eventuate.tram.commands.consumer.CommandWithDestination;
import io.eventuate.tram.sagas.reactive.orchestration.ReactiveSagaDefinition;
import io.eventuate.tram.sagas.reactive.simpledsl.ReserveCreditCommand;
import io.eventuate.tram.sagas.reactive.simpledsl.SimpleReactiveSaga;
import reactor.core.publisher.Mono;

public class ReactiveSagaWithParticipant implements SimpleReactiveSaga<SagaData> {

    @Override
    public ReactiveSagaDefinition<SagaData> getSagaDefinition() {
        return step()
                .invokeParticipant((sagaData) ->
                    Mono.just(new CommandWithDestination("channel", "resource", new ReserveCreditCommand()))
                )
                .onReply(FailureReply.class, this::handleFailureReply)
                .onReply(SuccessReply.class, this::handleSuccessReply)
                .build();
    }

    private Mono<Void> handleFailureReply(SagaData data, FailureReply reply) {
        data.failureReason = "just because";
        return Mono.empty();
    }

    private Mono<Void> handleSuccessReply(SagaData data, SuccessReply reply) {
        data.failureReason = "n/a";
        return Mono.empty();
    }
}
