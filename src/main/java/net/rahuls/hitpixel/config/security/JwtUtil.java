package net.rahuls.hitpixel.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class JwtUtil {

    private final SecretKey jwtSecretKey;

    @Value("${hitpixel.jwt.expires-in-seconds}")
    private long jwtExpiresInSeconds;

    public String generateToken(String email) {
        long currentTimeInMillis = Instant.now().toEpochMilli();
        Date now = new Date(currentTimeInMillis);

        long expiryTimeInMillis = currentTimeInMillis + TimeUnit.SECONDS.toMillis(jwtExpiresInSeconds);
        Date expiry = new Date(expiryTimeInMillis);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}
