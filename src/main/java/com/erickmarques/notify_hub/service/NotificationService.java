package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.controller.dto.CreateNotificationDto;
import com.erickmarques.notify_hub.controller.dto.NotificationResponseDto;
import com.erickmarques.notify_hub.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public UUID create(CreateNotificationDto createNotificationDto){
        var notification = notificationRepository.save(createNotificationDto.toNotification());
        return notification.getId();
    }

    public NotificationResponseDto findById(UUID id){
        var notification =  notificationRepository
                                .findById(id)
                                .orElseThrow(() ->
                                        new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "Não existe notificação para o ID informado!"));

        return NotificationResponseDto.toDto(notification);
    }
}
