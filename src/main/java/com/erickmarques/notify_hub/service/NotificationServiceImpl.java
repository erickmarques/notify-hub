package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.controller.dto.NotificationCreateDto;
import com.erickmarques.notify_hub.controller.dto.NotificationResponseDto;
import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.entity.Status;
import com.erickmarques.notify_hub.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ChannelService channelService;
    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Transactional
    @Override
    public String create(NotificationCreateDto NotificationcreateDto){

        var channel = channelService.findByDescription(NotificationcreateDto.channel())
                .orElseThrow(() -> {
                    var allChannels = channelService.findAllChannels();
                    var validChannels = channelService.concatenateChannels(allChannels);
                    var errorMessage = String.format("O canal informado não existe! Os Canais disponíveis são (%s)", validChannels);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
                });

        var notification = notificationRepository.save(NotificationcreateDto.toNotification(channel));

        logger.info("Salvando a notificação {}", notification.toString());
        return notification.getId().toString();
    }

    @Transactional(readOnly = true)
    @Override
    public NotificationResponseDto findById(String id){
        var notification =  notificationRepository
                                .findById(stringToUUID(id))
                                .orElseThrow(() ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "Não existe notificação para o ID informado!"));

        return NotificationResponseDto.toDto(notification);
    }

    @Transactional
    @Override
    public void updateNotificationStatus(Notification notification, Status status){
        logger.info("Atualizando status com {}", status.toString());

        notification.setStatus(status);
        notificationRepository.save(notification);
    }

    private UUID stringToUUID(String id){
        try {
            return UUID.fromString(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Favor informar um ID válido!");
        }
    }
}
