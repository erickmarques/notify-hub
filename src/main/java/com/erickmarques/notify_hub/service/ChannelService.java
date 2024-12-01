package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.entity.Channel;

import java.util.List;
import java.util.Optional;

public interface ChannelService {

    Optional<Channel> findByDescription(String description);

    List<Channel> findAllChannels();

    String concatenateChannels(List<Channel> allChannels);
}
