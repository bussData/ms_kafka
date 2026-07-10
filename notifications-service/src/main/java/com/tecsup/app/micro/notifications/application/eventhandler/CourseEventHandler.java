package com.tecsup.app.micro.notifications.application.eventhandler;

import com.tecsup.app.micro.events.CourseCreatedEvent;
import com.tecsup.app.micro.events.DomainEvent;
import com.tecsup.app.micro.notifications.infraestructure.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CourseEventHandler {

    @KafkaListener(
            topics = KafkaConfig.COURSE_EVENTS_TOPIC,
            groupId = "course-notifications-group"
    )
    public void handleCourseEvents(DomainEvent event) {

        if (event instanceof CourseCreatedEvent) {
            this.handleCourseCreated((CourseCreatedEvent) event);
        } else {
            throw new RuntimeException("Invalid event type " + event.getClass());
        }

    }

    public void handleCourseCreated(CourseCreatedEvent event) {

        log.info("[Kafka] CURSO creado: {}", event);

    }

}
