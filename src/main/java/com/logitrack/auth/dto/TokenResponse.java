package com.logitrack.auth.dto;

import java.time.Instant;

public record TokenResponse(
        String token,
        Instant expiration
) {
}
