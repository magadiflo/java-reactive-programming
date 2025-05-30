package dev.magadiflo.app.sec10;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec10.assignment.groupby.OrderProcessingService;
import dev.magadiflo.app.sec10.assignment.groupby.PurchaseOrder;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Lec06GroupByAssignment {
    public static void main(String[] args) {
        orderStream()
                .filter(OrderProcessingService.canProcess())
                .groupBy(PurchaseOrder::category)
                .flatMap(groupedFlux -> groupedFlux
                        .transform(OrderProcessingService.getProcessor(groupedFlux.key())))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

    private static Flux<PurchaseOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(value -> PurchaseOrder.create());
    }
}
