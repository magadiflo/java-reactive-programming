package dev.magadiflo.app;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

class Lec06VirtualTimeTest {

    private Flux<Integer> getItems() {
        return Flux.range(1, 5)
                .delayElements(Duration.ofSeconds(10));
    }

    // Nosotros no podemos correr asi, es decir, esperará a 50 segundos para ejecutar el test
    @Test
    @Disabled
    void fluxTest() {
        StepVerifier.create(getItems())
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

    @Test
    void virtualTimeTest1() {
        StepVerifier.withVirtualTime(this::getItems)
                .thenAwait(Duration.ofSeconds(51))
                .expectNext(1, 2, 3, 4, 5)
                .verifyComplete();
    }

    // El primer evento se emita a los 10 segundos
    @Test
    void virtualTimeTest2() {
        StepVerifier.withVirtualTime(this::getItems)
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(9))
                .thenAwait(Duration.ofSeconds(1))
                .expectNext(1)
                .thenAwait(Duration.ofSeconds(40))
                .expectNext(2, 3, 4, 5)
                .verifyComplete();
    }
}
