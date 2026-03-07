package com.splitwise.clone.controller;

import com.splitwise.clone.mapper.UserMapper;
import com.splitwise.clone.model.request.LogoutRequest;
import com.splitwise.clone.model.request.RefreshTokenRequest;
import com.splitwise.clone.model.request.UserLoginRequest;
import com.splitwise.clone.model.request.UserSignupRequest;
import com.splitwise.clone.model.response.AuthResponse;
import com.splitwise.clone.model.vo.UserSignUpRequestVo;
import com.splitwise.clone.service.auth.IAuthService;
import com.splitwise.clone.service.auth.impl.RefreshTokenService;
import com.splitwise.clone.service.auth.impl.TokenBlacklistService;
import com.splitwise.clone.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final RefreshTokenService refreshTokenService;

    private final TokenBlacklistService tokenBlacklistService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        log.info("Login request received for email: {}", userLoginRequest.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));

        String accessToken = jwtUtil.generateToken(userLoginRequest.getEmail());
        String refreshToken = refreshTokenService.createRefreshToken(userLoginRequest.getEmail());

        AuthResponse response = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(JwtUtil.ACCESS_TOKEN_EXPIRATION / 1000) // in seconds
                .message("Login successful")
                .build();

        log.info("Login successful for email: {}", userLoginRequest.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        log.info("Refresh token request received");

        String userEmail = refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        String newAccessToken = jwtUtil.generateToken(userEmail);

        AuthResponse response = AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshTokenRequest.getRefreshToken()) // return the same refresh token
                .expiresIn(JwtUtil.ACCESS_TOKEN_EXPIRATION / 1000)
                .message("Token refreshed successfully")
                .build();

        log.info("Token refreshed successfully for email: {}", userEmail);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(@Valid @RequestBody LogoutRequest logoutRequest, HttpServletRequest request) {
        log.info("Logout request received");

        refreshTokenService.deleteRefreshToken(logoutRequest.getRefreshToken());

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String accessToken = authHeader.substring(7);
            tokenBlacklistService.blacklist(accessToken);
        }

        AuthResponse response = AuthResponse.builder()
                .message("Logout successful")
                .build();

        log.info("Logout successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody UserSignupRequest userSignupRequest) {
        log.info("Signup request received for email: {}", userSignupRequest.getEmail());

        UserSignUpRequestVo userSignUpRequestVo = UserMapper.INSTANCE.toUserSignupRequestVo(userSignupRequest);
        authService.signUp(userSignUpRequestVo);

        log.info("Signup successful for email: {}", userSignupRequest.getEmail());
        return "Signup successful";
    }
}
