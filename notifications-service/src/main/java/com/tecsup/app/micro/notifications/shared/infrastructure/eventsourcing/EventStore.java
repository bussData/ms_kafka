package com.tecsup.app.micro.notifications.shared.infrastructure.eventsourcing;

import com.tecsup.app.micro.notifications.shared.domain.event.DomainEvent;

import java.util.List;

public interface EventStore {

    void save(String aggregateId, DomainEvent event);

    List<DomainEvent> getEvents(String aggregateId);

}
