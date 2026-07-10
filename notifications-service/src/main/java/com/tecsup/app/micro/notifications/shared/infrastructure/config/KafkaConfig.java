package com.tecsup.app.micro.notifications.shared.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaConfig {

    // Set TOPICS
    public static final String COURSE_EVENTS_TOPIC = "course.events";
    public static final String ENROLLMENT_EVENTS_TOPIC = "enrollment.events";
    public static final String ENROLLMENT_UPDATE_TOPIC = "enrollment.events";
    /**
     *  Topic de eventos del curso
     * @return
     */
    @Bean
    public NewTopic courseEventTopic() {

        return new NewTopic(COURSE_EVENTS_TOPIC,  // topic
                3,   // Nro. particiones
                (short) 1  // Nro. de replicas
        );
    }

    @Bean
    public NewTopic enrollmentEventsTopic() {
        return TopicBuilder
                .name(ENROLLMENT_EVENTS_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic enrollmentUpdateTopic() {
        return TopicBuilder
                .name(ENROLLMENT_UPDATE_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }


}
