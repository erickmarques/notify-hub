package com.erickmarques.notify_hub.factory;

import com.erickmarques.notify_hub.entity.Channel;

import java.util.List;

public class ChannelFactory {

    public static Channel createChannelDefault(){
        return Channel.builder()
                    .id(Constants.ID)
                    .description(Constants.CHANNEL_DESCRIPTION)
                    .build();
    }
    public static List<Channel> createListChannelDefault(){
        return List.of(
                createChannelDefault(),

                Channel.builder()
                    .id(2L)
                    .description("WHATSAPP")
                    .build()
        );
    }
}
