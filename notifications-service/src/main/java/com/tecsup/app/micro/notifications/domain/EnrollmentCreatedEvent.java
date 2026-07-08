package com.tecsup.app.micro.notifications.domain;

import com.tecsup.app.micro.notifications.shared.domain.event.DomainEvent;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@ToString
@NoArgsConstructor(force = true)
public class EnrollmentCreatedEvent extends DomainEvent {

    private final String id;
    private final String userId;
    private final String courseId;
    private final String status;

    @Override
    public String getKey(){ return this.id;}
}