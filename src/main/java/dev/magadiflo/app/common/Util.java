package dev.magadiflo.app.common;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.UnaryOperator;

public class Util {

    private static final Logger log = LoggerFactory.getLogger(Util.class);
    private static final Faker faker = Faker.instance();

    private Util() {
    }

    public static <T> Subscriber<T> subscriber() {
        return new DefaultSubscriber<>("");
    }

    public static <T> Subscriber<T> subscriber(String name) {
        return new DefaultSubscriber<>(name);
    }

    public static Faker faker() {
        return faker;
    }

    public static void sleepSeconds(long seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(seconds));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> UnaryOperator<Flux<T>> fluxLogger(String name) {
        return flux -> flux
                .doOnSubscribe(subscription -> log.info("Subscribiendo al {}", name))
                .doOnCancel(() -> log.info("Cancelando {}", name))
                .doOnComplete(() -> log.info("{} completado", name));
    }
}
