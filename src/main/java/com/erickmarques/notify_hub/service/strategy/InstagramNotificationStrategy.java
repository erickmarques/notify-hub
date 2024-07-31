package com.erickmarques.notify_hub.service.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InstagramNotificationStrategy implements NotificationStrategy{

    private static final Logger logger = LoggerFactory.getLogger(InstagramNotificationStrategy.class);

    @Override
    public void send(String destination, String message) {
        logger.info("Notificando {} com a mensagem ({}) via INSTAGRAM.", destination, message);
    }
}
