package com.erickmarques.notify_hub.controller.dto;

import com.erickmarques.notify_hub.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<UUID> create(@RequestBody CreateNotificationDto createNotificationDto) {
        var id = notificationService.create(createNotificationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> getNotification(@PathVariable("id") UUID id) {
        var notification = notificationService.findById(id);
        return ResponseEntity.ok(notification);
    }
}
