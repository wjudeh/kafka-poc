package com.stc.kafkapoc.exception.dto;

import com.stc.kafkapoc.exception.dto.FieldError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class ValidationError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorCode;

    private List<FieldError> fieldErrors = new ArrayList<>();

    public void add(String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        FieldError build = FieldError.builder().field(field).message(message).build();

        fieldErrors.add(build);
    }
}
