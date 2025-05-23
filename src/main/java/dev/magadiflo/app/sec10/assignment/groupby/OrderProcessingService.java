package dev.magadiflo.app.sec10.assignment.groupby;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class OrderProcessingService {

    private static final Map<String, UnaryOperator<Flux<PurchaseOrder>>> PROCESSOR_MAP = Map.of(
            "Kids", kidsProcessing(),
            "Automotive", automotiveProcessing()
    );

    private static UnaryOperator<Flux<PurchaseOrder>> automotiveProcessing() {
        return flux -> flux
                .map(purchaseOrder -> new PurchaseOrder(
                        purchaseOrder.item(),
                        purchaseOrder.category(),
                        purchaseOrder.price() + 100
                ));
    }

    private static UnaryOperator<Flux<PurchaseOrder>> kidsProcessing() {
        return flux -> flux
                .flatMap(purchaseOrder ->
                        getFreeKidsOrder(purchaseOrder)
                                .flux()
                                .startWith(purchaseOrder)
                );
    }

    private static Mono<PurchaseOrder> getFreeKidsOrder(PurchaseOrder order) {
        return Mono.fromSupplier(() -> new PurchaseOrder(
                order.item() + "-FREE",
                order.category(),
                0
        ));
    }

    public static Predicate<PurchaseOrder> canProcess() {
        return order -> PROCESSOR_MAP.containsKey(order.category());
    }

    public static UnaryOperator<Flux<PurchaseOrder>> getProcessor(String category) {
        return PROCESSOR_MAP.get(category);
    }
}
