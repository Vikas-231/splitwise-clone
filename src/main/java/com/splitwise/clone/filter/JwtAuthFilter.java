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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

                List<String> roles = extractRoles(userEntity);
                var userContext = getUserContext(userEntity, roles);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userContext, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }

    private List<String> extractRoles(UserEntity userEntity) {
        if (userEntity.getUserRoles() == null || userEntity.getUserRoles().isEmpty()) {
            return Collections.emptyList();
        }
        return userEntity.getUserRoles().stream()
                .map(userRole -> userRole.getRole().getRoleName())
                .collect(Collectors.toList());
    }

    private UserContext getUserContext(UserEntity userEntity, List<String> roles) {
        return UserContext.builder()
                .userId(userEntity.getUserId())
                .userName(userEntity.getUserName())
                .email(userEntity.getEmail())
                .roles(roles)
                .build();
    }
}
