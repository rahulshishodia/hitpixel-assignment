package net.rahuls.hitpixel.core;

import net.rahuls.hitpixel.api.RequestPayload;
import net.rahuls.hitpixel.api.ResponsePayload;
import net.rahuls.hitpixel.api.dto.LoginDto;
import net.rahuls.hitpixel.core.actions.Action;
import net.rahuls.hitpixel.core.actions.UserAction;
import net.rahuls.hitpixel.core.exceptions.HandlerNotFoundException;
import net.rahuls.hitpixel.domain.user.handler.LoginUserHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ActionHandlerFactoryTest {

    private ActionHandlerFactory actionHandlerFactory;

    @Mock
    private LoginUserHandler loginUserHandler;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        lenient().when(loginUserHandler.getActionType()).thenReturn(UserAction.LOGIN);

        lenient().when(loginUserHandler.handle(any(LoginDto.class))).thenReturn("token");

        Map<String, RequestHandler<?, ?>> map = Map.of(
                UserAction.LOGIN.name(), loginUserHandler
        );
        actionHandlerFactory = new ActionHandlerFactory(map);

    }

    @Test
    void testGetHandler_whenHandlerExists() {
        ResponsePayload<Object> handler = actionHandlerFactory.handle(new RequestPayload<>(UserAction.LOGIN, null));
        assertNotNull(handler);
    }

    @Test
    void testGetHandler_whenHandlerDoesNotExist() {
        Action unknownAction = () -> "INVALID_ACTION";
        RequestPayload<Object> handler = new RequestPayload<>(unknownAction);
        assertThrows(HandlerNotFoundException.class, () -> actionHandlerFactory.handle(handler));
    }

    @Test
    void testHandle_whenHandlerExists() {
        RequestPayload<LoginDto> payload = new RequestPayload<>(UserAction.LOGIN, new LoginDto());
        ResponsePayload<String> response = actionHandlerFactory.handle(payload);

        assertNotNull(response);
        assertEquals("token", response.data());
    }

    @Test
    void testHandle_whenHandlerDoesNotExist() {
        Action unknownAction = () -> "INVALID_ACTION";
        RequestPayload<String> payload = new RequestPayload<>(unknownAction, "data");

        assertThrows(HandlerNotFoundException.class, () -> actionHandlerFactory.handle(payload));
    }
}
