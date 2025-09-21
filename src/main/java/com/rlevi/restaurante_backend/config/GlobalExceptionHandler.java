package com.rlevi.restaurante_backend.config;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rlevi.restaurante_backend.dto.ErrorResponseDTO;
import com.rlevi.restaurante_backend.dto.ValidationErrorResponseDTO;
import com.rlevi.restaurante_backend.exception.AuthenticationException;
import com.rlevi.restaurante_backend.exception.BusinessException;
import com.rlevi.restaurante_backend.exception.DuplicateResourceException;
import com.rlevi.restaurante_backend.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {

        logger.warn("Recurso não encontrado: {}", ex.getMessage());

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .error("Recurso Não Encontrado")
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicateResource(
            DuplicateResourceException ex, HttpServletRequest request) {

        logger.warn("Recurso duplicado: {}", ex.getMessage());

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .error("Recurso Duplicado")
                .message(ex.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {

        logger.warn("Erro de negócio: {}", ex.getMessage());

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .error("Erro de Regra de Negócio")
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(
            AuthenticationException ex, HttpServletRequest request) {

        logger.warn("Erro de autenticação: {}", ex.getMessage());

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .error("Erro de Autenticação")
                .message(ex.getMessage())
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDenied(
            AccessDeniedException ex, HttpServletRequest request) {

        logger.warn("Acesso negado: {}", ex.getMessage());

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .error("Acesso Negado")
                .message("Você não tem permissão para acessar este recurso")
                .status(HttpStatus.FORBIDDEN.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDTO> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        logger.warn("Erro de validação: {}", ex.getMessage());

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        ValidationErrorResponseDTO error = ValidationErrorResponseDTO.builder()
                .error("Erro de Validação")
                .message("Dados inválidos fornecidos")
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .fieldErrors(fieldErrors)
                .build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, HttpServletRequest request) {

        logger.error("Violação de integridade de dados: {}", ex.getMessage());

        String message = "Erro de integridade de dados. Verifique se os dados não violam as regras do banco.";
        if (ex.getMessage().contains("unique")) {
            message = "Já existe um registro com esses dados únicos.";
        }

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .error("Erro de Integridade")
                .message(message)
                .status(HttpStatus.CONFLICT.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception ex, HttpServletRequest request) {

        logger.error("Erro interno do servidor: {}", ex.getMessage(), ex);

        ErrorResponseDTO error = ErrorResponseDTO.builder()
                .error("Erro Interno do Servidor")
                .message("Ocorreu um erro inesperado. Tente novamente mais tarde.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}