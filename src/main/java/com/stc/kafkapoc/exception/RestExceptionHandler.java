package com.stc.kafkapoc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.stc.kafkapoc.exception.ErrorCode.UNKNOWN_ERROR;
import static com.stc.kafkapoc.exception.ErrorCode.VALIDATION_ERROR;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiErrorDTO> handleServiceException(ServiceException ex) {

        log.error(ex.getMessage());

        ApiErrorDTO apiErrorDTO = ex.getApiErrorDTO();

        if (apiErrorDTO != null) {
            return ResponseEntity.status(ex.getHttpStatusCode())
                    .body(apiErrorDTO);
        }

        return ResponseEntity.status(ex.getHttpStatusCode()).build();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ValidationErrorDTO> handleRequestNotValidException(MethodArgumentNotValidException ex) {

        log.error(ex.getMessage());

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO(VALIDATION_ERROR.name());

        for (FieldError fieldError : fieldErrors) {
            validationErrorDTO.add(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(validationErrorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleException(Exception ex) {

        log.error(ex.getMessage());

        ApiErrorDTO body = ApiErrorDTO.builder().code(UNKNOWN_ERROR)
                .message("An error occurred, general rest advice handle caught the exception" + ex.getClass().getSimpleName())
                .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(body);
    }


}
