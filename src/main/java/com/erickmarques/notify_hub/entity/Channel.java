package com.erickmarques.notify_hub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "channels")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Channel {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String description;
}
