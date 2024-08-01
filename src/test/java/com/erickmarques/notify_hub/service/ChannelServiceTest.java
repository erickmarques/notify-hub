package com.erickmarques.notify_hub.service;


import com.erickmarques.notify_hub.factory.ChannelFactory;
import com.erickmarques.notify_hub.factory.Constants;
import com.erickmarques.notify_hub.repository.ChannelRepository;
import org.apache.tomcat.util.bcel.Const;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de teste para {@link ChannelService}.
 */
@ExtendWith(MockitoExtension.class)
class ChannelServiceTest {

    @Mock
    private ChannelRepository channelRepository;

    @InjectMocks
    private ChannelService channelService;

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

}