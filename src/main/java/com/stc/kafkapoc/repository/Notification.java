package com.stc.kafkapoc.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_sequence")
    @SequenceGenerator(name = "notification_sequence", sequenceName = "notification_sequence", allocationSize = 1)
    private Long id;

    private String key;

    private String value;
}
