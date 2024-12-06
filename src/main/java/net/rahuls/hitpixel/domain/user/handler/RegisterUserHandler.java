package net.rahuls.hitpixel.domain.user.handler;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.rahuls.hitpixel.api.dto.UserDto;
import net.rahuls.hitpixel.core.RequestHandler;
import net.rahuls.hitpixel.core.actions.Action;
import net.rahuls.hitpixel.core.actions.UserAction;
import net.rahuls.hitpixel.core.exceptions.InvalidInputException;
import net.rahuls.hitpixel.data.entity.User;
import net.rahuls.hitpixel.data.repository.RoleRepository;
import net.rahuls.hitpixel.data.repository.UserRepository;
import net.rahuls.hitpixel.domain.user.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class RegisterUserHandler implements RequestHandler<UserDto, Void> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public Action getActionType() {
        return UserAction.REGISTER;
    }

    @Transactional
    @Override
    public Void handle(UserDto userDto) {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new InvalidInputException("Email already exists");
        }

        User user = userMapper.toUser(userDto);

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        roleRepository.getUserRole()
                .ifPresent(userRole -> user.setRoles(Set.of(userRole)));

        userRepository.save(user);
        return null;
    }
}
