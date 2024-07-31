package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.controller.dto.CreateNotificationDto;
import com.erickmarques.notify_hub.controller.dto.NotificationResponseDto;
import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void create(CreateNotificationDto createNotificationDto){
        notificationRepository.save(createNotificationDto.toNotification());
    }

    public NotificationResponseDto findById(UUID id){
        var notification =  notificationRepository.findById(id).orElseThrow();

        return NotificationResponseDto.toDto(notification);
    }
}
