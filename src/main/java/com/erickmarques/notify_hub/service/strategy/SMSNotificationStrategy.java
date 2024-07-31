package com.erickmarques.notify_hub.service.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSNotificationStrategy implements NotificationStrategy{

    private static final Logger logger = LoggerFactory.getLogger(SMSNotificationStrategy.class);

    @Override
    public void send(String destination, String message) {
        logger.info("Notificando {} com a mensagem ({}) via SMS.", destination, message);
    }
}
