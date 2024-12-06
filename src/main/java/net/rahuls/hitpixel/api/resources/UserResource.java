package net.rahuls.hitpixel.api.resources;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.rahuls.hitpixel.api.RequestPayload;
import net.rahuls.hitpixel.api.ResponsePayload;
import net.rahuls.hitpixel.api.dto.LoginDto;
import net.rahuls.hitpixel.api.dto.UserDto;
import net.rahuls.hitpixel.core.ActionHandlerFactory;
import net.rahuls.hitpixel.core.actions.UserAction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final ActionHandlerFactory actionHandlerFactory;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponsePayload<Void> register(@Valid @RequestBody UserDto userDto) {
        var payload = new RequestPayload<>(UserAction.REGISTER, userDto);
        return actionHandlerFactory.handle(payload);
    }

    @PostMapping("/login")
    public ResponsePayload<UserDto> login(@Valid @RequestBody LoginDto loginDto) {
        var payload = new RequestPayload<>(UserAction.LOGIN, loginDto);
        return actionHandlerFactory.handle(payload);
    }

}
