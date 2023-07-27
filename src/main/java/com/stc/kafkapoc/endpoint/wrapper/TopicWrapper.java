package com.stc.kafkapoc.endpoint.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@Setter
@Getter
public class TopicWrapper {

    private Boolean isDone;
}
