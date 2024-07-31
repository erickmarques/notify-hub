package com.erickmarques.notify_hub.controller.dto;

import com.erickmarques.notify_hub.entity.Channel;
import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.entity.Status;
import jakarta.validation.constraints.NotBlank;


public record NotificationCreateDto(@NotBlank(message = "Favor informar o destino!") String destination,
                                    @NotBlank(message = "Favor informar a mensagem!") String message,
                                    @NotBlank(message = "Favor informar o canal!") String channel){

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
