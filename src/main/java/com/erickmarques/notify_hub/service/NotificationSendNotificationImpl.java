package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.entity.Status;
import com.erickmarques.notify_hub.repository.NotificationRepository;
import com.erickmarques.notify_hub.service.strategy.NotificationStrategyFactory;
import com.erickmarques.notify_hub.service.strategy.NotificationType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationSendNotificationImpl implements NotificationSendNotification {

    private final NotificationStrategyFactory notificationStrategyFactory;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    private static final Logger logger = LoggerFactory.getLogger(NotificationSendNotificationImpl.class);

    @Override
    public void checkAndSend(LocalDateTime dateTime) {

        logger.info("verificando se existe notificação...");

        var status = List.of(Status.SCHEDULED, Status.ERROR);
        var notifications = notificationRepository.findByStatusInAndDateTimeBeforeNow(status, dateTime);

        notifications.forEach(this::notify);
    }

    @Override
    public void notify(Notification notification) {

        Status status;
        try {
            logger.info("Enviando para plataforma {}", notification.getChannel().getDescription());

            var type = NotificationType.valueOf(notification.getChannel().getDescription());

            notificationStrategyFactory
                    .getStrategy(type)
                    .send(notification.getDestination(), notification.getMessage());

            status = Status.SUCCESS;

        } catch (Exception e){
            logger.error("Falha ao enviar notificação: {}", notification.getId(), e);
            status = Status.ERROR;
        }

        notificationService.updateNotificationStatus(notification, status);
    }
}
