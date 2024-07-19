package com.springfulldemo.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.springfulldemo.api.config.multitenancy.TenantContext;
import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumGenericsException;
import com.springfulldemo.api.model.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.UUID;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    private Instant genExpirationDateAccessToken() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));
    }

    private Instant genExpirationDateRefreshToken() {
        return LocalDateTime.now().plusDays(30).toInstant(ZoneOffset.of("-03:00"));
    }

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withAudience(TenantContext.getCurrentTenant())
                    .withIssuer("springfulldemo-session")
                    .withSubject(user.getLogin())
                    .withExpiresAt(genExpirationDateAccessToken())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new ApplicationGenericsException(EnumGenericsException.GENERATE_TOKEN);
        }
    }

    public String generateRefreshToken(User user) {
        try {
            String uniqueId = UUID.randomUUID().toString();
            String userIdAndSchema = user.getId() + "-" + TenantContext.getCurrentTenant();
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withJWTId(uniqueId)
                    .withIssuer("springfulldemo-session")
                    .withSubject(new String(Base64.getEncoder().encode(userIdAndSchema.getBytes())))
                    .withExpiresAt(genExpirationDateRefreshToken())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new ApplicationGenericsException(EnumGenericsException.GENERATE_REFRESH_TOKEN);
        }
    }

    public String validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try {
            return JWT.require(algorithm)
                    .withIssuer("springfulldemo-session")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            if (e.getClass().equals(TokenExpiredException.class)) {
                throw new ApplicationGenericsException(EnumGenericsException.EXPIRED_SESSION);
            } else if (e.getClass().equals(JWTVerificationException.class)) {
                throw new ApplicationGenericsException(EnumGenericsException.VALIDATE_TOKEN);
            } else {
                throw new ApplicationGenericsException(e.getMessage());
            }
        }
    }

    public String getAudienceFromToken(String token) {
        try {
            return JWT.decode(token)
                    .getAudience()
                    .getFirst();
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }
}