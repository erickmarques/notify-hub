package com.erickmarques.notify_hub.repository;

import com.erickmarques.notify_hub.entity.Notification;
import com.erickmarques.notify_hub.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    @Query("SELECT n FROM Notification n WHERE n.status IN :status AND n.dateTime <= :now")
    List<Notification> findByStatusInAndDateTimeBeforeNow(@Param("status") List<Status> status, @Param("now") LocalDateTime now);
}
