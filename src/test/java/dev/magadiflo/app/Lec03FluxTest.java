package dev.magadiflo.app;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class Lec03FluxTest {
    private Flux<Integer> getItems() {
        return Flux.just(1, 2, 3, 4, 5, 6)
                .log();
    }

    @Test
    void fluxTest1() {
        // given
        // when
        Flux<Integer> items = getItems();

        // then
        StepVerifier.create(items)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    void fluxTest2() {
        // given
        // when
        Flux<Integer> items = getItems();

        // then
        StepVerifier.create(items)
                .expectNext(1)
                .expectNext(2)
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void fluxTest3() {
        // given
        // when
        Flux<Integer> items = getItems();

        // then
        StepVerifier.create(items)
                .expectNext(1, 2, 3, 4)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void fluxTest4() {
        // given
        // when
        Flux<Integer> items = getItems();

        // then
        StepVerifier.create(items, 2)
                .expectNext(1)
                .expectNext(2)
                .thenCancel()
                .verify();
    }
}
