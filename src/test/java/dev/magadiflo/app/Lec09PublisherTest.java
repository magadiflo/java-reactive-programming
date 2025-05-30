package dev.magadiflo.app;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.util.function.UnaryOperator;

class Lec09PublisherTest {

    private UnaryOperator<Flux<String>> processor() {
        return flux -> flux
                .filter(s -> s.length() > 1)
                .map(String::toUpperCase)
                .map(s -> s + ":" + s.length());
    }

    @Test
    void publisherTest() {
        TestPublisher<String> publisher = TestPublisher.create();
        Flux<String> flux = publisher.flux();

        StepVerifier.create(flux.transform(processor()))
                .then(() -> publisher.emit("Hola", "Saludos"))
                .expectNext("HOLA:4")
                .expectNext("SALUDOS:7")
                .verifyComplete();
    }
}
