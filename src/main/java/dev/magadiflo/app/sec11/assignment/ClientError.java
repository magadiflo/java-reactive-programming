package dev.magadiflo.app.sec11.assignment;

public class ClientError extends RuntimeException {
    public ClientError() {
        super("¡Bad request!");
    }
}
