package com.erickmarques.notify_hub.controller.dto;

import com.erickmarques.notify_hub.entity.Notification;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record NotificationResponseDto (UUID id,
                                       LocalDateTime dateTime,
                                       String destination,
                                       String message,
                                       String channel,
                                       String status) {

    public static NotificationResponseDto toDto(Notification notification){
        return NotificationResponseDto
                .builder()
                .id(notification.getId())
                .dateTime(notification.getDateTime())
                .destination(notification.getDestination())
                .message(notification.getMessage())
                .channel(notification.getChannel().getDescription())
                .status(notification.getStatus().getDescription())
                .build();
    }
}
