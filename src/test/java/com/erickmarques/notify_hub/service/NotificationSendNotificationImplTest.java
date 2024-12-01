package com.erickmarques.notify_hub.service;


import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.entity.Status;
import com.erickmarques.notify_hub.factory.ChannelFactory;
import com.erickmarques.notify_hub.factory.NotificationFactory;
import com.erickmarques.notify_hub.repository.NotificationRepository;
import com.erickmarques.notify_hub.service.strategy.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

/**
 * Classe de teste para {@link NotificationSendNotificationImpl}.
 */
@ExtendWith(MockitoExtension.class)
class NotificationSendNotificationImplTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationStrategyFactory notificationStrategyFactory;

    @Mock
    private NotificationService notificationService;

    @Mock
    private NotificationStrategy mockStrategy;

    @InjectMocks
    private NotificationSendNotificationImpl notificationSendNotification;

    @Nested
    class CheckAndSend {

        private static final List<Status> status = List.of(Status.SCHEDULED, Status.ERROR);
        private static final LocalDateTime dateTime = LocalDateTime.now();

        @Test
        void shouldCheckAndSendNotificationsSuccessfully() {
            // Arrange
            var channel = ChannelFactory.createChannelDefault();
            var notifications = NotificationFactory.createListNotificationDefault(channel);

            when(notificationStrategyFactory.getStrategy(NotificationType.valueOf(channel.getDescription())))
                    .thenReturn(mockStrategy);

            when(notificationRepository.findByStatusInAndDateTimeBeforeNow(status, dateTime))
                    .thenReturn(notifications);

            // Act
            notificationSendNotification.checkAndSend(dateTime);

            // Assert
            verify(notificationRepository, times(1))
                    .findByStatusInAndDateTimeBeforeNow(anyList(), eq(dateTime));
        }

        @Test
        void shouldNotSendNotifications_WhenNoNotificationsFound() {
            // Arrange
            when(notificationRepository.findByStatusInAndDateTimeBeforeNow(anyList(), eq(dateTime)))
                    .thenReturn(Collections.emptyList());

            // Act
            notificationSendNotification.checkAndSend(dateTime);

            // Assert
            verify(notificationRepository, times(1))
                    .findByStatusInAndDateTimeBeforeNow(anyList(), eq(dateTime));
            verify(notificationRepository, times(0)).save(any(Notification.class));
        }
    }

    @Nested
    class Notify {
        @Test
        public void shouldNotifySuccessfully()  {
            // Arrange
            var channel = ChannelFactory.createChannelDefault();
            var notification = NotificationFactory.createNotificationDefault(channel);

            when(notificationStrategyFactory.getStrategy(NotificationType.valueOf(channel.getDescription())))
                    .thenReturn(mockStrategy);

            // Act
            notificationSendNotification.notify(notification);

            // Assert
            verify(mockStrategy, times(1)).send(notification.getDestination(), notification.getMessage());
            verify(notificationService, times(1)).updateNotificationStatus(notification, Status.SUCCESS);
        }

        @Test
        void shouldSetStatusToErrorWhenNotificationSendingFails() {
            // Arrange
            var channel = ChannelFactory.createChannelDefault();
            var notification = NotificationFactory.createNotificationDefault(channel);

            doThrow(new RuntimeException("Notification sending failed")).when(mockStrategy)
                    .send(notification.getDestination(), notification.getMessage());

            when(notificationStrategyFactory.getStrategy(NotificationType.valueOf(channel.getDescription())))
                    .thenReturn(mockStrategy);

            // Act
            notificationSendNotification.notify(notification);

            // Assert
            verify(notificationService, times(1)).updateNotificationStatus(notification, Status.ERROR);
        }
    }
}