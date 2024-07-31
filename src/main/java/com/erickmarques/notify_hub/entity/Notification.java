package com.erickmarques.notify_hub.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table( name = "notifications",
        indexes = {
            @Index(name = "idx_status", columnList = "status")
        }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "date_time", nullable = false)
    @CreatedDate
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String destination;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Setter
    private Status status;

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private Channel channel;
}
