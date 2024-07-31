package com.erickmarques.notify_hub.controller.dto;

import com.erickmarques.notify_hub.entity.Channel;
import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.entity.Status;


public record CreateNotificationDto (String destination,
                                     String message,
                                     String channel){

    public Notification toNotification(Channel channel) {
        return Notification
                .builder()
                .destination(destination)
                .message(message)
                .status(Status.SCHEDULED)
                .channel(channel)
                .build();
    }
}
