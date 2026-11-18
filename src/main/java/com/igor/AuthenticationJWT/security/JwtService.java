package com.igor.AuthenticationJWT.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.igor.AuthenticationJWT.domain.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

    @Value("${SECRET_KEY_TOKEN_JWT}")
    public String secret;

    public String generateToken(User user){
      try{
          Algorithm algorithm = Algorithm.HMAC256(secret);

          return JWT.create()
                  .withIssuer("JWT-GUARD")
                  .withSubject(user.getEmail())
                  .withClaim("id", user.getId())
                  .withClaim("active", user.getActive())
                  .withExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES))
                  .sign(algorithm);
      }catch (JWTCreationException ex){
          throw new JWTCreationException("ERROR GENERATING TOKEN", ex);
      }
    }

    public String validateToken(String token){
       try {
           Algorithm algorithm = Algorithm.HMAC256(secret);

           return JWT.require(algorithm)
                   .withIssuer("JWT-GUARD")
                   .build()
                   .verify(token)
                   .getSubject();
       } catch (JWTVerificationException ex){
           throw new JWTCreationException("TOKEN INVALID", ex);
       }
    }


}
