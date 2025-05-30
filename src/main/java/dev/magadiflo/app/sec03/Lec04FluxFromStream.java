package dev.magadiflo.app.sec03;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

public class Lec04FluxFromStream {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3);

        Flux<Integer> flux = Flux.fromStream(() -> numbers.stream()); // Abre un nuevo flujo para cada uno de los subscribers

        flux.subscribe(Util.subscriber("sub1"));
        flux.subscribe(Util.subscriber("sub2"));
    }
}
