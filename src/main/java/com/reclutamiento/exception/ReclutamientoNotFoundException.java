package com.reclutamiento.exception;

/**
 * Excepcion lanzada cuando no se encuentra un recurso en el sistema.
 * Retorna HTTP 404.
 *
 * @author Ana Gabriela Pardo
 * @since 2026-03-27
 */
public class ReclutamientoNotFoundException extends RuntimeException {

    public ReclutamientoNotFoundException(String message) {
        super(message);
    }
}

