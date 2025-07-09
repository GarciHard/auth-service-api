package com.garcihard.auth.exception;

import com.garcihard.auth.exception.custom.UsernameAlreadyExistException;
import com.garcihard.auth.exception.dto.ErrorResponse;
import com.garcihard.auth.exception.dto.FieldErrorResponse;
import com.garcihard.auth.utils.ApiConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String errorCode = ApiConstants.REQUEST_VALIDATION_CODE;
        String message = ApiConstants.REQUEST_VALIDATION_FAILED;

        List<FieldErrorResponse> fieldErrorResponses = ex.getBindingResult().getFieldErrors()
                .stream().map(error -> new FieldErrorResponse(error.getField(), error.getDefaultMessage()))
                .toList();

        log.warn("Handled ValidationException [{}]: {}", errorCode, message);

        ErrorResponse errorResponse = ErrorResponse.of(errorCode, message, fieldErrorResponses);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        String errorCode = ApiConstants.INVALID_CREDENTIALS_CODE;
        String message = ApiConstants.INVALID_CREDENTIALS;

        log.warn("Handled BadCredentialsException [{}]: {}", errorCode, message);

        ErrorResponse errorResponse = ErrorResponse.of(errorCode, message, null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(UsernameAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistException (UsernameAlreadyExistException ex) {
        String errorCode = ex.getErrorCode();
        String message = ex.getMessage();

        log.warn("Handled UserAlreadyExistException [{}]: {}", errorCode, message);

        ErrorResponse errorResponse = ErrorResponse.of(errorCode, message, null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
