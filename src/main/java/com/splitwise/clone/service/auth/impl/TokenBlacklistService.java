package com.splitwise.clone.service.auth.impl;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory blacklist for invalidated access tokens.
 * Access tokens are short-lived (15 min), so an in-memory store is sufficient.
 * For production with multiple instances, consider using Redis instead.
 */
@Service
public class TokenBlacklistService {

    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    public void blacklist(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}

