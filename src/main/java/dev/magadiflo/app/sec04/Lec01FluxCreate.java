package dev.magadiflo.app.sec04;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

// A veces podríamos tener algunos requisitos como seguir haciendo algo hasta que se cumpla una condición
public class Lec01FluxCreate {
    public static void main(String[] args) {
        Flux.create(fluxSink -> {
            String country;
            do {
                country = Util.faker().country().name();
                fluxSink.next(country);
            } while (!country.equalsIgnoreCase("Peru"));

            fluxSink.complete();
        }).subscribe(Util.subscriber());
    }
}
