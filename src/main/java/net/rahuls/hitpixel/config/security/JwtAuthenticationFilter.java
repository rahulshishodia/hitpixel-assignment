package net.rahuls.hitpixel.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.rahuls.hitpixel.data.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            log.error("Authorization header is empty");
            return null;
        }

        if (!authHeader.startsWith("Bearer ")) {
            log.error("Authorization header is incorrect");
            return null;
        }

        return authHeader.substring(7);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = getJwt(request);
        if (jwt != null && !jwt.isEmpty()) {
            String email = jwtUtil.getEmail(jwt);
            boolean isTokenExpired = jwtUtil.isTokenExpired(jwt);
            if (!isTokenExpired && email != null) {
                User user = (User) userDetailsService.loadUserByUsername(email);
                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("Successfully authenticated user : {}", user.getId());
                }
            } else {
                log.error("Unauthorized :: Invalid Token");
            }
        }

        filterChain.doFilter(request, response);
    }
}
