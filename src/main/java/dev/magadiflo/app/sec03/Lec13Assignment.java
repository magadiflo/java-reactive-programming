package dev.magadiflo.app.sec03;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec03.assignment.ExternalServiceClient;
import dev.magadiflo.app.sec03.assignment.StockPriceObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec13Assignment {
    private static final Logger log = LoggerFactory.getLogger(Lec13Assignment.class);

    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();
        StockPriceObserver subscriber = new StockPriceObserver();
        client.getPriceChanges()
                .subscribe(subscriber);

        Util.sleepSeconds(21); //Solo bloqueamos el hilo principal para fines demostrativos
    }
}
