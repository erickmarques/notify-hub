package com.erickmarques.notify_hub.integrations;

import com.erickmarques.notify_hub.factory.NotificationCreateDtoFactory;
import com.erickmarques.notify_hub.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
}
