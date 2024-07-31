package com.erickmarques.notify_hub.controller.dto;

import com.erickmarques.notify_hub.entity.Notification;



public record CreateNotificationDto (String destination,
                                     String message,
                                     String channel){

    public Notification toNotification() {
        return Notification
                .builder()
                .destination(destination)
                .message(message)
                .status(null)
                .channel(null)
                .build();
    }
}
