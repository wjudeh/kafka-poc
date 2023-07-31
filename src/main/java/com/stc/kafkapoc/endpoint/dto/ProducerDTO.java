package com.stc.kafkapoc.endpoint.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProducerDTO {

    @Nullable
    private String topic;

    @Nullable
    private Integer partition;

    @Nullable
    private String key;

    @NotNull
    private String value;
}
