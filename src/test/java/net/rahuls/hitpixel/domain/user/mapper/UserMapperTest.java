package net.rahuls.hitpixel.domain.user.mapper;

import net.rahuls.hitpixel.api.dto.UserDto;
import net.rahuls.hitpixel.data.entity.Role;
import net.rahuls.hitpixel.data.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @Test
    void invalidUser_toUserDto() {
        assertNull(userMapper.toUserDto(null));
    }

    @Test
    void invalidUserDto_toUser() {
        assertNull(userMapper.toUser(null));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "USER")
    void validUser_toUserDto(String roleName) {
        Set<Role> userRoles = null;
        if (roleName != null) {
            userRoles = Set.of(new Role(roleName, ""));
        }

        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setFullName("Full Name");
        user.setEmail("email@email.com");
        user.setPassword("password");
        user.setEnabled(false);
        user.setCredentialsNonExpired(false);
        user.setAccountNonLocked(false);
        user.setAccountNonExpired(false);
        user.setRoles(userRoles);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        UserDto userDto = userMapper.toUserDto(user);
        assertEquals(1L, userDto.getId());
        assertEquals("username", userDto.getUsername());
        assertEquals("Full Name", userDto.getFullName());
        assertEquals("email@email.com", userDto.getEmail());
        assertNull(userDto.getPassword());
    }

    @Test
    void validUserDto_toUser() {

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("username");
        userDto.setFullName("Full Name");
        userDto.setEmail("email@email.com");
        userDto.setPassword("password");

        User user = userMapper.toUser(userDto);

        assertEquals(1L, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("Full Name", user.getFullName());
        assertEquals("email@email.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertTrue(user.isEnabled());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isAccountNonExpired());
        assertNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());
    }

}