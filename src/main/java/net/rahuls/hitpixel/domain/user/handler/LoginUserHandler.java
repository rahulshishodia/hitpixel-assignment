package net.rahuls.hitpixel.domain.user.handler;

import lombok.RequiredArgsConstructor;
import net.rahuls.hitpixel.api.dto.LoginDto;
import net.rahuls.hitpixel.config.security.JwtUtil;
import net.rahuls.hitpixel.core.RequestHandler;
import net.rahuls.hitpixel.core.actions.Action;
import net.rahuls.hitpixel.core.actions.UserAction;
import net.rahuls.hitpixel.core.exceptions.InvalidInputException;
import net.rahuls.hitpixel.data.entity.User;
import net.rahuls.hitpixel.data.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LoginUserHandler implements RequestHandler<LoginDto, String> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Action getActionType() {
        return UserAction.LOGIN;
    }

    @Override
    public String handle(LoginDto requestDto) {

        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new InvalidInputException("Invalid email"));

        boolean passwordMatches = passwordEncoder.matches(requestDto.getPassword(), user.getPassword());

        return passwordMatches ? jwtUtil.generateToken(requestDto.getEmail()) : null;
    }
}
