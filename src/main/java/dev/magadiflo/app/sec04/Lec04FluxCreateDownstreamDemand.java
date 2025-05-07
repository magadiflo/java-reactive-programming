package dev.magadiflo.app.sec04;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.pubsub.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class Lec04FluxCreateDownstreamDemand {
    private static final Logger log = LoggerFactory.getLogger(Lec04FluxCreateDownstreamDemand.class);

    public static void main(String[] args) {
        produceOnDemand();
    }

    private static void produceOnDemand() {
        SubscriberImpl subscriber = new SubscriberImpl();

        Flux<String> fluxCreate = Flux.create(fluxSink -> {
            fluxSink.onRequest(value -> {
                for (int i = 0; i < value && !fluxSink.isCancelled(); i++) {
                    String name = Util.faker().name().firstName();
                    log.info("generado-onDemand: {}", name);
                    fluxSink.next(name);
                }
            });
        });

        fluxCreate.subscribe(subscriber);
        subscriber.getSubscription().request(2);
        subscriber.getSubscription().request(2);
        subscriber.getSubscription().cancel();
    }

    private static void produceEarlyDefault() {
        SubscriberImpl subscriber = new SubscriberImpl();

        Flux.<String>create(fluxSink -> {
            for (int i = 0; i < 10; i++) {
                String name = Util.faker().name().firstName();
                log.info("generado: {}", name);
                fluxSink.next(name);
            }
            fluxSink.complete();
        }).subscribe(subscriber);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);

        Util.sleepSeconds(2);
        subscriber.getSubscription().request(2);

        subscriber.getSubscription().cancel();
    }
}
