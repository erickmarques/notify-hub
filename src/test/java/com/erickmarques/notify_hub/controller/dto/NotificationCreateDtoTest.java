package com.erickmarques.notify_hub.controller.dto;

import com.erickmarques.notify_hub.factory.ChannelFactory;
import com.erickmarques.notify_hub.factory.NotificationCreateDtoFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;
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

    @Nested
    class Validation {

        private Validator validator;

        @BeforeEach
        void setUp() {
            ValidatorFactory factory = buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }

        @Test
        void shouldNotValidateWhenDestinationIsBlank() {
            // Arrange
            NotificationCreateDto notificationCreateDto = NotificationCreateDto.builder()
                                                            .destination("")
                                                            .message("TESTE MENSAGEM!")
                                                            .channel("EMAIL")
                                                            .build();

            // Act
            Set<ConstraintViolation<NotificationCreateDto>> violations = validator.validate(notificationCreateDto);
            ConstraintViolation<NotificationCreateDto> violation = violations.iterator().next();

            // Assert
            assertThat(1).isEqualTo(violations.size());
            assertThat("Favor informar o destino!").isEqualTo(violation.getMessage());
        }

        @Test
        void shouldNotValidateWhenMessageIsBlank() {
            // Arrange
            NotificationCreateDto notificationCreateDto = NotificationCreateDto.builder()
                                                            .destination("TESTE DESTINATION!")
                                                            .message("")
                                                            .channel("EMAIL")
                                                            .build();

            // Act
            Set<ConstraintViolation<NotificationCreateDto>> violations = validator.validate(notificationCreateDto);
            ConstraintViolation<NotificationCreateDto> violation = violations.iterator().next();

            // Assert
            assertThat(1).isEqualTo(violations.size());
            assertThat("Favor informar a mensagem!").isEqualTo(violation.getMessage());
        }

        @Test
        void shouldNotValidateWhenChannelIsBlank() {
            // Arrange
            NotificationCreateDto notificationCreateDto = NotificationCreateDto.builder()
                                                            .destination("TESTE DESTINATION!")
                                                            .message("TESTE DESTINATION!")
                                                            .channel("")
                                                            .build();

            // Act
            Set<ConstraintViolation<NotificationCreateDto>> violations = validator.validate(notificationCreateDto);
            ConstraintViolation<NotificationCreateDto> violation = violations.iterator().next();

            // Assert
            assertThat(1).isEqualTo(violations.size());
            assertThat("Favor informar o canal!").isEqualTo(violation.getMessage());
        }
    }
}
