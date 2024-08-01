package com.erickmarques.notify_hub.controller.dto;


import com.erickmarques.notify_hub.factory.ChannelFactory;
import com.erickmarques.notify_hub.factory.NotificationFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de teste para {@link NotificationResponseDto}.
 */
class NotificationResponseDtoTest {

    @Nested
    class ToDto {

        @Test
        void shouldMapCorrectly() {
            // Arrange
            var channel = ChannelFactory.createChannelDefault();
            var notification = NotificationFactory.createNotificationDefault(channel);

            // Act
            var result = NotificationResponseDto.toDto(notification);

            // Assert
            assertThat(result.destination()).isEqualTo(notification.getDestination());
            assertThat(result.message()).isEqualTo(notification.getMessage());
            assertThat(result.channel()).isEqualTo(notification.getChannel().getDescription());
        }
    }
}