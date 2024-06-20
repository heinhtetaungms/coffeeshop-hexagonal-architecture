package org.h2.mw.coffeeshop.infrastructure.shared;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.h2.mw.coffeeshop.core.exception.ItemMalformedException;
import org.h2.mw.coffeeshop.core.exception.OrderNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.h2.mw.coffeeshop.infrastructure.shared.ApiError.fieldApiError;
import static org.h2.mw.coffeeshop.infrastructure.shared.ApiErrorResponse.*;

@RestControllerAdvice
@Slf4j
class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                          HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return badRequest("Malformed JSON request");
    }

    @Override
    protected ResponseEntity handleTypeMismatch(TypeMismatchException ex,
                                                HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return badRequest("Type mismatch request");
    }

    @Override
    protected ResponseEntity handleNoHandlerFoundException(NoHandlerFoundException exception,
                                                           HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return notFound("Resource '" + exception.getRequestURL() + "' not found");
    }

    @Override
    protected ResponseEntity handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception,
                                                                 HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var supportedMethods = exception.getSupportedHttpMethods();

        if (!CollectionUtils.isEmpty(supportedMethods)) {
            headers.setAllow(supportedMethods);
        }

        return methodNotAllowed(headers, "Request method '" + exception.getMethod() + "' not supported");
    }

    @Override
    protected ResponseEntity handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException exception,
                                                              HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return notAcceptable("Could not find acceptable representation");
    }

    @Override
    protected ResponseEntity handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException exception,
                                                             HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var mediaTypes = exception.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(mediaTypes)) {
            headers.setAccept(mediaTypes);
        }

        return unsupportedMediaType(headers, "Content type '" + exception.getContentType() + "' not supported");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ApiErrorResponse> handle(ConstraintViolationException ex) {
        var apiErrors = ex.getConstraintViolations().stream()
                .map(c -> fieldApiError(c.getPropertyPath().toString(), c.getMessage(), c.getInvalidValue()))
                .toList();
        log.error("ConstraintViolationException throws", ex);
        return unprocessableEntity(apiErrors, "Schema validation failure.");
    }
    @ExceptionHandler(ItemMalformedException.class)
    ResponseEntity<ApiErrorResponse> handle(ItemMalformedException ex) {
        log.error("ItemMalformedException throws", ex);
        return badRequest("Malformed JSON request");
    }

    @ExceptionHandler(OrderNotFoundException.class)
    ResponseEntity<ApiErrorResponse> handle(OrderNotFoundException ex) {
        log.error("OrderNotFoundException throws", ex);
        return notFound(ex.getMessage());
    }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<ApiErrorResponse> handle(IllegalStateException ex) {
        log.error("IllegalStateException throws", ex);
        return badRequest(ex.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<ApiErrorResponse> handleThrowable(Throwable throwable) {
        log.error("Request handling failed", throwable);
        return internalServerError("An unexpected error occurred");
    }
}
