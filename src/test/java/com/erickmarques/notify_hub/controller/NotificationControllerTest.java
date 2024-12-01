package com.erickmarques.notify_hub.controller;

import com.erickmarques.notify_hub.factory.NotificationCreateDtoFactory;
import com.erickmarques.notify_hub.factory.NotificationResponseDtoFactory;
import com.erickmarques.notify_hub.service.NotificationServiceImpl;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationServiceImpl notificationServiceImpl;

    @InjectMocks
    private NotificationController notificationController;

    @Nested
    class Create {

        @Test
        void shouldCreateNotificationSuccessfully() {
            // Arrange
            var notificationCreateDto = NotificationCreateDtoFactory.createNotificationDtoDefault();
            var notificationId = UUID.randomUUID().toString();
            when(notificationServiceImpl.create(notificationCreateDto)).thenReturn(notificationId);

            // Act
            ResponseEntity<String> response = notificationController.create(notificationCreateDto);

            // Assert
            assertThat(HttpStatus.CREATED).isEqualTo(response.getStatusCode());
            assertThat(notificationId).isEqualTo(response.getBody());
            verify(notificationServiceImpl).create(notificationCreateDto);
        }
    }

    @Nested
    class GetNotification {

        @Test
        void shouldReturnNotificationSuccessfully() {
            // Arrange
            var notificationResponseDto = NotificationResponseDtoFactory.createNotificationResponseDefault();
            var id = notificationResponseDto.id().toString();

            when(notificationServiceImpl.findById(anyString())).thenReturn(notificationResponseDto);

            // Act
            var response = notificationController.getNotification(id);

            // Assert
            assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
            assertThat(notificationResponseDto).isEqualTo(response.getBody());
            verify(notificationServiceImpl).findById(id);
        }

        @Test
        void shouldThrowExceptionWhenNotificationNotFound() {
            // Arrange
            var notificationId = "ID_NOT_FOUND";
            var msgException = "Não existe notificação para o ID informado!";
            when(notificationServiceImpl.findById(notificationId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, msgException));

            // Act & Assert
            ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
                notificationController.getNotification(notificationId);
            });

            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(exception.getReason()).isEqualTo(msgException);
            verify(notificationServiceImpl).findById(notificationId);
        }
    }
}