package com.stc.kafkapoc.exception;

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
public class ValidationErrorDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String errorCode;

    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    public void add(String field, String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        FieldErrorDTO build = FieldErrorDTO.builder().field(field).message(message).build();

        fieldErrors.add(build);
    }
}
