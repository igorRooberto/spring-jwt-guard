package com.igor.AuthenticationJWT.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            Optional<String> token = recoverToken(request);
            token.ifPresent(this::authentication);

        }
        catch (JWTVerificationException ex){
            handlerExceptionResolver.resolveException(request, response,null, ex);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> recoverToken(HttpServletRequest request){
        var authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")){
            return Optional.empty();
        }

        String token = authorization.replace("Bearer ", "").trim();
        return Optional.of(token);
    }

    private void authentication(String token){
        DecodedJWT decodedJWT = jwtTokenService.validateToken(token);

        String userId = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = roles.stream().map(
                r -> new SimpleGrantedAuthority("ROLE_"+ r)).toList();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userId, null, simpleGrantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
