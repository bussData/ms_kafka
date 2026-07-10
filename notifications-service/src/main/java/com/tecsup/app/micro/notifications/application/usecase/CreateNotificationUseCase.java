package com.tecsup.app.micro.notifications.application.usecase;

import com.tecsup.app.micro.notifications.domain.model.Notification;
import com.tecsup.app.micro.notifications.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateNotificationUseCase {

    private final NotificationRepository notificationRepository;

    /**
     * Registrar una notificación.
     */
    public Notification registrarNotification(Long userId, String message) {

        Notification notification = Notification.builder()
                .userId(userId)
                .message(message)
                .sent(false)
                .createdAt(LocalDateTime.now())
                .build();

        Notification saved = notificationRepository.save(notification);

        log.info("Notification created: {}", saved.getId());

        return saved;
    }

    /**
     * Buscar una notificación por id.
     */
    public Notification getNotificationById(Long id) {

        return notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found with id: " + id));
    }

    /**
     * Actualizar una notificación.
     */
    public Notification updateNotification(Notification notification) {

        Notification updated = notificationRepository.save(notification);

        log.info("Notification updated: {}", updated.getId());

        return updated;
    }

}
