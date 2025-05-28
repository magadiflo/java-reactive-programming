package dev.magadiflo.app.sec13;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

public class Lec03ContextPropagation {
    private static final Logger log = LoggerFactory.getLogger(Lec03ContextPropagation.class);

    public static void main(String[] args) {
        demo2();
        Util.sleepSeconds(2);
    }

    // Todos los productores reciben el contexto
    public static void demo1() {
        getWelcomeMessage()
                .concatWith(Flux.merge(producer1(), producer2()))
                .contextWrite(Context.of("user", "magadiflo"))
                .subscribe(Util.subscriber());
    }

    // Supongamos que el producer2 no debe recibir el contexto
    public static void demo2() {
        getWelcomeMessage()
                .concatWith(Flux.merge(producer1(), producer2().contextWrite(context -> Context.empty())))
                .contextWrite(Context.of("user", "magadiflo"))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getWelcomeMessage() {
        return Mono.deferContextual(ctx -> {
            log.info("{}", ctx);
            if (ctx.hasKey("user")) {
                return Mono.just("Bienvenido %s".formatted(ctx.get("user").toString()));
            }
            return Mono.error(new RuntimeException("No autenticado"));
        });
    }

    private static Mono<String> producer1() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer1: {}", ctx);
            return Mono.empty();
        }).subscribeOn(Schedulers.boundedElastic()); // Hace uso de su propio pool de hilos
    }

    private static Mono<String> producer2() {
        return Mono.<String>deferContextual(ctx -> {
            log.info("producer2: {}", ctx);
            return Mono.empty();
        }).subscribeOn(Schedulers.parallel()); // Hace uso de su propio pool de hilos
    }
}
