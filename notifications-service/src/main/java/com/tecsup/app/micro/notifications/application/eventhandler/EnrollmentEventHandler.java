package com.tecsup.app.micro.notifications.application.eventhandler;


import com.tecsup.app.micro.events.DomainEvent;
import com.tecsup.app.micro.events.EnrollmentCreatedEvent;
import com.tecsup.app.micro.events.EnrollmentUpdatedEvent;
import com.tecsup.app.micro.notifications.application.command.CreateNotificationCommand;
import com.tecsup.app.micro.notifications.infraestructure.config.KafkaConfig;
import com.tecsup.app.micro.notifications.infraestructure.controller.NotificationController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Es el consumidor de eventos
 */
@Slf4j
@Component
public class EnrollmentEventHandler {

    private final NotificationController notificationCommandHandler;

    public EnrollmentEventHandler(NotificationController notificationCommandHandler) {
        this.notificationCommandHandler = notificationCommandHandler;
    }

    //@EventListener
    @KafkaListener(
            topics = KafkaConfig.ENROLLMENT_EVENTS_TOPIC,      // Topico que va a escuchando
            groupId = "enrollment-notifications-group"         // Grupo de consumidores
    )
    public void handleCourseEvents(DomainEvent event) {
        if (event instanceof EnrollmentCreatedEvent) {
            this.handleEnrollmentCreated((EnrollmentCreatedEvent) event);
        } else if (event instanceof EnrollmentUpdatedEvent) {
            this.handleEnrollmentUpdate((EnrollmentUpdatedEvent) event);

        }else {
            throw new RuntimeException("Invalid event type " + event.getClass());
        }
    }


    public void handleEnrollmentCreated(EnrollmentCreatedEvent event)
    {
        log.info("[Kafka] MATRICULA realizada: {}", event);
        CreateNotificationCommand command = new CreateNotificationCommand(Long.parseLong(event.getUserId())
                , "Se creo la matricula de alumno:" +event.getUserId()+" con matricula id: "+event.getId(), false);
        notificationCommandHandler.createNotification(command);
    }

    public void handleEnrollmentUpdate(EnrollmentUpdatedEvent event)
    {
        log.info("[Kafka] MATRICULA actualizada por estado del pago: {}", event);
        CreateNotificationCommand command = new CreateNotificationCommand(Long.parseLong(event.getStudentId())
                , "Se actualizo la matricula de id: "+event.getStudentId()+" con matricula id: "+event.getId(), true);
        notificationCommandHandler.createNotification(command);

    }
}