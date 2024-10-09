package com.testtask.TaskManagementSystem.exceptions;
/**
 * Класс-исключение для ошибок при работе с токенами
 */
public class JwtAuthenticationException extends RuntimeException{
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
