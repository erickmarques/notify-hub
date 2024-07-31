package com.erickmarques.notify_hub.controller;

import com.erickmarques.notify_hub.controller.dto.NotificationCreateDto;
import com.erickmarques.notify_hub.controller.dto.NotificationResponseDto;
import com.erickmarques.notify_hub.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody NotificationCreateDto notificationCreateDto) {
        var id = notificationService.create(notificationCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDto> getNotification(@PathVariable("id") String id) {
        var notification = notificationService.findById(id);
        return ResponseEntity.ok(notification);
    }
}
