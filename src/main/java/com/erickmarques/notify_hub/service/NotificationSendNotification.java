package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.entity.Notification;

import java.time.LocalDateTime;

public interface NotificationSendNotification {

    void checkAndSend(LocalDateTime dateTime);
    void notify(Notification notification);
}
