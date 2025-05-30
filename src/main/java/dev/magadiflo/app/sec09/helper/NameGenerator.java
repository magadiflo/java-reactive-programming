package dev.magadiflo.app.sec09.helper;

import dev.magadiflo.app.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {

    private static final Logger log = LoggerFactory.getLogger(NameGenerator.class);
    private final List<String> redis = new ArrayList<>();

    public Flux<String> generateNames() {
        return Flux.generate(synchronousSink -> {
                    log.info("generando nombre");
                    Util.sleepSeconds(1);

                    String name = Util.faker().name().firstName();
                    this.redis.add("[caché] " + name);
                    synchronousSink.next(name);
                })
                .startWith(this.redis) // Emitimos primero los datos en caché
                .cast(String.class);
    }
}
