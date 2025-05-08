package dev.magadiflo.app.sec04.helper;

import dev.magadiflo.app.common.Util;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public class NameGenerator implements Consumer<FluxSink<String>> {

    private FluxSink<String> fluxSink;

    @Override
    public void accept(FluxSink<String> fluxSink) {
        this.fluxSink = fluxSink;
    }

    public void generate() {
        this.fluxSink.next(Util.faker().country().name());
    }

    public void complete() {
        this.fluxSink.complete();
    }
}
