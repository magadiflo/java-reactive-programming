package dev.magadiflo.app.sec02;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class Lec06MonoFromCallable {

    private static final Logger log = LoggerFactory.getLogger(Lec06MonoFromCallable.class);

    public static void main(String[] args) {
        Mono.fromCallable(() -> getData())
                .subscribe(Util.subscriber());
    }

    private static String getData() throws Exception {
        log.info("Obteniendo datos...");
        double random = Math.random();
        if (random > 0.5) {
            throw new Exception("Falló en la obtención de datos. random=%f".formatted(random));
        }
        return "Datos obtenidos correctamente. random=%f".formatted(random);
    }

}
