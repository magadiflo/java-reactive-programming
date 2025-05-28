package dev.magadiflo.app.sec13.client;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RateLimiter {
    private static final Map<String, Integer> categoryAttemps = Collections.synchronizedMap(new HashMap<>());

    static {
        refresh();
    }

    static <T> Mono<T> limitCalls() {
        return Mono.deferContextual(ctx -> {
            boolean allowCall = ctx.<String>getOrEmpty("category")
                    .map(RateLimiter::canAllow)
                    .orElseGet(() -> false);
            return allowCall ? Mono.empty() : Mono.error(new RuntimeException("Superó el límite dado"));
        });
    }

    private static synchronized boolean canAllow(String category) {
        Integer attemps = categoryAttemps.getOrDefault(category, 0);
        if (attemps > 0) {
            categoryAttemps.put(category, attemps - 1);
            return true;
        }
        return false;
    }

    private static void refresh() {
        Flux.interval(Duration.ofSeconds(5)) // El primer valor lo emite después de 5 segundos, por eso nos apoyamos el startWith(...)
                .startWith(0L)                      // Logramos emitir el primer valor al instante
                .subscribe(value -> {
                    categoryAttemps.put("standard", 2);
                    categoryAttemps.put("premium", 3);
                });
    }
}
