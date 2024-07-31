package com.erickmarques.notify_hub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "channels")
public class Channel {
    @Id
    private Long id;
    private String description;
}
