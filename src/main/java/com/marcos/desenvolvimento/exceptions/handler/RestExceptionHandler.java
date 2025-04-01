package com.marcos.desenvolvimento.exceptions.handler;

import com.marcos.desenvolvimento.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.WeakKeyException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.naming.NoPermissionException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(InvalidObjectException.class)
    public ExceptionFilters handleInvalidObjectException(final InvalidObjectException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .status(422)
                .title("Invalid data!")
                .build();
    }


    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ExceptionFilters handleUserNotFound(final UserNotFoundException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .devMsg(ex.getClass().getName())
                .status(NOT_FOUND.value())
                .title("User not found!")
                .build();
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(DateTimeParseException.class)
    public ExceptionFilters handleDateTime(final DateTimeParseException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .status(NOT_FOUND.value())
                .title("Invalid date!")
                .build();
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ExceptionFilters handleInvalidCretentials(final InvalidCredentialsException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .status(UNAUTHORIZED.value())
                .title("Invalid Credentials")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ExceptionFilters dataIntegrationViolation(final DataIntegrityViolationException ex) {
        String result = null;

        final Pattern pattern = Pattern.compile("Key (.*)");
        final Matcher comparator = pattern.matcher(ex.getCause().getCause().getMessage());

        if (comparator.find()) {
            result = comparator.group(1);
        }

        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(result)
                .status(BAD_REQUEST.value())
                .title("Data Violation")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionFilters constraintViolationException(ConstraintViolationException ex) {

        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            message.append(violation.getMessage().concat(";"));
        }

        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(message.toString())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("Invalid Arguments!")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(NoPermissionException.class)
    public ExceptionFilters noPermissionException(NoPermissionException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .status(BAD_REQUEST.value())
                .title("NoPermissionException")
                .build();
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(PermissionDeniedException.class)
    public ExceptionFilters permissionDenied(final PermissionDeniedException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .status(UNAUTHORIZED.value())
                .title("Permission Denied!")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionFilters methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .status(BAD_REQUEST.value())
                .title("MethodArgumentTypeMismatchException")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ExceptionFilters emptyResultDataAccessException(EmptyResultDataAccessException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .status(BAD_REQUEST.value())
                .title("EmptyResultDataAccessException")
                .build();
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionFilters httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .status(BAD_REQUEST.value())
                .title("HttpRequestMethodNotSupportedException")
                .build();
    }

    /*
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionFilters methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .status(BAD_REQUEST.value())
                .title("MethodArgumentNotValidException")
                .build();
    }*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionFilters handleIllegalArgumentException(IllegalArgumentException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .title("IllegalArgumentException")
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({WeakKeyException.class, SecurityException.class})
    public ExceptionFilters handleInvalidSignatureException(Exception ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details("Invalid JWT signature.")
                .status(HttpStatus.UNAUTHORIZED.value())
                .title("InvalidJWTSignature")
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MalformedJwtException.class)
    public ExceptionFilters handleMalformedJwtException(MalformedJwtException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details("Invalid JWT token.")
                .status(HttpStatus.UNAUTHORIZED.value())
                .title("MalformedJWT")
                .build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnsupportedJwtException.class)
    public ExceptionFilters handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return ExceptionFilters.builder()
                .timestamp(LocalDateTime.now())
                .details("Unsupported JWT type.")
                .status(HttpStatus.UNAUTHORIZED.value())
                .title("UnsupportedJWT")
                .build();
    }

}

