package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.controller.dto.NotificationCreateDto;
import com.erickmarques.notify_hub.controller.dto.NotificationResponseDto;
import com.erickmarques.notify_hub.entity.Channel;
import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.entity.Status;
import com.erickmarques.notify_hub.repository.ChannelRepository;
import com.erickmarques.notify_hub.repository.NotificationRepository;
import com.erickmarques.notify_hub.service.strategy.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ChannelRepository channelRepository;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private final Map<String, NotificationStrategy> mapStrategy = Map.of(
            "INSTAGRAM", new InstagramNotificationStrategy(),
            "EMAIL", new EmailNotificationStrategy(),
            "SMS", new SMSNotificationStrategy(),
            "WHATSAPP", new WhatsappNotificationStrategy()
    );

    @Transactional
    public String create(NotificationCreateDto NotificationcreateDto){
        var channel = getChannel(NotificationcreateDto.channel());
        validateChannel(channel);

        var notification = notificationRepository.save(NotificationcreateDto.toNotification(channel.get()));
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

    @Transactional(readOnly = true)
    private Optional<Channel> getChannel(String description){
        return channelRepository.findByDescription(description);
    }

    private void validateChannel(Optional<Channel> channel) {
        if (channel.isEmpty()){
            var allChannels = findAllChannels();
            var validChannels = concatenateChannels(allChannels);
            var errorMessage = String.format("O canal informado não existe! Os Canais disponíveis são (%s)", validChannels);

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }
    }

    @Transactional(readOnly = true)
    private List<Channel> findAllChannels(){
        return channelRepository.findAll();
    }

    private UUID stringToUUID(String id){
        try {
            return UUID.fromString(id);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Favor informar um ID válido!");
        }
    }

    private String concatenateChannels(List<Channel> allChannels){

        if (allChannels.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "É preciso cadastrar os canais disponíveis!");

        return allChannels
                .stream()
                .map(Channel::getDescription)
                .collect(Collectors.joining(", "));
    }

    public void checkAndSend(LocalDateTime dateTime) {
        var status = List.of(Status.SCHEDULED, Status.ERROR);
        var notifications = notificationRepository.findByStatusInAndDateTimeBeforeNow(status, dateTime);

        notifications.forEach(this::notify);
    }

    public void notify(Notification notification) {

        Status status = null;
        try {

            logger.info("Enviando para plataforma {}", notification.getChannel().getDescription());

            mapStrategy.get(notification.getChannel().getDescription())
                    .send(notification.getDestination(), notification.getMessage());

            status = Status.SUCCESS;

        } catch (Exception e){
            logger.error("Falha ao enviar notificação: {}", notification.getId(), e);
            status = Status.ERROR;
        }

        setStatus(notification, status);
    }

    @Transactional
    private void setStatus(Notification notification, Status status){

        logger.info("Atualizando status com {}", status.toString());
        notification.setStatus(status);

        notificationRepository.save(notification);
    }
}
