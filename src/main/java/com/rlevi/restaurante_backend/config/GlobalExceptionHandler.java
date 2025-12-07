package com.rlevi.restaurante_backend.config;

import com.rlevi.restaurante_backend.shared.dto.response.ErrorResponseDTO;
import com.rlevi.restaurante_backend.shared.dto.response.ValidationErrorResponseDTO;
import com.rlevi.restaurante_backend.shared.exception.AuthenticationException;
import com.rlevi.restaurante_backend.shared.exception.BusinessException;
import com.rlevi.restaurante_backend.shared.exception.DuplicateResourceException;
import com.rlevi.restaurante_backend.shared.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponseDTO> handleResourceNotFound(
                        ResourceNotFoundException ex, HttpServletRequest request) {

                logger.warn("Recurso não encontrado: {}", ex.getMessage());

                ErrorResponseDTO error = new ErrorResponseDTO(
                                "Recurso Não Encontrado",
                                ex.getMessage(),
                                HttpStatus.NOT_FOUND.value(),
                                request.getRequestURI(),
                                LocalDateTime.now(),
                                null);

                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<ErrorResponseDTO> handleDuplicateResource(
                        DuplicateResourceException ex, HttpServletRequest request) {

                logger.warn("Recurso duplicado: {}", ex.getMessage());

                ErrorResponseDTO error = new ErrorResponseDTO(
                                "Recurso Duplicado",
                                ex.getMessage(),
                                HttpStatus.CONFLICT.value(),
                                request.getRequestURI(),
                                LocalDateTime.now(),
                                null);

                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(BusinessException.class)
        public ResponseEntity<ErrorResponseDTO> handleBusinessException(
                        BusinessException ex, HttpServletRequest request) {

                logger.warn("Erro de negócio: {}", ex.getMessage());

                ErrorResponseDTO error = new ErrorResponseDTO(
                                "Erro de Regra de Negócio",
                                ex.getMessage(),
                                HttpStatus.BAD_REQUEST.value(),
                                request.getRequestURI(),
                                LocalDateTime.now(),
                                null);

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(
                        AuthenticationException ex, HttpServletRequest request) {

                logger.warn("Erro de autenticação: {}", ex.getMessage());

                ErrorResponseDTO error = new ErrorResponseDTO(
                                "Erro de Autenticação",
                                ex.getMessage(),
                                HttpStatus.UNAUTHORIZED.value(),
                                request.getRequestURI(),
                                LocalDateTime.now(),
                                null);

                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ErrorResponseDTO> handleAccessDenied(
                        AccessDeniedException ex, HttpServletRequest request) {

                logger.warn("Acesso negado: {}", ex.getMessage());

                ErrorResponseDTO error = new ErrorResponseDTO(
                                "Acesso Negado",
                                "Você não tem permissão para acessar este recurso",
                                HttpStatus.FORBIDDEN.value(),
                                request.getRequestURI(),
                                LocalDateTime.now(),
                                null);

                return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationErrorResponseDTO> handleValidationErrors(
                        MethodArgumentNotValidException ex, HttpServletRequest request) {

                logger.warn("Erro de validação: {}", ex.getMessage());

                Map<String, String> fieldErrors = new HashMap<>();
                ex.getBindingResult().getFieldErrors()
                                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

                ValidationErrorResponseDTO error = new ValidationErrorResponseDTO(
                                "Erro de Validação",
                                "Dados inválidos fornecidos",
                                HttpStatus.BAD_REQUEST.value(),
                                request.getRequestURI(),
                                LocalDateTime.now(),
                                fieldErrors);

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

                ErrorResponseDTO error = new ErrorResponseDTO(
                                "Erro de Integridade",
                                message,
                                HttpStatus.CONFLICT.value(),
                                request.getRequestURI(),
                                LocalDateTime.now(),
                                null);

                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponseDTO> handleGenericException(
                        Exception ex, HttpServletRequest request) {

                logger.error("Erro interno do servidor: {}", ex.getMessage(), ex);

                ErrorResponseDTO error = new ErrorResponseDTO(
                                "Erro Interno do Servidor",
                                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                request.getRequestURI(),
                                LocalDateTime.now(),
                                null);

                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
