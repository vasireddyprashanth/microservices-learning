package com.prash.ecommerce.inventory_service.exceptions;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
public record BaseError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        String traceId
) {
}
