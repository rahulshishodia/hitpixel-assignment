package net.rahuls.hitpixel.api.resources;

import net.rahuls.hitpixel.BaseIntegrationTest;
import net.rahuls.hitpixel.api.ResponsePayload;
import net.rahuls.hitpixel.api.dto.LoginDto;
import net.rahuls.hitpixel.api.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


class UserResourceTest extends BaseIntegrationTest {

    @Test
    void register() {
        ResponseEntity<ResponsePayload> response = restTemplate.postForEntity("/api/users/register", getUser(), ResponsePayload.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().success());
    }

    @Test
    void login() {
        LoginDto login = new LoginDto("john.doe@test.com", "Password@123");

        ResponseEntity<ResponsePayload> response = restTemplate.postForEntity("/api/users/login", login, ResponsePayload.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody().data());
    }

    private static UserDto getUser() {
        UserDto userDto = new UserDto();
        userDto.setFullName("John Doe");
        userDto.setUsername("john.doe");
        userDto.setEmail("john.doe@test.com");
        userDto.setPassword("Password@123");
        return userDto;
    }
}