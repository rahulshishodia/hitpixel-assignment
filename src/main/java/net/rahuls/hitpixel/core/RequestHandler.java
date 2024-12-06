package net.rahuls.hitpixel.core;

import net.rahuls.hitpixel.core.actions.Action;

public interface RequestHandler<T, R> {

    Action getActionType();

    R handle(T requestDto);
}
