package dev.magadiflo.app.sec13.client;

import reactor.util.context.Context;

import java.util.Map;
import java.util.function.UnaryOperator;

public class UserService {
    private static final Map<String, String> USER_CATEGORY = Map.of(
            "sam", "standard",
            "mike", "premium"
    );

    static UnaryOperator<Context> userCategoryContext() {
        return ctx -> ctx.<String>getOrEmpty("user")
                .filter(USER_CATEGORY::containsKey)
                .map(USER_CATEGORY::get)
                .map(category -> ctx.put("category", category))
                .orElseGet(() -> ctx);
    }
}
