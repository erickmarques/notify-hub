package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.factory.ChannelFactory;
import com.erickmarques.notify_hub.factory.Constants;
import com.erickmarques.notify_hub.factory.NotificationCreateDtoFactory;
import com.erickmarques.notify_hub.factory.NotificationFactory;
import com.erickmarques.notify_hub.repository.NotificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Classe de teste para {@link NotificationService}.
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private ChannelService channelService;

    @InjectMocks
    private NotificationService notificationService;

    @Nested
    class Create {

        @Test
        void shouldCreateNotificationSuccessfully() {
            // Arrange
            var channelDescription = Constants.CHANNEL_DESCRIPTION;
            var notificationCreateDto = NotificationCreateDtoFactory.createNotificationDtoDefault();
            var channel = ChannelFactory.createChannelDefault();
            var notification = NotificationFactory.createNotificationDefault(channel);

            when(channelService.findByDescription(channelDescription)).thenReturn(Optional.of(channel));
            when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

            // Act
            var result = notificationService.create(notificationCreateDto);

            // Assert
            assertThat(result).isEqualTo(notification.getId().toString());
            verify(channelService, times(1)).findByDescription(channelDescription);
            verify(notificationRepository, times(1)).save(any(Notification.class));

        }
    }

    @Test
    void shouldThrowException_WhenChannelNotFound() {
        // Arrange
        var channelDescription = Constants.CHANNEL_DESCRIPTION;
        var notificationCreateDto = NotificationCreateDtoFactory.createNotificationDtoDefault();

        when(channelService.findByDescription(channelDescription)).thenReturn(Optional.empty());

        // Act
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> notificationService.create(notificationCreateDto));

        // Assert
        assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        verify(channelService, times(1)).findByDescription(channelDescription);
        verify(notificationRepository, never()).save(any(Notification.class));

    }
}