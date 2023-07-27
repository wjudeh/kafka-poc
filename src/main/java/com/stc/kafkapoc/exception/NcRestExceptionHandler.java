package com.stc.kafkapoc.exception;

import com.stc.kafkapoc.exception.dto.ApiError;
import com.stc.kafkapoc.exception.dto.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.stc.kafkapoc.exception.dto.ErrorCode.UNKNOWN_ERROR;
import static com.stc.kafkapoc.exception.dto.ErrorCode.VALIDATION_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class NcRestExceptionHandler {

    @ExceptionHandler(NcWebServiceException.class)
    public ResponseEntity<ApiError> handleServiceException(NcWebServiceException ex) {

        log.error(ex.getMessage());

        ApiError apiError = ex.getApiError();

        if (apiError != null) {
            return ResponseEntity.status(ex.getHttpStatusCode())
                    .body(apiError);
        }

        return ResponseEntity.status(ex.getHttpStatusCode()).build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ValidationError> handleRequestNotValidException(MethodArgumentNotValidException ex) {

        log.error(ex.getMessage());

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        ValidationError validationError = new ValidationError(VALIDATION_ERROR.name());

        for (FieldError fieldError : fieldErrors) {
            validationError.add(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(validationError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex) {

        log.error(ex.getMessage());

        ApiError body = ApiError.builder().code(UNKNOWN_ERROR)
                .message("An error occurred, general rest advice handle caught the exception" + ex.getClass().getSimpleName())
                .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(body);
    }


}
