package dev.magadiflo.app.sec11;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec11.assignment.ExternalServiceClient;
import dev.magadiflo.app.sec11.assignment.ServerError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.util.retry.Retry;

import java.time.Duration;

public class Lec03ExternalServiceDemo {
    private static final Logger log = LoggerFactory.getLogger(Lec03ExternalServiceDemo.class);

    public static void main(String[] args) {
        retry();

        Util.sleepSeconds(60);
    }

    private static void repeat() {
        ExternalServiceClient client = new ExternalServiceClient();
        client.getCountry()
                .repeat()
                .takeUntil(country -> country.equalsIgnoreCase("peru"))
                .subscribe(Util.subscriber());
    }

    private static void retry() {
        ExternalServiceClient client = new ExternalServiceClient();
        client.getProductName(2)
                .retryWhen(retryOnServerError())
                .subscribe(Util.subscriber());
    }

    private static Retry retryOnServerError() {
        return Retry.fixedDelay(20, Duration.ofSeconds(1))
                .filter(throwable -> ServerError.class.equals(throwable.getClass()))
                .doBeforeRetry(retrySignal -> log.info("reintentando {}", retrySignal.failure().getMessage()));
    }
}
