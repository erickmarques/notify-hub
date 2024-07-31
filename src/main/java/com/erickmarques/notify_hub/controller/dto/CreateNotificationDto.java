package com.erickmarques.notify_hub.controller.dto;

import com.erickmarques.notify_hub.entity.Notification;

import java.time.LocalDateTime;

public record CreateNotificationDto (LocalDateTime dateTime,
                                     String destination,
                                     String message,
                                     String channel){

    public Notification toNotification() {
        return Notification
                .builder()
                .dateTime(dateTime)
                .destination(destination)
                .message(message)
                .status(null)
                .channel(null)
                .build();
    }

}
