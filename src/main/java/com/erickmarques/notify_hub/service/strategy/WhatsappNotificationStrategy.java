package com.erickmarques.notify_hub.service.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhatsappNotificationStrategy implements NotificationStrategy{

    private static final Logger logger = LoggerFactory.getLogger(WhatsappNotificationStrategy.class);

    @Override
    public void send(String destination, String message) {
        logger.info("Notificando {} com a mensagem ({}) via WHATSAPP.", destination, message);
    }
}
