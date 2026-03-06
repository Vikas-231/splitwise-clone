package com.splitwise.clone.filter;

import com.splitwise.clone.jpa.entity.UserEntity;
import com.splitwise.clone.model.auth.UserContext;
import com.splitwise.clone.service.auth.impl.AuthService;
import com.splitwise.clone.service.auth.impl.TokenBlacklistService;
import com.splitwise.clone.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final AuthService authService;

    private final TokenBlacklistService tokenBlacklistService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.isTokenValid(token) && !tokenBlacklistService.isBlacklisted(token)) {
                String userEmail = jwtUtil.extractUserEmail(token);
                var userEntity = authService.getUserByEmail(userEmail);
                var userContext = getUserContext(userEntity);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userContext, null, null);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }

    private UserContext getUserContext(UserEntity userEntity) {
        return UserContext.builder().userId(userEntity.getUserId()).userName(userEntity.getUserName()).
                email(userEntity.getUserName()).build();
    }
}
