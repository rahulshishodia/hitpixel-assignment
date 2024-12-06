package net.rahuls.hitpixel.core;

import lombok.extern.slf4j.Slf4j;
import net.rahuls.hitpixel.api.RequestPayload;
import net.rahuls.hitpixel.api.ResponsePayload;
import net.rahuls.hitpixel.core.actions.Action;
import net.rahuls.hitpixel.core.exceptions.HandlerNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ActionHandlerFactory {

    private final Map<Action, RequestHandler<?, ?>> handlers;

    public ActionHandlerFactory(Map<String, RequestHandler<?, ?>> handlerBeans) {
        this.handlers = handlerBeans.values().stream()
                .collect(Collectors.toMap(RequestHandler::getActionType, handler -> handler));
    }

    @SuppressWarnings("unchecked")
    private <T, R> RequestHandler<T, R> getHandler(Action action) {
        RequestHandler<?, ?> handler = handlers.get(action);
        if (handler == null) {
            throw new HandlerNotFoundException("No handler found for action: " + action);
        }
        return (RequestHandler<T, R>) handler;
    }

    public <T, R> ResponsePayload<R> handle(RequestPayload<T> payload) {
        log.info("Received request payload: {} for action {}", payload.data(), payload.action().name());
        RequestHandler<T, R> handler = getHandler(payload.action());
        return ResponsePayload.ok(handler.handle(payload.data()));
    }
}
