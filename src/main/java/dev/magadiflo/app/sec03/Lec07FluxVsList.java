package dev.magadiflo.app.sec03;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec03.helper.NameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.List;

public class Lec07FluxVsList {

    private static final Logger log = LoggerFactory.getLogger(Lec07FluxVsList.class);

    public static void main(String[] args) {
        List<String> namesList = NameGenerator.getNamesList(5); //Somos bloqueados por 5 segundos. Retorna la cantidad solicitada o nada
        log.info(String.valueOf(namesList));

        Flux<String> namesFlux = NameGenerator.getNamesFlux(5); //Aquí fácilmente se puede cancelar luego del primer resultado
        namesFlux.subscribe(Util.subscriber());
    }
}
