package dev.magadiflo.app.sec10.assignment;

import dev.magadiflo.app.common.Util;

public record BookOrder(String genre, String title, Integer price) {
    public static BookOrder create() {
        return new BookOrder(
                Util.faker().book().genre(),
                Util.faker().book().title(),
                Util.faker().random().nextInt(10, 100)
        );
    }
}
