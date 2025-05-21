package dev.magadiflo.app.sec10;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec10.assignment.BookOrder;
import dev.magadiflo.app.sec10.assignment.RevenueReport;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Lec02BufferAssignment {

    public static void main(String[] args) {
        Set<String> allowedCategories = Set.of("Science fiction", "Fantasy", "Suspense/Thriller");
        orderStream()
                .filter(bookOrder -> allowedCategories.contains(bookOrder.genre()))
                .buffer(Duration.ofSeconds(5))
                .map(Lec02BufferAssignment::generateReport)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

    private static Flux<BookOrder> orderStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(value -> BookOrder.create());
    }

    private static RevenueReport generateReport(List<BookOrder> bookOrders) {
        Map<String, Integer> revenue = bookOrders.stream()
                .collect(Collectors.groupingBy(
                        BookOrder::genre,
                        Collectors.summingInt(BookOrder::price)
                ));
        return new RevenueReport(LocalTime.now().truncatedTo(ChronoUnit.SECONDS), revenue);
    }
}
