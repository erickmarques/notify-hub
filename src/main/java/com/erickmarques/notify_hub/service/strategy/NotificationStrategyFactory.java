package com.erickmarques.notify_hub.service.strategy;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class NotificationStrategyFactory {
    public NotificationStrategy getStrategy(NotificationType type) {

        if (type == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de notificação inválido!");
        }

        return switch (type) {
            case INSTAGRAM -> new InstagramNotificationStrategy();
            case EMAIL -> new EmailNotificationStrategy();
            case SMS -> new SMSNotificationStrategy();
            case WHATSAPP -> new WhatsappNotificationStrategy();
        };
    }
}
