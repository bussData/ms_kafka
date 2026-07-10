package com.tecsup.app.micro.notifications.domain.repository;

import com.tecsup.app.micro.notifications.domain.model.Notification;

import java.util.Optional;

public interface NotificationRepository {

    Notification save(Notification notification);

    Optional<Notification> findById(Long id);

}
