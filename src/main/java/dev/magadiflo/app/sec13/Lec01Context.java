package dev.magadiflo.app.sec13;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

//El contexto sirve para proporcionar metadatos sobre el request (similar a los headers HTTP)
public class Lec01Context {
    private static final Logger log = LoggerFactory.getLogger(Lec01Context.class);

    public static void main(String[] args) {
        getWelcomeMessage()
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
}
