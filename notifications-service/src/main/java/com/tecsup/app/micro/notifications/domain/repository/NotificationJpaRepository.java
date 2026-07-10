package com.tecsup.app.micro.notifications.domain.repository;


import com.tecsup.app.micro.notifications.infraestructure.persistence.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository
        extends JpaRepository<NotificationEntity,Long> {
}
