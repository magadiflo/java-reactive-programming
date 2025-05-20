package dev.magadiflo.app.sec09;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class Lec15Then {

    private static final Logger log = LoggerFactory.getLogger(Lec15Then.class);

    public static void main(String[] args) {
        demo1();

        Util.sleepSeconds(2);
    }

    private static void demo1() {
        saveRecords(List.of("a", "b", "c"))  //Flux<String>
                .then()                                         //Mono<Void>
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        List<String> records = List.of("a", "b", "c");
        saveRecords(records)  //Flux<String>
                .then(sendNotification(records))
                .subscribe(Util.subscriber());
    }

    private static Flux<String> saveRecords(List<String> records) {
        return Flux.fromIterable(records)
                .map(record -> "Guardado " + record)
                .delayElements(Duration.ofMillis(500));
    }

    private static Mono<Void> sendNotification(List<String> records) {
        return Mono.fromRunnable(() -> log.info("Todos estos {} registros se guardaron correctamente", records));
    }
}
