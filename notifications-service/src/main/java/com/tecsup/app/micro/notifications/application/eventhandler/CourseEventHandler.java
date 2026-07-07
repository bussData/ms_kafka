package com.tecsup.app.micro.notifications.application.eventhandler;

import com.tecsup.app.micro.enrollment.shared.infrastructure.config.KafkaConfig;
import com.tecsup.app.micro.notifications.domain.CourseCreatedEvent;
import com.tecsup.app.micro.notifications.domain.CoursePublishedEvent;
import com.tecsup.app.micro.notifications.shared.domain.event.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


/**
 * Es el consumidor de eventos
 */
@Slf4j
@Component
public class CourseEventHandler {

    //@EventListener
    @KafkaListener(
         topics = KafkaConfig.COURSE_EVENTS_TOPIC,      // Topico que va a escuchando
         groupId = "course-notifications-group"         // Grupo de consumidores
    )
     public void handleCourseEvents(DomainEvent event) {
        if (event instanceof CourseCreatedEvent) {
            this.handleCourseCreated((CourseCreatedEvent) event);
        } else if (event instanceof CoursePublishedEvent) {
                this.handleCoursePublished((CoursePublishedEvent) event);
        } else {
            throw new RuntimeException("Invalid event type " + event.getClass());
        }
    }

    private void handleCoursePublished(CoursePublishedEvent event) {
        log.info("[Kafka] Course published event received: {}", event);

    }

    public void handleCourseCreated(CourseCreatedEvent event)
    {
        log.info("[Kafka] Course created event received: {}", event);

    }

}
