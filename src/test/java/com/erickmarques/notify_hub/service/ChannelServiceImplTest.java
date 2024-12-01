package com.erickmarques.notify_hub.service;


import com.erickmarques.notify_hub.factory.ChannelFactory;
import com.erickmarques.notify_hub.factory.Constants;
import com.erickmarques.notify_hub.repository.ChannelRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Classe de teste para {@link ChannelServiceImpl}.
 */
@ExtendWith(MockitoExtension.class)
class ChannelServiceImplTest {

    @Mock
    private ChannelRepository channelRepository;

    @InjectMocks
    private ChannelServiceImpl channelService;

    @Nested
    class FindByDescription {

        private final String description = Constants.CHANNEL_DESCRIPTION;

        @Test
        void shouldReturnChannelWhenDescriptionExists() {
            // Arrange
            var channel = ChannelFactory.createChannelDefault();
            var optionalChannel = Optional.of(channel);

            when(channelRepository.findByDescription(description)).thenReturn(optionalChannel);

            // Act
            var result = channelService.findByDescription(description);

            // Assert
            assertThat(optionalChannel).isEqualTo(result);
        }

        @Test
        void shouldReturnEmptyWhenDescriptionDoesNotExist() {
            // Arrange
            when(channelRepository.findByDescription(description)).thenReturn(Optional.empty());

            // Act
            var result = channelService.findByDescription(description);

            // Assert
            assertThat(Optional.empty()).isEqualTo(result);
        }
    }

    @Nested
    class FindAllChannels {

        @Test
        void shouldReturnAllChannels() {
            // Arrange
            var channels = ChannelFactory.createListChannelDefault();
            when(channelRepository.findAll()).thenReturn(channels);

            // Act
            var result = channelService.findAllChannels();

            // Assert
            assertThat(channels).isEqualTo(result);
            assertThat(channels).hasSize(2);
        }

        @Test
        void shouldReturnEmptyListWhenNoChannels() {
            // Arrange
            var emptyList = Collections.emptyList();
            when(channelRepository.findAll()).thenReturn(Collections.emptyList());

            // Act
            var result = channelService.findAllChannels();

            // Assert
            assertThat(emptyList).isEqualTo(result);
            assertThat(emptyList).hasSize(0);
        }
    }

    @Nested
    class ConcatenateChannels {

        @Test
        void shouldConcatenateChannelDescriptions() {
            // Arrange
            var channels = ChannelFactory.createListChannelDefault();

            // Act
            String result = channelService.concatenateChannels(channels);

            // Assert
            assertThat("EMAIL, WHATSAPP").isEqualTo(result);
        }

        @Test
        void shouldThrowExceptionWhenChannelsListIsEmpty() {
            // Arrange & Act
            ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
                channelService.concatenateChannels(Collections.emptyList());
            });

            // Assert
            assertThat(exception.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(exception.getReason()).isEqualTo("É preciso cadastrar os canais disponíveis!");
        }
    }
}