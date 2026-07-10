package com.tecsup.app.micro.notifications.application.eventhandler;

import com.tecsup.app.micro.events.CourseCreatedEvent;
import com.tecsup.app.micro.events.CoursePublishedEvent;
import com.tecsup.app.micro.events.DomainEvent;
import com.tecsup.app.micro.notifications.application.command.CreateNotificationCommand;
import com.tecsup.app.micro.notifications.infraestructure.config.KafkaConfig;
import com.tecsup.app.micro.notifications.infraestructure.controller.NotificationController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CourseEventHandler {

    private final   NotificationController notificationCommandHandler;

    public CourseEventHandler(NotificationController notificationCommandHandler) {
        this.notificationCommandHandler = notificationCommandHandler;
    }

    @KafkaListener(
            topics = KafkaConfig.COURSE_EVENTS_TOPIC,
            groupId = "course-notifications-group"
    )
    public void handleCourseEvents(DomainEvent event) {

        if (event instanceof CourseCreatedEvent) {
            this.handleCourseCreated((CourseCreatedEvent) event);
        } else if(event instanceof CoursePublishedEvent) {
            this.handleCoursePublished((CoursePublishedEvent) event);
        }else{
            throw new RuntimeException("Invalid event type " + event.getClass());
        }

    }

    public void handleCourseCreated(CourseCreatedEvent event) {

        log.info("[Kafka] CURSO creado: {}", event);
        CreateNotificationCommand command = new CreateNotificationCommand(Long.parseLong(event.getCourseId())
                , "Se creo el curso: "+event.getTitle(), true);
        notificationCommandHandler.createNotification(command);
    }
    public void handleCoursePublished(CoursePublishedEvent event) {

        log.info("[Kafka] CURSO publicado: {}", event);
        CreateNotificationCommand command = new CreateNotificationCommand(Long.parseLong(event.getCourseId())
                , "Se publico el curso: "+event.getTitle(), true);
        notificationCommandHandler.createNotification(command);
    }

}
