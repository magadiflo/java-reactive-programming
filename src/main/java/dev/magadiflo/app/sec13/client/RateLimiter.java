package dev.magadiflo.app.sec13.client;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RateLimiter {
    private static final Map<String, Integer> categoryAttemps = Collections.synchronizedMap(new HashMap<>());

    static {
        categoryAttemps.put("standard", 2);
        categoryAttemps.put("premium", 3);
    }

    static <T> Mono<T> limitCalls() {
        return Mono.deferContextual(ctx -> {
            boolean allowCall = ctx.<String>getOrEmpty("category")
                    .map(RateLimiter::canAllow)
                    .orElseGet(() -> false);
            return allowCall ? Mono.empty() : Mono.error(new RuntimeException("Superó el límite dado"));
        });
    }

    private static boolean canAllow(String category) {
        Integer attemps = categoryAttemps.getOrDefault(category, 0);
        if (attemps > 0) {
            categoryAttemps.put(category, attemps - 1);
            return true;
        }
        return false;
    }
}
