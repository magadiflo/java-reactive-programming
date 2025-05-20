package dev.magadiflo.app.sec09;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec09.assignment.ExternalServiceClient;
import reactor.core.publisher.Flux;

public class Lec12FluxFlatMapAssignment {

    public static void main(String[] args) {
        ExternalServiceClient externalServiceClient = new ExternalServiceClient();

        Flux.range(1, 10)
                .flatMap(externalServiceClient::getProduct)
                .transform(Util.fluxLogger("assignment"))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }
}
