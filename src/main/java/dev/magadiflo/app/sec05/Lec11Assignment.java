package dev.magadiflo.app.sec05;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec05.assignment.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec11Assignment {

    private static final Logger log = LoggerFactory.getLogger(Lec11Assignment.class);

    public static void main(String[] args) {
        ExternalServiceClient externalServiceClient = new ExternalServiceClient();
        for (int i = 1; i <= 4; i++) {
            externalServiceClient.getProductName(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(3);
    }
}
