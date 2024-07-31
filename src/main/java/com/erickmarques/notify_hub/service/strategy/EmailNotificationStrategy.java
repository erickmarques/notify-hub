package com.erickmarques.notify_hub.service.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailNotificationStrategy implements NotificationStrategy{

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationStrategy.class);

    @Override
    public void send(String destination, String message) {
        logger.info("Notificando {} com a mensagem ({}) via EMAIL.", destination, message);
    }
}
