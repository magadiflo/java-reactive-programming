package dev.magadiflo.app;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.util.context.Context;

class Lec08ContextTest {

    private Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            if (ctx.hasKey("user")) {
                return Mono.just("Bienvenido %s".formatted(ctx.get("user").toString()));
            }
            return Mono.error(new RuntimeException("No autenticado"));
        });
    }

    @Test
    void welcomeMessageTest() {
        // given
        StepVerifierOptions stepVerifierOptions = StepVerifierOptions.create()
                .withInitialContext(Context.of("user", "sam"));

        // when
        Mono<String> welcomeMessage = getWelcomeMessage();

        // then
        StepVerifier.create(welcomeMessage, stepVerifierOptions)
                .expectNext("Bienvenido sam")
                .verifyComplete();
    }

    @Test
    void unauthenticatedTest() {
        // given
        StepVerifierOptions stepVerifierOptions = StepVerifierOptions.create()
                .withInitialContext(Context.empty());

        // when
        Mono<String> welcomeMessage = getWelcomeMessage();

        // then
        StepVerifier.create(welcomeMessage, stepVerifierOptions)
                .expectErrorMessage("No autenticado")
                .verify();
    }
}
