package com.erickmarques.notify_hub.repository;

import com.erickmarques.notify_hub.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByDescription(String description);
}
