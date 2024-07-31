package com.erickmarques.notify_hub.factory;

import com.erickmarques.notify_hub.controller.dto.NotificationCreateDto;

public class NotificationCreateDtoFactory {

    public static NotificationCreateDto createNotificationDtoDefault(){
        return NotificationCreateDto.builder()
                    .destination(Constants.DESTINATION)
                    .message(Constants.MESSAGE)
                    .channel(Constants.CHANNEL_DESCRIPTION)
                    .build();
    }
}
