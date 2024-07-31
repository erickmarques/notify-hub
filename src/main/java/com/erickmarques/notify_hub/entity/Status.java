package com.erickmarques.notify_hub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "status")
@Getter
public class Status {
    @Id
    private Long id;
    private String description;
}
