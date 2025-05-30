package dev.magadiflo.app;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

// StepVerifier actúa como un suscriptor
class Lec01MonoTest {
    private static final Logger log = LoggerFactory.getLogger(Lec01MonoTest.class);

    private Mono<String> getProduct(int id) {
        return Mono.fromSupplier(() -> "product-" + id)
                .doFirst(() -> log.info("invocado"));
    }

    @Test
    void productTest() {
        // given
        int id = 1;

        // when
        Mono<String> product = getProduct(id);

        // then
        StepVerifier.create(product)
                .expectNext("product-1")
                .expectComplete()
                .verify(); // Subscribe, aquí es donde se activa la suscripción
    }
}
