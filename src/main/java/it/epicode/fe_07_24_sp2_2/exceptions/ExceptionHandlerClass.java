package it.epicode.fe_07_24_sp2_2.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerClass extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return generateErrorResponse(ex, authentication, request, HttpStatus.NOT_FOUND, "Entity not found");
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> alreadyExists(AlreadyExistsException ex, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return generateErrorResponse(ex, authentication, request, HttpStatus.BAD_REQUEST, "Entity already exists");
    }

    @ExceptionHandler(UploadException.class)
    public ResponseEntity<ErrorMessage> uploadExceptionHandler(UploadException ex, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return generateErrorResponse(ex, authentication, request, HttpStatus.BAD_REQUEST, "Upload error");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            if (fieldName.contains(".")) {
                fieldName = fieldName.substring(fieldName.lastIndexOf('.') + 1);
            }
            errors.put(fieldName, violation.getMessage());
        }

        errors.put("user", authentication != null ? authentication.getName() : "Anonymous");
        errors.put("roles", authentication != null ? getRoles(authentication) : "None");
        errors.put("url", request.getRequestURI());
        errors.put("method", request.getMethod());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), "Errore controller: " + error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    private ResponseEntity<ErrorMessage> generateErrorResponse(Exception ex, Authentication authentication, HttpServletRequest request, HttpStatus status, String message) {
        String user = authentication != null ? authentication.getName() : "Anonymous";
        String roles = authentication != null ? getRoles(authentication) : "None";
        String url = request.getRequestURI();
        String method = request.getMethod();

        ErrorMessage error = new ErrorMessage();
        error.setMessage(message + " | " + ex.getMessage());
        error.setStatusCode(status);
        error.setUser(user);
        error.setRoles(roles);
        error.setUrl(url);
        error.setMethod(method);

        return new ResponseEntity<>(error, status);
    }

    private String getRoles(Authentication authentication) {
        return authentication != null && authentication.getAuthorities() != null
                ? authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "))
                : "None";
    }
}
