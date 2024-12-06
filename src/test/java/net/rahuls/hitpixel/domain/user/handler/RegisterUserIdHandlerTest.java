package net.rahuls.hitpixel.domain.user.handler;

import net.rahuls.hitpixel.api.dto.UserDto;
import net.rahuls.hitpixel.core.actions.UserAction;
import net.rahuls.hitpixel.core.exceptions.InvalidInputException;
import net.rahuls.hitpixel.data.entity.Role;
import net.rahuls.hitpixel.data.entity.User;
import net.rahuls.hitpixel.data.repository.RoleRepository;
import net.rahuls.hitpixel.data.repository.UserRepository;
import net.rahuls.hitpixel.domain.user.mapper.UserMapper;
import net.rahuls.hitpixel.domain.user.mapper.UserMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserIdHandlerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    RoleRepository roleRepository;

    @Spy
    UserMapper userMapper = new UserMapperImpl();

    @InjectMocks
    RegisterUserHandler registerUserHandler;

    @Test
    void getActionType() {
        assertEquals(UserAction.REGISTER, registerUserHandler.getActionType());
    }

    @Test
    void userAlreadyExists_handle() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@test.com");
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(InvalidInputException.class, () -> registerUserHandler.handle(userDto));
    }

    @Test
    void handle() {
        Role mockRole = new Role();
        mockRole.setName("USER");
        when(roleRepository.getUserRole()).thenReturn(Optional.of(mockRole));

        when(passwordEncoder.encode("password")).thenReturn("$password$");

        User mockUser = new User();
        mockUser.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        UserDto input = new UserDto();
        input.setUsername("username");
        input.setPassword("password");
        registerUserHandler.handle(input);

        verify(userRepository).save(any(User.class));
    }
}