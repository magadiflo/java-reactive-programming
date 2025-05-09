package dev.magadiflo.app.sec05;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec03DoCallbacks {

    private static final Logger log = LoggerFactory.getLogger(Lec03DoCallbacks.class);

    public static void main(String[] args) {
        Flux.<Integer>create(fluxSink -> {
                    log.info("Inicio del productor");
                    for (int i = 0; i < 4; i++) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                    // fluxSink.error(new RuntimeException("Ocurrió un error dentro del Flux.create()"));
                    log.info("Fin del productor");
                })
                .doOnComplete(() -> log.info("doOnComplete-1"))
                .doFirst(() -> log.info("doFirst-1"))
                .doOnNext(item -> log.info("doOnNext-1: {}", item))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-1: {}", subscription))
                .doOnRequest(request -> log.info("doOnRequest-1: {}", request))
                .doOnError(error -> log.info("doOnError-1: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("doOnTerminate-1")) // Se activa cuando finaliza Flux, ya sea al completarse exitosamente o al fallar con un error.
                .doOnCancel(() -> log.info("doOnCancel-1"))
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-1: {}", o))
                .doFinally(signal -> log.info("doFinally-1: {}", signal)) // Se activa tras la finalización del Flux por cualquier motivo, incluida la cancelación.
                 .take(2)
                .doOnComplete(() -> log.info("doOnComplete-2"))
                .doFirst(() -> log.info("doFirst-2"))
                .doOnNext(item -> log.info("doOnNext-2: {}", item))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-2: {}", subscription))
                .doOnRequest(request -> log.info("doOnRequest-2: {}", request))
                .doOnError(error -> log.info("doOnError-2: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("doOnTerminate-2"))
                .doOnCancel(() -> log.info("doOnCancel-2"))
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-2: {}", o))
                .doFinally(signal -> log.info("doFinally-2: {}", signal))
                //.take(4)
                .subscribe(Util.subscriber("subscriber"));
    }
}
