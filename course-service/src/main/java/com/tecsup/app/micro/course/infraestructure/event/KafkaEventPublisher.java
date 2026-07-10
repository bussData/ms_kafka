package com.tecsup.app.micro.course.infraestructure.event;

import com.tecsup.app.micro.course.domain.event.CourseCreatedEvent;
import com.tecsup.app.micro.course.domain.event.CoursePublishedEvent;
import com.tecsup.app.micro.course.domain.event.DomainEvent;
import com.tecsup.app.micro.course.infraestructure.config.KafkaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

//import pe.edu.tecsup.app.micro.course.domain.event.EnrollmentRequestedEvent;
//import pe.edu.tecsup.lms.payment.domain.event.PaymentFailedEvent;
//import pe.edu.tecsup.lms.payment.domain.event.PaymentProcessedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {

    private final KafkaTemplate<String, DomainEvent> kafkaTemplate;

    public void publish(DomainEvent event) {

        log.info("Publishing {}", event);

        String topic = getTopicFromEvent(event);

        String key = event.getKey();

        //

        this.kafkaTemplate.send(
                topic,
                key,
                event);

    }

    private String getTopicFromEvent(DomainEvent event) {

        if ( event instanceof CourseCreatedEvent ||
                event instanceof CoursePublishedEvent) {
            return KafkaConfig.COURSE_EVENTS_TOPIC;
        } else {
            throw new IllegalArgumentException("Unknown event type: " + event.getEventType());
        }
    }


}
