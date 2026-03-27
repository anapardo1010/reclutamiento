package com.reclutamiento.exception;

/**
 * Excepcion lanzada cuando se viola una regla de negocio.
 * Retorna HTTP 400.
 *
 * @author Ana Gabriela Pardo
 * @since 2026-03-27
 */
public class ReclutamientoBusinessException extends RuntimeException {

    public ReclutamientoBusinessException(String message) {
        super(message);
    }
}

