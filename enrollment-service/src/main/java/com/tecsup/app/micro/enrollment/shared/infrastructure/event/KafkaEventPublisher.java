package com.tecsup.app.micro.enrollment.shared.infrastructure.event;


import com.tecsup.app.micro.enrollment.shared.infrastructure.config.KafkaConfig;
import com.tecsup.app.micro.events.DomainEvent;
import com.tecsup.app.micro.events.EnrollmentCreatedEvent;
import com.tecsup.app.micro.events.EnrollmentRequestedEvent;
import com.tecsup.app.micro.events.EnrollmentUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


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

       if (event instanceof EnrollmentRequestedEvent) {  // AGREGAR
            return KafkaConfig.ENROLLMENT_REQUEST_TOPIC;  // AGREGAR
       } else if (event instanceof EnrollmentCreatedEvent) {  // AGREGAR
            return KafkaConfig.ENROLLMENT_EVENTS_TOPIC;
       }else if (event instanceof EnrollmentUpdatedEvent) {  // AGREGAR
               return KafkaConfig.ENROLLMENT_UPDATE_TOPIC;
       }else{
            throw new IllegalArgumentException("Unknown event type: " + event.getEventType());
        }
    }


}
