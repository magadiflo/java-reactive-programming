package dev.magadiflo.app.sec06;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec06.assignment.ExternalServiceClient;
import dev.magadiflo.app.sec06.assignment.InventoryService;
import dev.magadiflo.app.sec06.assignment.RevenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec06Assignment {

    private static final Logger log = LoggerFactory.getLogger(Lec06Assignment.class);

    public static void main(String[] args) {
        ExternalServiceClient externalServiceClient = new ExternalServiceClient();
        InventoryService inventoryService = new InventoryService();
        RevenueService revenueService = new RevenueService();

        externalServiceClient.orderStream()
                .subscribe(inventoryService::consume);
        externalServiceClient.orderStream()
                .subscribe(revenueService::consume);

        inventoryService.stream()
                .subscribe(Util.subscriber("inventory"));
        revenueService.stream()
                .subscribe(Util.subscriber("revenue"));

        Util.sleepSeconds(30);
    }
}
