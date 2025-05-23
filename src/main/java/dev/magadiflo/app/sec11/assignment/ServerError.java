package dev.magadiflo.app.sec11.assignment;

public class ServerError extends RuntimeException {
    public ServerError() {
        super("¡Server Error!");
    }
}
