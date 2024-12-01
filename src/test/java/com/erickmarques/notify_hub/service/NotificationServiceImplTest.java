package com.erickmarques.notify_hub.service;

import com.erickmarques.notify_hub.entity.Notification;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Classe de teste para {@link NotificationServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {
    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private ChannelServiceImpl channelServiceImpl;

    @InjectMocks
    private NotificationServiceImpl notificationServiceImpl;

    @Nested
    class Create {

        private static final String channelDescription = Constants.CHANNEL_DESCRIPTION;

        @Test
        void shouldCreateNotificationSuccessfully() {
            // Arrange
            var notificationCreateDto = NotificationCreateDtoFactory.createNotificationDtoDefault();
            var channel = ChannelFactory.createChannelDefault();
            var notification = NotificationFactory.createNotificationDefault(channel);

            when(channelServiceImpl.findByDescription(channelDescription)).thenReturn(Optional.of(channel));
            when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

            // Act
            var result = notificationServiceImpl.create(notificationCreateDto);

            // Assert
            assertThat(result).isEqualTo(notification.getId().toString());
            verify(channelServiceImpl, times(1)).findByDescription(channelDescription);
            verify(notificationRepository, times(1)).save(any(Notification.class));

        }

        @Test
        void shouldThrowException_WhenChannelNotFound() {
            // Arrange
            var notificationCreateDto = NotificationCreateDtoFactory.createNotificationDtoDefault();

            when(channelServiceImpl.findByDescription(channelDescription)).thenReturn(Optional.empty());

            // Act
            ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                    () -> notificationServiceImpl.create(notificationCreateDto));

            // Assert
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            verify(channelServiceImpl, times(1)).findByDescription(channelDescription);
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
            var result = notificationServiceImpl.findById(notification.getId().toString());

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
                    () -> notificationServiceImpl.findById(notification.getId().toString()));

            // Assert
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(exception.getReason()).isEqualTo("Não existe notificação para o ID informado!");
        }

        @Test
        void shouldThrowException_WhenInvalidId(){
            // Arrange
            var id = "ID_INVALID";

            // Act
            ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                    () -> notificationServiceImpl.findById(id));

            // Assert
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(exception.getReason()).isEqualTo("Favor informar um ID válido!");
        }
    }
}