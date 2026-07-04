package com.tecsup.app.micro.enrollment.infrastructure.config;

import com.tecsup.app.micro.enrollment.application.command.EnrollmentCommandHandler;
import com.tecsup.app.micro.enrollment.shared.infrastructure.eventsourcing.MemoryEventStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnrollmentConfiguration {

    @Bean
    public EnrollmentCommandHandler enrollmentCommandHandler(MemoryEventStore eventStore) {
        return new EnrollmentCommandHandler(eventStore);
    }
}