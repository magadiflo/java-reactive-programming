package dev.magadiflo.app.sec12;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

//Podemos enviar varios mensajes, pero solo habrá un suscriptor.
public class Lec02SinkUnicast {
    private static final Logger log = LoggerFactory.getLogger(Lec02SinkUnicast.class);

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        //manija a través de la cual empujaríamos los elementos
        //onBackPressureBuffer - cola ilimitada
        Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();

        //Manejador a través del cual los suscriptores recibirán elementos
        Flux<Object> flux = sink.asFlux();

        sink.tryEmitNext("Hola");
        sink.tryEmitNext("Buen día");
        sink.tryEmitNext("¿Cómo estás?");

        flux.subscribe(Util.subscriber());
    }

    // Al unicast no le gustan los suscriptores múltiples, solo permitirá un suscriptor
    private static void demo2() {
        Sinks.Many<Object> sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<Object> flux = sink.asFlux();

        sink.tryEmitNext("Hola");
        sink.tryEmitNext("Buen día");
        sink.tryEmitNext("¿Cómo estás?");

        flux.subscribe(Util.subscriber("sam"));
        flux.subscribe(Util.subscriber("mike"));
    }
}
