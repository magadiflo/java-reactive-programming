package dev.magadiflo.app.sec10;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

// Recopilar items en función del valor interno / tamaño dado
public class Lec01Buffer {
    public static void main(String[] args) {
        demo1();

        Util.sleepSeconds(60);
    }

    private static void demo1() {
        eventStream() //Flux<String>
                .buffer()//Flux<List<String>> intentará recoger todos los elementos
                .subscribe(Util.subscriber());
    }

    private static void demo2() {
        eventStream()
                .buffer(3)//Le decimos que recoja cada 3 items
                .subscribe(Util.subscriber());
    }

    private static void demo3() {
        eventStream()
                .buffer(Duration.ofMillis(500))//Le decimos que recoja items dentro de 500 milisegundos
                .subscribe(Util.subscriber());
    }

    private static void demo4() {
        eventStream()
                .bufferTimeout(3, Duration.ofSeconds(1))//Le decimos que tome 3 elementos y que espere como máximo 1 segundo
                .subscribe(Util.subscriber());
    }

    // Simulando flujo de eventos
    private static Flux<String> eventStream() {
        return Flux.interval(Duration.ofMillis(200))
                .map(value -> "evento-" + (value + 1));
    }
}
