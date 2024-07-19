package com.springfulldemo.api.config.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.springfulldemo.api.config.multitenancy.TenantContext;
import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumGenericsException;
import com.springfulldemo.api.model.entities.User;
import com.springfulldemo.api.repository.UserRepository;
import com.springfulldemo.api.service.TokenService;
import com.springfulldemo.api.utils.StringUtil;
import com.springfulldemo.api.utils.TokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        String token = TokenUtil.getTokenFromRequest(request);

        try {
            if (StringUtil.isNotNullOrEmpty(token)) {
                String login = tokenService.validateToken(token);

                TenantContext.clear();
                TenantContext.setCurrentTenant(tokenService.getAudienceFromToken(token));

                User user = userRepository.findByLogin(login);

                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            String errorMessage;
            Integer status = HttpServletResponse.SC_UNAUTHORIZED;

            if (e.getClass().equals(TokenExpiredException.class)) {
                errorMessage = EnumGenericsException.EXPIRED_SESSION.getMessage();
            } else if (e.getClass().equals(JWTVerificationException.class)) {
                errorMessage = EnumGenericsException.VALIDATE_TOKEN.getMessage();
            } else if (e.getClass().equals(ApplicationGenericsException.class)) {
                errorMessage = e.getMessage();
            } else {
                status = HttpServletResponse.SC_BAD_REQUEST;
                errorMessage = e.getMessage();
            }

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(status);
            response.getWriter().write("{\"message\":\"" + errorMessage + "\"}");
        }
    }
}
