package com.stc.kafkapoc.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@Getter
public record FieldErrorDTO(String field, String message) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}