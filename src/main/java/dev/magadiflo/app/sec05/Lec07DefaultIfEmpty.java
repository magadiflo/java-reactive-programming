package dev.magadiflo.app.sec05;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.Flux;

public class Lec07DefaultIfEmpty {

    public static void main(String[] args) {
        Flux.empty()
                .defaultIfEmpty("Valor predefinido")
                .subscribe(Util.subscriber());
    }
}
