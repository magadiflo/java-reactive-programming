package dev.magadiflo.app.sec09;

import dev.magadiflo.app.common.Util;
import dev.magadiflo.app.sec09.applications.PaymentService;
import dev.magadiflo.app.sec09.applications.UserService;

// Llamadas de E/S secuenciales sin bloqueo.
// flatMap se utiliza para aplanar el publisher interno o para suscribirse a él.
public class Lec09MonoFlatMap {

    public static void main(String[] args) {
        // Tenemos el username, necesitamos obtener el saldo de la cuenta
        UserService.getUserId("sam")
                .flatMap(PaymentService::getUserBalance)
                .subscribe(Util.subscriber());
    }
}
