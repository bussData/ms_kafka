package com.tecsup.app.micro.notifications.infraestructure.controller;

import com.tecsup.app.micro.notifications.application.command.CreateNotificationCommand;
import com.tecsup.app.micro.notifications.application.command.NotificationCommandHandler;
import com.tecsup.app.micro.notifications.domain.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationCommandHandler notificationCommandHandler;

    /**
     * Registrar una notificación.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notification createNotification(
            @RequestBody CreateNotificationCommand command) {

        log.info("[REST] Creando notificación para userId={}", command.getUserId());

        return notificationCommandHandler.createNotification(command);
    }

    /**
     * Consultar una notificación.
     */
    @GetMapping("/{id}")
    public Notification getNotification(@PathVariable Long id) {

        log.info("[REST] Consultando notificación {}", id);

        return notificationCommandHandler.getNotificationById(id);
    }

    /**
     * Marcar una notificación como enviada.
     */
    @PutMapping("/{id}/sent")
    public Notification markAsSent(@PathVariable Long id) {

        log.info("[REST] Marcando notificación {} como enviada", id);

        return notificationCommandHandler.updateSent(id);
    }

}
