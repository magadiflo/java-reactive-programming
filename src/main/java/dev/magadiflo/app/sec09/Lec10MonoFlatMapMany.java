package dev.magadiflo.app.sec09;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec09.applications.Order;
import dev.magadiflo.app.sec09.applications.OrderService;
import dev.magadiflo.app.sec09.applications.UserService;
import reactor.core.publisher.Flux;

// Se supone que Mono es 1 item: ¿qué pasa si flatMap devuelve varios items?
public class Lec10MonoFlatMapMany {

    public static void main(String[] args) {
        // Tenemos nombre de usuario, obtenemos todos los pedidos de usuario
        Flux<Order> orders = UserService.getUserId("sam")   //Mono<Integer>
                .flatMapMany(OrderService::getUserOrders);    //Flux<Order>
        orders.subscribe(Util.subscriber());

        Util.sleepSeconds(2);
    }
}
