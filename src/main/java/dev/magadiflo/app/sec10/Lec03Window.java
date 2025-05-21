package dev.magadiflo.app.sec10;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Lec03Window {

    public static void main(String[] args) {
        demo1();
        Util.sleepSeconds(60);
    }

    private static void demo1() {
        eventStream()
                .window(5)
                .flatMap(Lec03Window::processEvents)
                .subscribe();
    }

    private static void demo2() {
        eventStream()
                .window(Duration.ofMillis(1800))
                .flatMap(Lec03Window::processEvents)
                .subscribe();
    }

    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(500))
                .map(value -> "evento-" + (value + 1));
    }

    private static Mono<Void> processEvents(Flux<String> flux) {
        return flux.doOnNext(value -> System.out.print("*"))
                .doOnComplete(System.out::println)
                .then();
    }
}
