package com.tecsup.app.micro.enrollment.domain.exception;

/**
 * Excepción cuando los datos del producto son inválidos
 */
public class InvalidProductDataException extends RuntimeException {
    
    public InvalidProductDataException(String message) {
        super(message);
    }
}
