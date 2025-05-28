package dev.magadiflo.app.sec13;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

//El Context es un mapa inmutable. ¡Podemos añadir información adicional!
public class Lec02ContextAppendUpdate {
    private static final Logger log = LoggerFactory.getLogger(Lec02ContextAppendUpdate.class);

    public static void main(String[] args) {
        delete();
    }

    public static void delete() {
        getWelcomeMessage()
                .contextWrite(ctx -> ctx.delete("country").delete("city").delete("color")) // Eliminamos las claves
                .contextWrite(Context.of("color", "red").put("country", "Peru").put("city", "Lima"))
                .contextWrite(Context.of("token", "12345"))
                .contextWrite(Context.of("user", "magadiflo"))
                .subscribe(Util.subscriber());
    }

    public static void update1() {
        getWelcomeMessage()
                .contextWrite(ctx -> Context.empty()) // Quitamos tod del context, lo dejamos vacío
                .contextWrite(Context.of("color", "red").put("country", "Peru").put("city", "Lima"))
                .contextWrite(Context.of("token", "12345"))
                .contextWrite(Context.of("user", "magadiflo"))
                .subscribe(Util.subscriber());
    }

    public static void update2() {
        getWelcomeMessage()
                .contextWrite(Context.of("user", "nophy")) // Actualizamos el contexto del user, ahora será nophy
                .contextWrite(Context.of("color", "red").put("country", "Peru").put("city", "Lima"))
                .contextWrite(Context.of("token", "12345"))
                .contextWrite(Context.of("user", "magadiflo"))
                .subscribe(Util.subscriber());
    }

    public static void update3() {
        getWelcomeMessage()
                .contextWrite(ctx -> ctx.put("user", ctx.get("user").toString().toUpperCase())) // Actualizamos el contexto del user para colocar el valor en mayúscula
                .contextWrite(Context.of("color", "red").put("country", "Peru").put("city", "Lima"))
                .contextWrite(Context.of("token", "12345"))
                .contextWrite(Context.of("user", "magadiflo"))
                .subscribe(Util.subscriber());
    }

    public static void append() {
        getWelcomeMessage()
                .contextWrite(Context.of("color", "red").put("country", "Peru").put("city", "Lima"))
                .contextWrite(Context.of("token", "12345"))
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
