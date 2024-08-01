package com.erickmarques.notify_hub.controller.dto;

import com.erickmarques.notify_hub.factory.ChannelFactory;
import com.erickmarques.notify_hub.factory.NotificationCreateDtoFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de teste para {@link NotificationCreateDto}.
 */
public class NotificationCreateDtoTest {

    @Nested
    class ToNotification {

        @Test
        void shouldMapCorrectly() {
            // Arrange
            var channel = ChannelFactory.createChannelDefault();
            var notification = NotificationCreateDtoFactory.createNotificationDtoDefault();

            // Act
            var result = notification.toNotification(channel);

            // Assert
            assertThat(notification.destination()).isEqualTo(result.getDestination());
            assertThat(notification.message()).isEqualTo(result.getMessage());
            assertThat(notification.channel()).isEqualTo(result.getChannel().getDescription());
        }
    }
}
