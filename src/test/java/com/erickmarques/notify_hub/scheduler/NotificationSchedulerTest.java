package com.erickmarques.notify_hub.scheduler;

import static org.mockito.Mockito.verify;


import com.erickmarques.notify_hub.service.NotificationSendNotificationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class NotificationSchedulerTest {

    @Mock
    private NotificationSendNotificationImpl notificationSendNotificationImpl;

    @InjectMocks
    private NotificationScheduler notificationScheduler;

    @Test
    void shouldInvokeNotificationService() {
        // Arrange
        var now = LocalDateTime.now();

        // Act
        notificationScheduler.checkNotifications();

        // Assert
        verify(notificationSendNotificationImpl).checkAndSend(now);
    }
}