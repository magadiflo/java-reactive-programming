package dev.magadiflo.app.sec09.helper;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

// Representa la clase cliente para llamar a un servicio remoto
public class Qatar {
    private static final String AIRLINE = "Qatar";

    public static Flux<Flight> getFlights() {
        return Flux.range(1, Util.faker().random().nextInt(3, 5))
                .delayElements(Duration.ofMillis(Util.faker().random().nextInt(300, 800)))
                .map(value -> new Flight(AIRLINE, Util.faker().random().nextInt(400, 900)))
                .transform(Util.fluxLogger(AIRLINE));
    }
}
