package com.erickmarques.notify_hub.repository;

import com.erickmarques.notify_hub.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
