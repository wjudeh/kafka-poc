package com.stc.kafkapoc.endpoint.dto;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@AllArgsConstructor
@Getter
@Setter
public class TopicDTO {

    @NotNull
    private String name;

    @Nullable
    private Integer partitions;

    @Nullable
    private Short replicationFactor;
}
