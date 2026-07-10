package com.tecsup.app.micro.notifications.application.command;


import com.tecsup.app.micro.notifications.application.usecase.CreateNotificationUseCase;
import com.tecsup.app.micro.notifications.domain.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationCommandHandler {

    private final CreateNotificationUseCase createNotificationUseCase;

    /**
     * Registrar una notificación.
     */
    public Notification createNotification(CreateNotificationCommand command) {

        Notification notification =
                createNotificationUseCase.registrarNotification(
                        command.getUserId(),
                        command.getMessage()
                );

        log.info("[NOTIFICATION] Notificación registrada correctamente. Id={}",
                notification.getId());

        return notification;
    }

    /**
     * Obtener una notificación por id.
     */
    public Notification getNotificationById(Long id) {

        return createNotificationUseCase.getNotificationById(id);
    }

    /**
     * Marcar la notificación como enviada.
     */
    public Notification updateSent(Long id) {

        Notification notification =
                createNotificationUseCase.getNotificationById(id);

        notification.setSent(true);

        notification =
                createNotificationUseCase.updateNotification(notification);

        log.info("[NOTIFICATION] Notificación {} marcada como enviada",
                notification.getId());

        return notification;
    }

}
