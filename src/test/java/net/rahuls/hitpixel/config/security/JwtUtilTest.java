package net.rahuls.hitpixel.config.security;

import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    @Mock
    private SecretKey jwtSecretKey;

    @Test
    void testGenerateToken() throws NoSuchAlgorithmException {
        String email = "test@example.com";

        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSha512");
        jwtSecretKey = keyGenerator.generateKey();

        ReflectionTestUtils.setField(jwtUtil, "jwtSecretKey", jwtSecretKey);
        ReflectionTestUtils.setField(jwtUtil, "jwtExpiresInSeconds", 10);

        String token = jwtUtil.generateToken(email);

        assertNotNull(token, "The generated token should not be null");

        String decodedEmail = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        assertNotNull(decodedEmail, "The subject in the token should not be null");
        assert(decodedEmail.equals(email));
    }
}
