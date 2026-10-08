package dev.magadiflo.app.sec12;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class Lec03SinkThreadSafety {
    private static final Logger log = LoggerFactory.getLogger(Lec03SinkThreadSafety.class);

    public static void main(String[] args) {
        demo2();
    }

    private static void demo1() {
        // Sink unicast con cola ilimitada: es la "manija" por la que empujamos elementos.
        Sinks.Many<Integer> sink = Sinks.many().unicast().onBackpressureBuffer();
        // Flux por el que el único suscriptor recibirá los elementos del sink.
        Flux<Integer> flux = sink.asFlux();

        // ArrayList NO es thread-safe, y se usa a propósito: queremos probar el sink, no la lista.
        // Si el sink permitiera que dos hilos ejecutaran recibidos::add al mismo tiempo,
        // la lista se corrompería (elementos perdidos o una excepción).
        // Como el sink serializa la entrega, solo un hilo a la vez ejecuta recibidos::add.
        List<Integer> recibidos = new ArrayList<>();

        // Nos suscribimos ANTES de emitir. Cada elemento aceptado por el sink llega aquí.
        flux.subscribe(recibidos::add);

        // Contador thread-safe de emisiones rechazadas por concurrencia.
        // Es AtomicInteger porque lo incrementan muchos hilos a la vez.
        AtomicInteger rechazadas = new AtomicInteger();

        // Guardamos cada tarea para poder esperar a que todas terminen.
        List<CompletableFuture<Void>> tareas = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            // Una variable usada dentro de una lambda debe ser final o efectivamente final.
            // "i" cambia en cada vuelta, por eso se copia a "j".
            int j = i;

            // runAsync ejecuta la tarea en el pool ForkJoin: muchas emisiones salen en paralelo
            // y todas comparten el MISMO sink. Esa es la situación que queremos probar.
            tareas.add(CompletableFuture.runAsync(() -> {

                // tryEmitNext no bloquea ni reintenta: intenta emitir UNA vez
                // y devuelve el resultado (OK o algún FAIL_*).
                Sinks.EmitResult resultado = sink.tryEmitNext(j);

                // FAIL_NON_SERIALIZED: otro hilo estaba emitiendo en ese instante.
                // El sink rechazó esta emisión y el elemento j NO se entregó.
                if (resultado == Sinks.EmitResult.FAIL_NON_SERIALIZED) {
                    rechazadas.incrementAndGet();
                }
            }));
        }

        // Espera a que terminen las 1000 tareas (mejor que Util.sleepSeconds(2),
        // que solo "espera y confía" en que dos segundos alcancen).
        //
        // tareas.toArray(new CompletableFuture[0])
        //   -> allOf() recibe un array (varargs), no una List, así que convertimos la lista de tareas a array.
        //      El "new CompletableFuture[0]" solo indica el tipo del array; Java lo crea con el tamaño correcto.
        // CompletableFuture.allOf(...)
        //   -> devuelve UN nuevo CompletableFuture que se completa cuando TODAS las tareas del array terminan.
        // .join()
        //   -> bloquea el hilo actual (main) hasta que ese CompletableFuture se complete,
        //      es decir, hasta que las 1000 tareas hayan terminado.
        CompletableFuture.allOf(tareas.toArray(new CompletableFuture[0])).join();

        log.info("Recibidos: {}, rechazadas: {}, total: {}",
                recibidos.size(), rechazadas.get(), recibidos.size() + rechazadas.get());
    }

    private static void demo2() {
        Sinks.Many<Integer> sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<Integer> flux = sink.asFlux();

        List<Integer> recibidos = new ArrayList<>();
        flux.subscribe(recibidos::add);

        List<CompletableFuture<Void>> tareas = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            int j = i;
            tareas.add(CompletableFuture.runAsync(() ->
                    sink.emitNext(j, (signalType, emitResult) -> {
                        // true  -> reintentar
                        // false -> no reintentar
                        return Sinks.EmitResult.FAIL_NON_SERIALIZED.equals(emitResult);
                    })
            ));
        }

        CompletableFuture.allOf(tareas.toArray(new CompletableFuture[0])).join();
        log.info("Tamaño de la lista: {}", recibidos.size());
    }
}
