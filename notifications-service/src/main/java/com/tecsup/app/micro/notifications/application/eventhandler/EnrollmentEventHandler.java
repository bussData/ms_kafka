package com.tecsup.app.micro.notifications.application.eventhandler;


import com.tecsup.app.micro.events.DomainEvent;
import com.tecsup.app.micro.events.EnrollmentCreatedEvent;
import com.tecsup.app.micro.notifications.shared.infrastructure.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Es el consumidor de eventos
 */
@Slf4j
@Component
public class EnrollmentEventHandler {

    //@EventListener
    @KafkaListener(
            topics = KafkaConfig.ENROLLMENT_EVENTS_TOPIC,      // Topico que va a escuchando
            groupId = "enrollment-notifications-group"         // Grupo de consumidores
    )
    public void handleCourseEvents(DomainEvent event) {
        if (event instanceof EnrollmentCreatedEvent) {
            this.handleEnrollmentCreated((EnrollmentCreatedEvent) event);
        }  else {
            throw new RuntimeException("Invalid event type " + event.getClass());
        }
    }


    public void handleEnrollmentCreated(EnrollmentCreatedEvent event)
    {
        log.info("[Kafka] MATRICULA realizada: {}", event);

    }
}