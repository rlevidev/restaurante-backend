package com.rlevi.restaurante_backend.shared.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ValidationErrorResponseDTO(
        String error,
        String message,
        int status,
        String path,
        LocalDateTime timestamp,
        Map<String, String> fieldErrors) {
}
