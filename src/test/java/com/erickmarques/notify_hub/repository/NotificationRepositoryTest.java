package com.erickmarques.notify_hub.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.erickmarques.notify_hub.entity.Channel;
import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.entity.Status;
import com.erickmarques.notify_hub.factory.ChannelFactory;
import com.erickmarques.notify_hub.factory.NotificationFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ChannelRepository channelRepository;

    private Channel channel;
    private List<Notification> notifications;

    @BeforeEach
    void setUp() {
        // Arrange
        channel = channelRepository.save(ChannelFactory.createChannelDefault());
        notifications = notificationRepository.saveAllAndFlush(NotificationFactory.createListNotificationDefault(channel));
    }

    @Test
    void shouldReturnNotifications_WhenStatusIsInAndDateTimeIsBeforeNow() {

        // Arrange
        var status = List.of(Status.SCHEDULED, Status.ERROR);

        // Act
        var result = notificationRepository.findByStatusInAndDateTimeBeforeNow(status, LocalDateTime.now());

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(notifications.get(0).getId());
        assertThat(result.get(0).getDestination()).isEqualTo(notifications.get(0).getDestination());
        assertThat(result.get(0).getMessage()).isEqualTo(notifications.get(0).getMessage());
        assertThat(result.get(0).getStatus().toString()).isEqualTo(notifications.get(0).getStatus().toString());
    }
}