package com.rlevi.restaurante_backend.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorResponseDTO {
    private String error;
    private String message;
    private int status;
    private String path;
    private LocalDateTime timestamp;
    private Map<String, String> fieldErrors;
}