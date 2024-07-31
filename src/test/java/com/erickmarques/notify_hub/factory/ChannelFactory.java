package com.erickmarques.notify_hub.factory;

import com.erickmarques.notify_hub.entity.Channel;

import java.util.List;

public class ChannelFactory {

    private static Long ID = 1l;
    public static String DESCRIPTION = "EMAIL";

    public static Channel createChannelDefault(){
        return Channel.builder()
                    .id(ID)
                    .description(DESCRIPTION)
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
