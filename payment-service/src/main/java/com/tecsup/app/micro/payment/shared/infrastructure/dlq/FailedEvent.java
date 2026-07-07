package com.tecsup.app.micro.payment.shared.infrastructure.dlq;

import com.tecsup.app.micro.payment.shared.domain.event.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class FailedEvent {

    private final DomainEvent event;
    private final String message;
    private final long timestamp;

}
