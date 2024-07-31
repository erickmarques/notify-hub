package com.erickmarques.notify_hub.service.strategy;

import com.erickmarques.notify_hub.entity.Notification;

public interface NotificationStrategy {

    void send(String destination, String message);
}
