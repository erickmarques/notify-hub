package com.erickmarques.notify_hub.scheduler;

import com.erickmarques.notify_hub.service.NotificationSendNotificationImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(NotificationScheduler.class);
    private final NotificationSendNotificationImpl notificationSendNotificationImpl;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void checkNotifications() {
        var dateTime = LocalDateTime.now();
        logger.info("Iniciando pesquisa de notificações em {}", dateTime);
        notificationSendNotificationImpl.checkAndSend(dateTime);
    }
}
