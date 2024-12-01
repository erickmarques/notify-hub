package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.controller.dto.NotificationCreateDto;
import com.erickmarques.notify_hub.controller.dto.NotificationResponseDto;
import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.entity.Status;

public interface NotificationService {

    String create(NotificationCreateDto NotificationcreateDto);
    NotificationResponseDto findById(String id);
    void updateNotificationStatus(Notification notification, Status status);
}
