package dev.magadiflo.app.sec03;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Lec12MonoFluxConversion {
    public static void main(String[] args) {
        monoToFlux();
        fluxToMono();
    }

    private static void fluxToMono() {
        Flux<Integer> range = Flux.range(1, 5);
        Mono<Integer> mono = Mono.from(range); //range.next()
        mono.subscribe(Util.subscriber());
    }

    private static void monoToFlux() {
        Mono<String> username = getUsername(1);
        Flux<String> from = Flux.from(username);
        save(from);
    }

    public static Mono<String> getUsername(int userId) {
        return switch (userId) {
            case 1 -> Mono.just("Martín");
            case 2 -> Mono.empty();
            default -> Mono.error(() -> new RuntimeException("Entrada inválida"));
        };
    }

    private static void save(Flux<String> flux) {
        flux.subscribe(Util.subscriber());
    }
}
