package dev.magadiflo.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class Lec02EmptyErrorTest {
    private static final Logger log = LoggerFactory.getLogger(Lec02EmptyErrorTest.class);

    private Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("Marti");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("Entrada inválida"));
        };
    }

    @Test
    void userTestSuccess() {
        // given
        int id = 1;

        // when
        Mono<String> user = getUsername(id);

        // then
        StepVerifier.create(user)
                .expectNext("Marti")
                .expectComplete()
                .verify();
    }

    @Test
    void userTestEmpty() {
        // given
        int id = 2;

        // when
        Mono<String> user = getUsername(id);

        // then
        StepVerifier.create(user)
                .expectComplete()
                .verify();
    }

    @Test
    void userTestError1() {
        // given
        int id = 3;

        // when
        Mono<String> user = getUsername(id);

        // then
        StepVerifier.create(user)
                .expectError()
                .verify();
    }

    @Test
    void userTestError2() {
        // given
        int id = 3;

        // when
        Mono<String> user = getUsername(id);

        // then
        StepVerifier.create(user)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void userTestError3() {
        // given
        int id = 3;

        // when
        Mono<String> user = getUsername(id);

        // then
        StepVerifier.create(user)
                .expectErrorMessage("Entrada inválida")
                .verify();
    }

    @Test
    void userTestError4() {
        // given
        int id = 3;

        // when
        Mono<String> user = getUsername(id);

        // then
        StepVerifier.create(user)
                .expectErrorSatisfies(throwable -> {
                    Assertions.assertEquals(RuntimeException.class, throwable.getClass());
                    Assertions.assertEquals("Entrada inválida", throwable.getMessage());
                })
                .verify();
    }

    @Test
    void userTestError5() {
        // given
        int id = 3;

        // when
        Mono<String> user = getUsername(id);

        // then
        StepVerifier.create(user)
                .consumeErrorWith(throwable -> {
                    Assertions.assertEquals(RuntimeException.class, throwable.getClass());
                    Assertions.assertEquals("Entrada inválida", throwable.getMessage());
                })
                .verify();
    }
}
