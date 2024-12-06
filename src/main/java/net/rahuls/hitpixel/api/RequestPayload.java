package net.rahuls.hitpixel.api;

import net.rahuls.hitpixel.core.actions.Action;

public record RequestPayload<T>(Action action, T data) {

    public RequestPayload(Action action) {
        this(action, null);
    }
}
