package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.entity.Channel;
import com.erickmarques.notify_hub.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChannelService {

    private final ChannelRepository channelRepository;

    @Transactional(readOnly = true)
    public Optional<Channel> findByDescription(String description){
        return channelRepository.findByDescription(description);
    }

    @Transactional(readOnly = true)
    public List<Channel> findAllChannels(){
        return channelRepository.findAll();
    }

    public String concatenateChannels(List<Channel> allChannels){

        if (allChannels.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "É preciso cadastrar os canais disponíveis!");

        return allChannels
                .stream()
                .map(Channel::getDescription)
                .collect(Collectors.joining(", "));
    }
}
