package com.erickmarques.notify_hub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "status")
@Getter
@Builder
public class Status {
    @Id
    private Long id;
    private String description;
}
