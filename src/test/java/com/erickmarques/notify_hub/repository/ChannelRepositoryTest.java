package com.erickmarques.notify_hub.repository;

import com.erickmarques.notify_hub.entity.Channel;
import com.erickmarques.notify_hub.factory.ChannelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de teste para {@link ChannelRepository}.
 */
@DataJpaTest
@ActiveProfiles("test")
class ChannelRepositoryTest {

    @Autowired
    private ChannelRepository channelRepository;

    @BeforeEach
    void setUp() {
        // Arrange
        channelRepository.saveAll(ChannelFactory.createListChannelDefault());
    }

    @Test
    void shouldReturnChannel_WhenFindByDescription() {

        // Arrange
        var description = ChannelFactory.DESCRIPTION;

        // Act
        var result = channelRepository.findByDescription(description);

        // Assert
        assertThat(result.get().getId()).isNotNull();
        assertThat(result.get().getDescription()).isEqualTo(description);
    }
}