package com.splitwise.clone.exception;

import com.splitwise.clone.model.exception.ErrorResponse;
import com.splitwise.clone.model.exception.ResponseErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(UsernameNotFoundException exception) {
        log.error("User not found exception: {}", exception.getMessage());
        ErrorResponse errorResponse = createErrorResponse(ResponseErrorCode.USER_NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(404).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        log.error("Access denied exception: {}", exception.getMessage());
        ErrorResponse errorResponse = createErrorResponse(ResponseErrorCode.ACCESS_DENIED, exception.getMessage());
        return ResponseEntity.status(403).body(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        log.error("Bad credentials exception: {}", exception.getMessage());
        ErrorResponse errorResponse = createErrorResponse(ResponseErrorCode.USER_NOT_AUTHENTICATED, "Invalid email or password");
        return ResponseEntity.status(401).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException exception) {
        log.error("Authentication exception: {}", exception.getMessage());
        ErrorResponse errorResponse = createErrorResponse(ResponseErrorCode.USER_NOT_AUTHENTICATED, exception.getMessage());
        return ResponseEntity.status(401).body(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public final ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.error("Missing request parameter: {}", exception.getMessage());
        String message = "Required request parameter '" + exception.getParameterName() + "' is not present";
        ErrorResponse errorResponse = createErrorResponse(ResponseErrorCode.INVALID_REQUEST, message);
        return ResponseEntity.status(400).body(errorResponse);
    }

    @ExceptionHandler(DataValidationException.class)
    public final ResponseEntity<ErrorResponse> handleDataValidationExceptionException(DataValidationException exception) {
        log.error("Runtime exception: {}", exception.getMessage());
        ErrorResponse errorResponse = createErrorResponse(ResponseErrorCode.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleOtherException(Exception exception) {
        log.error("Runtime exception: {}", exception.getMessage());
        ErrorResponse errorResponse = createErrorResponse(ResponseErrorCode.INTERNAL_SERVER_ERROR, exception.getMessage());
        return ResponseEntity.status(500).body(errorResponse);
    }

    private ErrorResponse createErrorResponse(final ResponseErrorCode responseErrorCode, final String message) {
        return new ErrorResponse(responseErrorCode, responseErrorCode.getTitle(), message);
    }
}
