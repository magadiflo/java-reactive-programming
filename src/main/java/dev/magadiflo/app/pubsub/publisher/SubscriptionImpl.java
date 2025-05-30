package dev.magadiflo.app.pubsub.publisher;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionImpl implements Subscription {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionImpl.class);
    private static final int MAX_ITEMS = 10;
    private final Faker faker;
    private final Subscriber<? super String> subscriber;
    private boolean isCancelled;
    private int count = 0;

    public SubscriptionImpl(Subscriber<? super String> subscriber) {
        this.subscriber = subscriber;
        this.faker = Faker.instance();
    }

    @Override
    public void request(long requested) {
        if (this.isCancelled) return;

        log.info("El subscriber ha solicitado {} items", requested);
        if (requested > MAX_ITEMS) {
            this.subscriber.onError(new RuntimeException("Falló la validación"));
            this.isCancelled = true;
            return;
        }

        for (int i = 0; i < requested && this.count < MAX_ITEMS; i++) {
            this.count++;
            this.subscriber.onNext(this.faker.internet().emailAddress());
        }

        if (this.count == MAX_ITEMS) {
            log.info("No más datos para producir");
            this.subscriber.onComplete();
            this.isCancelled = true;
        }
    }

    @Override
    public void cancel() {
        log.info("El subscriber fue cancelado");
        this.isCancelled = true;
    }
}
