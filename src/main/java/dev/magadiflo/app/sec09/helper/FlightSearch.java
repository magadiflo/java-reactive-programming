package dev.magadiflo.app.sec09.helper;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class FlightSearch {
    public static Flux<Flight> getFlights() {
        return Flux.merge(
                AmericanAirlines.getFlights(),
                Emirates.getFlights(),
                Qatar.getFlights()
        ).take(Duration.ofSeconds(2)); // Retransmite valores de este Flujo hasta que transcurra la Duración especificada.
    }
}
