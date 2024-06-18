package org.h2.mw.coffeeshop.infrastructure.shared;

public interface ApiError {

    String message();

    static ApiError fieldApiError(String field, String message, Object rejectedValue) {
        return new ApiErrorField(field, message, rejectedValue);
    }

    record ApiErrorField(String field, String message, Object rejectedValue) implements ApiError {
    }
}
