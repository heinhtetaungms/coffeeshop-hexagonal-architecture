package org.h2.mw.coffeeshop.infrastructure.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

@Getter
@Setter
public class ApiResponse<T> {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS", timezone = "Asia/Rangoon")
    private Instant timestamp;
    private int status;
    private HttpStatus statusCode;
    private String message;
    private T data;
    @Builder
    private ApiResponse(HttpStatus httpStatus, String message, T data) {
        this.timestamp = Instant.now();
        this.status = httpStatus.value();
        this.statusCode = httpStatus;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity<ApiResponse<T>> resolve(HttpStatus httpStatus, T data) {
        ApiResponse<T> body = new ApiResponse<>(httpStatus, httpStatus.getReasonPhrase().toUpperCase(), data);
        return new ResponseEntity<>(body, httpStatus);
    }

    public static <T> ResponseEntity<ApiResponse<T>> resolve(HttpStatus httpStatus, HttpHeaders httpHeaders, T data) {
        ApiResponse<T> body = new ApiResponse<>(httpStatus, httpStatus.getReasonPhrase().toUpperCase(), data);
        return new ResponseEntity<>(body, httpHeaders, httpStatus);
    }

}
