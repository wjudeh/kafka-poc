package com.stc.kafkapoc.exception;

import com.stc.kafkapoc.exception.dto.ApiError;
import com.stc.kafkapoc.exception.dto.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NcWebServiceException extends RuntimeException {

    private final HttpStatus httpStatusCode;

    public ApiError apiError;

    public NcWebServiceException(HttpStatus httpStatusCode, Throwable cause) {
        super(cause);
        this.httpStatusCode = httpStatusCode;
    }

    public static NcWebServiceException build(HttpStatus httpStatusCode, Throwable cause) {
        return new NcWebServiceException(httpStatusCode, cause);
    }

    public NcWebServiceException addMessage(ErrorCode errorCode, String message) {
        return buildMessage(errorCode, message);
    }

    private NcWebServiceException buildMessage(ErrorCode errorCode, String message) {
        apiError = ApiError.builder().code(errorCode).message(message).build();
        return this;
    }
}
