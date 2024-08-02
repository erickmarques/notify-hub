package com.erickmarques.notify_hub.controller;

import com.erickmarques.notify_hub.controller.dto.NotificationCreateDto;
import com.erickmarques.notify_hub.factory.NotificationCreateDtoFactory;
import com.erickmarques.notify_hub.service.NotificationService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @Nested
    class Create {

        @Test
        void shouldCreateNotificationSuccessfully() {
            // Arrange
            var notificationCreateDto = NotificationCreateDtoFactory.createNotificationDtoDefault();
            var notificationId = UUID.randomUUID().toString();
            when(notificationService.create(notificationCreateDto)).thenReturn(notificationId);

            // Act
            ResponseEntity<String> response = notificationController.create(notificationCreateDto);

            // Assert
            assertThat(HttpStatus.CREATED).isEqualTo(response.getStatusCode());
            assertThat(notificationId).isEqualTo(response.getBody());
            verify(notificationService).create(notificationCreateDto);
        }
    }
}