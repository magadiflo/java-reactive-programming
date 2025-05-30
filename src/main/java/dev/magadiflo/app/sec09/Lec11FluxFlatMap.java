package dev.magadiflo.app.sec09;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec09.applications.Order;
import dev.magadiflo.app.sec09.applications.OrderService;
import dev.magadiflo.app.sec09.applications.User;
import dev.magadiflo.app.sec09.applications.UserService;
import reactor.core.publisher.Flux;

public class Lec11FluxFlatMap {

    public static void main(String[] args) {
        // Obtener todas las órdenes desde el servicio de orden
        Flux<Order> orderFlux = UserService.getAllUsers()      //Flux<User>
                .map(User::id)                         //Flux<Integer>
                .flatMap(OrderService::getUserOrders); //Flux<Order>
        orderFlux.subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }
}
