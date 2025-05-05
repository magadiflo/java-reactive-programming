package dev.magadiflo.app.sec02;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Mono;

public class Lec04MonoEmptyError {
    public static void main(String[] args) {
        getUsername(2).subscribe(Util.subscriber("Ejem1"));
        getUsername(3).subscribe(Util.subscriber("Ejem2"));
    }

    private static Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("Marti");
            case 2 -> Mono.empty();
            default -> Mono.error(new RuntimeException("Entrada inválida"));
        };
    }
}
