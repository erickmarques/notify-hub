package com.erickmarques.notify_hub.integrations;

import com.erickmarques.notify_hub.factory.NotificationCreateDtoFactory;
import com.erickmarques.notify_hub.factory.NotificationResponseDtoFactory;
import com.erickmarques.notify_hub.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/notifications";

    @Nested
    class Create {
        @Test
        void shouldCreateNotificationSuccessfully() throws Exception {
            // Arrange
            var notificationCreateDto = NotificationCreateDtoFactory.createNotificationDtoDefault();
            var notificationId = "12345";

            when(notificationService.create(notificationCreateDto)).thenReturn(notificationId);

            // Act & Assert
            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(notificationCreateDto)))
                    .andExpect(status().isCreated())
                    .andExpect(content().string(notificationId));
        }

        @Test
        void shouldHandleExceptionWhenDestinationIsBlank() throws Exception {
            // Arrange
            var notificationCreateDto = NotificationCreateDtoFactory.createNotificationDtoWithDestinationIsBlank();

            // Act & Assert
            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(notificationCreateDto)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldHandleExceptionWhenMessageIsBlank() throws Exception {
            // Arrange
            var notificationCreateDto = NotificationCreateDtoFactory.createNotificationDtoWithMessageIsBlank();

            // Act & Assert
            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(notificationCreateDto)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void shouldHandleExceptionWhenChannelIsBlank() throws Exception {
            // Arrange
            var notificationCreateDto = NotificationCreateDtoFactory.createNotificationDtoWithChannelIsBlank();

            // Act & Assert
            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(notificationCreateDto)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class GetNotification {

        @Test
        void shouldReturnNotification_WhenNotificationExists() throws Exception {
            // Arrange
            var notificationResponseDto = NotificationResponseDtoFactory.createNotificationResponseDefault();
            when(notificationService.findById(notificationResponseDto.id().toString())).thenReturn(notificationResponseDto);

            // Act & Assert
            mockMvc.perform(get("/api/notifications/{id}", notificationResponseDto.id())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(notificationResponseDto)));
        }

        @Test
        void shouldReturnNotFoundWhenNotificationDoesNotExist() throws Exception {
            // Arrange
            var id = "ID_INVALID";
            when(notificationService.findById(anyString())).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

            // Act & Assert
            mockMvc.perform(get("/api/notifications/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }
}
