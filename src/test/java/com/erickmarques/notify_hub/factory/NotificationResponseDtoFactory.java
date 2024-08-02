package com.erickmarques.notify_hub.factory;


import com.erickmarques.notify_hub.controller.dto.NotificationResponseDto;
import com.erickmarques.notify_hub.entity.Status;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationResponseDtoFactory {

    public static NotificationResponseDto createNotificationResponseDefault(){
        return NotificationResponseDto
                .builder()
                .id(UUID.randomUUID())
                .dateTime(LocalDateTime.now())
                .destination(Constants.DESTINATION)
                .message(Constants.MESSAGE)
                .channel(Constants.CHANNEL_DESCRIPTION)
                .status(Status.SUCCESS.toString())
                .build();
    }
}
