package com.erickmarques.notify_hub.repository;

import com.erickmarques.notify_hub.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
