package com.rlevi.restaurante_backend.shared.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
        String error,
        String message,
        int status,
        String path,
        LocalDateTime timestamp,
        List<String> details) {
}
