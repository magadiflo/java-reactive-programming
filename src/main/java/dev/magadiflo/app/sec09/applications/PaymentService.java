package dev.magadiflo.app.sec09.applications;

import reactor.core.publisher.Mono;

import java.util.Map;

//Solo para demostración.
//- Imagine que payment-service, como aplicación, tiene un punto final.
//- Esta es una clase cliente que representa la llamada a esos dos puntos finales (solicitudes de E/S).
public class PaymentService {
    private static final Map<Integer, Integer> userBalanceTable = Map.of(
            1, 100,
            2, 200,
            3, 300
    );

    public static Mono<Integer> getUserBalance(Integer userId) {
        return Mono.fromSupplier(() -> userBalanceTable.get(userId));
    }
}
