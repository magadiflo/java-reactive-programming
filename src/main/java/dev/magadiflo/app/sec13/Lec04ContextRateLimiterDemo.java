package dev.magadiflo.app.sec13;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec13.client.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec04ContextRateLimiterDemo {
    private static final Logger log = LoggerFactory.getLogger(Lec04ContextRateLimiterDemo.class);

    public static void main(String[] args) {
        ExternalServiceClient client = new ExternalServiceClient();
        for (int i = 0; i < 10; i++) {
            client.getBook()
                    .subscribe(Util.subscriber());
        }

        Util.sleepSeconds(5);
    }

}
