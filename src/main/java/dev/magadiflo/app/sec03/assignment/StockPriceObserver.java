package dev.magadiflo.app.sec03.assignment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StockPriceObserver implements Subscriber<Integer> {

    private static final Logger log = LoggerFactory.getLogger(StockPriceObserver.class);
    private int quantity = 0;
    private int balance = 1000;
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(Long.MAX_VALUE);
        this.subscription = subscription;
    }

    @Override
    public void onNext(Integer price) {
        if (price < 90 && this.balance >= price) {
            this.quantity++;
            this.balance -= price;
            log.info("Compró una acción a S/{}, cantidad total {}, saldo restante S/{}", price, this.quantity, this.balance);
        } else if (price > 110 && this.quantity > 0) {
            log.info("Venta de {} cantidades a S/{}", this.quantity, price);
            this.balance = this.balance + (this.quantity * price);
            this.quantity = 0;
            this.subscription.cancel();
            log.info("Ganancia: S/{}", (this.balance - 1000));
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("error: {}", throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("¡Completado!");
    }
}
