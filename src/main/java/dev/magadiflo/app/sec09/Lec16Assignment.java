package dev.magadiflo.app.sec09;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec09.applications.Order;
import dev.magadiflo.app.sec09.applications.OrderService;
import dev.magadiflo.app.sec09.applications.PaymentService;
import dev.magadiflo.app.sec09.applications.User;
import dev.magadiflo.app.sec09.applications.UserService;
import reactor.core.publisher.Mono;

import java.util.List;

// Obtener todos los usuarios y cree 1 objeto del tipo UserInformation
public class Lec16Assignment {

    record UserInformation(Integer userId, String username, Integer balance, List<Order> orders) {
    }

    public static void main(String[] args) {
        UserService.getAllUsers()
                .flatMap(Lec16Assignment::getUserInformation)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }

    private static Mono<UserInformation> getUserInformation(User user) {
        Mono<Integer> userBalanceMono = PaymentService.getUserBalance(user.id());
        Mono<List<Order>> orderListMono = OrderService.getUserOrders(user.id()).collectList();

        return Mono.zip(userBalanceMono, orderListMono)
                .map(tuple -> {
                    Integer balance = tuple.getT1();
                    List<Order> orders = tuple.getT2();
                    return new UserInformation(user.id(), user.username(), balance, orders);
                });
    }
}
