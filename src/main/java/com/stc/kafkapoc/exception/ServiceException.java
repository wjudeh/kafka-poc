package com.stc.kafkapoc.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {

    private final HttpStatus httpStatusCode;

    public ApiErrorDTO apiErrorDTO;

    public ServiceException(HttpStatus httpStatusCode, Throwable cause) {
        super(cause);
        this.httpStatusCode = httpStatusCode;
    }

    public static ServiceException build(HttpStatus httpStatusCode, Throwable cause) {
        return new ServiceException(httpStatusCode, cause);
    }

    public ServiceException addMessage(ErrorCode errorCode, String message) {
        return buildMessage(errorCode, message);
    }

    private ServiceException buildMessage(ErrorCode errorCode, String message) {
        apiErrorDTO = ApiErrorDTO.builder().code(errorCode).message(message).build();
        return this;
    }
}
