package net.rahuls.hitpixel.api;

public record ResponsePayload<T>(boolean success, T data, String message) {

    public static <T> ResponsePayload<T> ok(T data) {
        return new ResponsePayload<>(true, data, null);
    }

    public static ResponsePayload<Void> error(String message) {
        return new ResponsePayload<>(false, null, message);
    }
}
