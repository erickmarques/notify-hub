package com.erickmarques.notify_hub.factory;

import com.erickmarques.notify_hub.entity.Channel;
import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.entity.Status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class NotificationFactory {

    public static Notification createNotificationDefault(Channel channel){
        return Notification.builder()
                .id(UUID.randomUUID())
                .destination(Constants.DESTINATION)
                .message(Constants.MESSAGE)
                .status(Status.SCHEDULED)
                .channel(channel)
                .build();
    }

    public static List<Notification> createListNotificationDefault(Channel channel){
        return List.of(
                createNotificationDefault(channel),

                Notification.builder()
                        .id(UUID.randomUUID())
                        .destination("test1@example.com")
                        .message("Test message 1")
                        .status(Status.SCHEDULED)
                        .channel(channel)
                        .build(),

                Notification.builder()
                        .id(UUID.randomUUID())
                        .destination("test2@example.com")
                        .message("Test message 2")
                        .status(Status.SUCCESS)
                        .channel(channel)
                        .build()
        );
    }
}
