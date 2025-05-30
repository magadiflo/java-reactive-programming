package dev.magadiflo.app;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;

class Lec07ScenarioNameTest {

    private Flux<Integer> getItems() {
        return Flux.range(1, 3);
    }

    @Test
    void scenarioNameTest() {
        // given
        StepVerifierOptions stepVerifierOptions = StepVerifierOptions.create().scenarioName("Pruebas de items desde el 1 al 3");
        // when
        Flux<Integer> items = getItems();

        // then
        StepVerifier.create(items, stepVerifierOptions)
                .expectNext(11).as("El primer item debería ser 11")
                .expectNext(2, 3).as("Los siguientes números deben ser 2 y 3")
                .verifyComplete();
    }
}
