package dev.magadiflo.app.sec10;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec10.assignment.window.FileWriter;
import reactor.core.publisher.Flux;

import java.nio.file.Path;
import java.time.Duration;

public class Lec04WindowAssignment {

    private static final String FILE_NAME_FORMAT = "src/main/resources/sec10/log%d.txt";

    public static void main(String[] args) {
        eventStream()
                .window(Duration.ofMillis(1800))
                .zipWith(sequential())
                .flatMap(tuple -> {
                    Flux<String> window = tuple.getT1();
                    Integer count = tuple.getT2();
                    return FileWriter.create(window, Path.of(FILE_NAME_FORMAT.formatted(count)));
                })
                .subscribe();

        Util.sleepSeconds(60);
    }

    private static Flux<Integer> sequential() {
        return Flux.generate(
                () -> 1,
                (count, synchronousSink) -> {
                    synchronousSink.next(count);
                    return count + 1;
                }
        );
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(value -> "evento-" + (value + 1));
    }
}
