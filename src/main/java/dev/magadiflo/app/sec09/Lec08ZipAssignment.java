package dev.magadiflo.app.sec09;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec09.assignment.ExternalServiceClient;

public class Lec08ZipAssignment {
    public static void main(String[] args) {
        ExternalServiceClient externalServiceClient = new ExternalServiceClient();

        for (int i = 1; i <= 10; i++) {
            externalServiceClient.getProduct(i)
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(2);
    }
}
