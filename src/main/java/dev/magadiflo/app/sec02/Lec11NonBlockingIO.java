package dev.magadiflo.app.sec02;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec02.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec11NonBlockingIO {

    private static final Logger log = LoggerFactory.getLogger(Lec11NonBlockingIO.class);

    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();

        log.info("iniciando");
        for (int i = 1; i <= 5; i++) {
            client.getProductName(i)
                    .subscribe(Util.subscriber());
        }

        //Solo para ver el resultado en consola es necesario bloquear el hilo principal
        Util.sleepSeconds(2);
    }
}
