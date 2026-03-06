package com.splitwise.clone.service.auth.impl;

import com.splitwise.clone.jpa.entity.RefreshTokenEntity;
import com.splitwise.clone.jpa.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private static final long REFRESH_TOKEN_EXPIRATION = 7L * 24 * 60 * 60 * 1000; // 7 days

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Creates a new refresh token for the given user email.
     */
    @Transactional
    public String createRefreshToken(String userEmail) {
        // Remove any existing refresh tokens for this user
        refreshTokenRepository.deleteByUserEmail(userEmail);

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setToken(UUID.randomUUID().toString());
        refreshTokenEntity.setUserEmail(userEmail);
        refreshTokenEntity.setExpiryDate(Instant.now().plusMillis(REFRESH_TOKEN_EXPIRATION));
        refreshTokenEntity.setCreatedOn(Instant.now());

        refreshTokenRepository.save(refreshTokenEntity);
        log.info("Refresh token created for user: {}", userEmail);
        return refreshTokenEntity.getToken();
    }

    /**
     * Validates the refresh token and returns the associated user email.
     * Throws RuntimeException if the token is invalid or expired.
     */
    public String validateRefreshToken(String token) {
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshTokenEntity.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshTokenEntity);
            throw new RuntimeException("Refresh token has expired. Please login again.");
        }

        return refreshTokenEntity.getUserEmail();
    }

    /**
     * Deletes the refresh token (used during logout).
     */
    @Transactional
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
        log.info("Refresh token invalidated");
    }

    /**
     * Deletes all refresh tokens for a user (used for force logout from all devices).
     */
    @Transactional
    public void deleteAllTokensForUser(String userEmail) {
        refreshTokenRepository.deleteByUserEmail(userEmail);
        log.info("All refresh tokens invalidated for user: {}", userEmail);
    }
}

