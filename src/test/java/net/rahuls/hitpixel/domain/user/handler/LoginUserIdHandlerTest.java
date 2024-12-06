package net.rahuls.hitpixel.domain.user.handler;

import net.rahuls.hitpixel.api.dto.LoginDto;
import net.rahuls.hitpixel.config.security.JwtUtil;
import net.rahuls.hitpixel.core.actions.UserAction;
import net.rahuls.hitpixel.core.exceptions.InvalidInputException;
import net.rahuls.hitpixel.data.entity.User;
import net.rahuls.hitpixel.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUserIdHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private LoginUserHandler loginUserHandler;

    @Test
    void actionType() {
        assertEquals(UserAction.LOGIN, loginUserHandler.getActionType());
    }

    @Test
    void handle_InvalidEmail() {
        LoginDto loginDto = new LoginDto();
        assertThrows(InvalidInputException.class, () -> loginUserHandler.handle(loginDto));
    }

    @Test
    void handle_InvalidPassword() {
        User mockUser = new User();
        mockUser.setEmail("test@test.com");
        mockUser.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        LoginDto loginDto = new LoginDto("test@test.com", "password");
        assertNull(loginUserHandler.handle(loginDto));
    }

    @Test
    void handle() {
        User mockUser = new User();
        mockUser.setEmail("test@test.com");
        mockUser.setPassword("password");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateToken(anyString())).thenReturn("token");

        LoginDto loginDto = new LoginDto("test@test.com", "password");
        assertEquals("token", loginUserHandler.handle(loginDto));
    }
}