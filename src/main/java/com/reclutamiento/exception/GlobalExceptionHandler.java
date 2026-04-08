package com.reclutamiento.exception;

import com.reclutamiento.modelo.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Manejador global de excepciones.
 * Intercepta todas las excepciones del sistema y las convierte
 * en respuestas HTTP con formato estandar ResponseModel.
 *
 * @author Ana Gabriela Pardo
 * @since 2026-03-27
 */
@RestControllerAdvice //esta clase se comporta como un controlador pero para manejar excepciones globalmente
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ReclutamientoNotFoundException.class)
    public ResponseEntity<ResponseModel<Void>> handleNotFoundException(ReclutamientoNotFoundException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseModel.<Void>builder()
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(ReclutamientoBusinessException.class)
    public ResponseEntity<ResponseModel<Void>> handleBusinessException(ReclutamientoBusinessException ex) {
        log.warn("Regla de negocio violada: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseModel.<Void>builder()
                        .message(ex.getMessage())
                        .data(null)
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseModel<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String errores = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("Error de validacion: {}", errores);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseModel.<Void>builder()
                        .message(errores)
                        .data(null)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModel<Void>> handleGenericException(Exception ex) {
        log.error("Error interno del servidor: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseModel.<Void>builder()
                        .message("Error interno del servidor")
                        .data(null)
                        .build());
    }
}

