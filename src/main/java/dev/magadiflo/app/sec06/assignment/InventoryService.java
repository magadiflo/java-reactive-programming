package dev.magadiflo.app.sec06.assignment;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class InventoryService implements OrderProcessor {
    private final Map<String, Integer> db = new HashMap<>();

    @Override
    public void consume(Order order) {
        Integer currentInventory = this.db.getOrDefault(order.category(), 500);
        int updateInventory = currentInventory - order.quantity();
        db.put(order.category(), updateInventory);
    }

    @Override
    public Flux<String> stream() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(value -> this.db.toString());
    }
}
