package dev.magadiflo.app;

import dev.magadiflo.app.common.Util;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class Lec04RangeTest {
    private Flux<Integer> getRandomItems() {
        return Flux.range(1, 50)
                .map(i -> Util.faker().random().nextInt(1, 100));
    }

    @Test
    void rangeTest1() {
        // given
        // when
        Flux<Integer> items = getRandomItems();

        // then
        StepVerifier.create(items)
                .expectNextMatches(i -> i > 0 && i < 101)
                .expectNextCount(49)
                .verifyComplete();
    }
}
