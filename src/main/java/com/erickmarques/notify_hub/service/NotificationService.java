package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.controller.dto.CreateNotificationDto;
import com.erickmarques.notify_hub.controller.dto.NotificationResponseDto;
import com.erickmarques.notify_hub.entity.Channel;
import com.erickmarques.notify_hub.repository.ChannelRepository;
import com.erickmarques.notify_hub.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ChannelRepository channelRepository;

    @Transactional
    public String create(CreateNotificationDto createNotificationDto){
        var channel = getChanell(createNotificationDto.channel());
        var notification = notificationRepository.save(createNotificationDto.toNotification(channel));
        return notification.getId().toString();
    }

    @Transactional(readOnly = true)
    public NotificationResponseDto findById(String id){
        var notification =  notificationRepository
                                .findById(stringToUUID(id))
                                .orElseThrow(() ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "Não existe notificação para o ID informado!"));

        return NotificationResponseDto.toDto(notification);
    }

    private UUID stringToUUID(String id){
        try {
            return UUID.fromString(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Favor informar um ID válido!");
        }
    }

    @Transactional(readOnly = true)
    private Channel getChanell(String description){
        return channelRepository
                .findByDescription(description)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,  "O canal informado não existe!"));
    }
}
