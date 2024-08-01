package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.entity.Status;
import com.erickmarques.notify_hub.factory.ChannelFactory;
import com.erickmarques.notify_hub.factory.Constants;
import com.erickmarques.notify_hub.factory.NotificationCreateDtoFactory;
import com.erickmarques.notify_hub.factory.NotificationFactory;
import com.erickmarques.notify_hub.repository.NotificationRepository;
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

        private static final String channelDescription = Constants.CHANNEL_DESCRIPTION;

        @Test
        void shouldCreateNotificationSuccessfully() {
            // Arrange
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

        @Test
        void shouldThrowException_WhenChannelNotFound() {
            // Arrange
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

    @Nested
    class FindById {

        @Test
        void shouldReturnNotificationSuccessfully(){
            // Arrange
            var channel = ChannelFactory.createChannelDefault();
            var notification = NotificationFactory.createNotificationDefault(channel);
            when(notificationRepository.findById(notification.getId())).thenReturn(Optional.of(notification));

            // Act
            var result = notificationService.findById(notification.getId().toString());

            // Assert
            assertThat(result.id()).isEqualTo(notification.getId());
            assertThat(result.destination()).isEqualTo(notification.getDestination());
            assertThat(result.message()).isEqualTo(notification.getMessage());
            assertThat(result.channel()).isEqualTo(notification.getChannel().getDescription());
            assertThat(result.status()).isEqualTo(notification.getStatus().toString());
        }

        @Test
        void shouldThrowException_WhenNotificationNotFound(){
            // Arrange
            var channel = ChannelFactory.createChannelDefault();
            var notification = NotificationFactory.createNotificationDefault(channel);
            when(notificationRepository.findById(notification.getId())).thenReturn(Optional.empty());

            // Act
            ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                    () -> notificationService.findById(notification.getId().toString()));

            // Assert
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(exception.getReason()).isEqualTo("Não existe notificação para o ID informado!");
        }

        @Test
        void shouldThrowException_WhenInvalidId(){
            // Arrange
            String id = "ID_INVALID";

            // Act
            ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                    () -> notificationService.findById(id));

            // Assert
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(exception.getReason()).isEqualTo("Favor informar um ID válido!");
        }
    }

    @Nested
    class CheckAndSend {

        private static final List<Status> status = List.of(Status.SCHEDULED, Status.ERROR);
        private static final LocalDateTime dateTime = LocalDateTime.now();

        @Test
        void shouldCheckAndSendNotificationsSuccessfully() {
            // Arrange
            var channel = ChannelFactory.createChannelDefault();
            var notifications = NotificationFactory.createListNotificationDefault(channel);

            when(notificationRepository.findByStatusInAndDateTimeBeforeNow(status, dateTime))
                    .thenReturn(notifications);

            // Act
            notificationService.checkAndSend(dateTime);

            // Assert
            verify(notificationRepository, times(1))
                    .findByStatusInAndDateTimeBeforeNow(anyList(), eq(dateTime));
            notifications.forEach(notification -> {
                verify(notificationRepository, times(1)).save(notification);
            });
        }

        @Test
        void shouldNotSendNotifications_WhenNoNotificationsFound() {
            // Arrange
            when(notificationRepository.findByStatusInAndDateTimeBeforeNow(anyList(), eq(dateTime)))
                    .thenReturn(Collections.emptyList());

            // Act
            notificationService.checkAndSend(dateTime);

            // Assert
            verify(notificationRepository, times(1))
                    .findByStatusInAndDateTimeBeforeNow(anyList(), eq(dateTime));
            verify(notificationRepository, times(0)).save(any(Notification.class));
        }
    }
}