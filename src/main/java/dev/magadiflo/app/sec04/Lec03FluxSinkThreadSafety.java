package dev.magadiflo.app.sec04;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec04.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

// FluxSink es seguro para subprocesos (FluxSink is thread safe)
public class Lec03FluxSinkThreadSafety {
    private static final Logger log = LoggerFactory.getLogger(Lec03FluxSinkThreadSafety.class);

    public static void main(String[] args) {
        threadSafe();
    }

    private static void threadSafe() {
        List<String> list = new ArrayList<>();
        NameGenerator nameGenerator = new NameGenerator();
        Flux<String> stringFlux = Flux.create(nameGenerator);
        stringFlux.subscribe(list::add);

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                nameGenerator.generate();
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread.ofPlatform().start(runnable);
        }

        Util.sleepSeconds(3);
        log.info("Tamaño del list: {}", list.size());
    }

    private static void notThreadSafe() {
        //Un arrayList no es seguro para hilos
        List<Integer> list = new ArrayList<>();

        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };

        for (int i = 0; i < 10; i++) {
            // En java 17
            //new Thread(runnable).start();

            // En java 21
            Thread.ofPlatform().start(runnable);
        }

        Util.sleepSeconds(3);
        log.info("Tamaño de la lista: {}", list.size());
    }
}
