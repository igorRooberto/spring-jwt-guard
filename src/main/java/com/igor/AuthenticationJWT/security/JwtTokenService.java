package com.igor.AuthenticationJWT.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.igor.AuthenticationJWT.domain.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtTokenService {

    @Value("${SECRET_KEY_TOKEN_JWT}")
    public String secret;

    public String generateToken(User user){
      try{
          Algorithm algorithm = Algorithm.HMAC256(secret);

          return JWT.create()
                  .withIssuer("JWT-GUARD")
                  .withSubject(user.getId().toString())
                  .withClaim("roles", user.getRoles())
                  .withExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                  .sign(algorithm);
      }catch (JWTCreationException ex){
          throw new JWTCreationException("ERROR GENERATING TOKEN", ex);
      }
    }

    public DecodedJWT validateToken(String token){
       try {
           Algorithm algorithm = Algorithm.HMAC256(secret);

           return JWT.require(algorithm)
                   .withIssuer("JWT-GUARD")
                   .withClaimPresence("roles")
                   .build()
                   .verify(token);
       } catch (JWTVerificationException ex){
           throw new JWTVerificationException("TOKEN INVALID", ex);
       }
    }


}
