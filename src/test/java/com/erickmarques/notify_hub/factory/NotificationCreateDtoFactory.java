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

    public static NotificationCreateDto createNotificationDtoWithDestinationIsBlank(){
        return NotificationCreateDto.builder()
                .destination("")
                .message(Constants.MESSAGE)
                .channel(Constants.CHANNEL_DESCRIPTION)
                .build();
    }

    public static NotificationCreateDto createNotificationDtoWithMessageIsBlank(){
        return NotificationCreateDto.builder()
                .destination(Constants.CHANNEL_DESCRIPTION)
                .message("")
                .channel(Constants.CHANNEL_DESCRIPTION)
                .build();
    }

    public static NotificationCreateDto createNotificationDtoWithChannelIsBlank(){
        return NotificationCreateDto.builder()
                .destination(Constants.DESTINATION)
                .message(Constants.MESSAGE)
                .channel("")
                .build();
    }
}
