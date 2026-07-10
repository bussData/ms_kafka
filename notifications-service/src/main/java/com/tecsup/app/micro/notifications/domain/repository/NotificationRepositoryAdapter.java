package com.tecsup.app.micro.notifications.domain.repository;

import com.tecsup.app.micro.notifications.domain.model.Notification;
import com.tecsup.app.micro.notifications.domain.repository.NotificationRepository;
import com.tecsup.app.micro.notifications.infraestructure.persistence.NotificationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryAdapter
        implements NotificationRepository {

    private final NotificationJpaRepository repository;

    @Override
    public Notification save(Notification notification) {

        NotificationEntity entity =
                NotificationEntity.builder()
                        .id(notification.getId())
                        .userId(notification.getUserId())
                        .message(notification.getMessage())
                        .sent(notification.getSent())
                        .createdAt(notification.getCreatedAt())
                        .build();

        NotificationEntity saved = repository.save(entity);

        return Notification.builder()
                .id(saved.getId())
                .userId(saved.getUserId())
                .message(saved.getMessage())
                .sent(saved.getSent())
                .createdAt(saved.getCreatedAt())
                .build();

    }

    @Override
    public Optional<Notification> findById(Long id) {

        return repository.findById(id)
                .map(entity ->
                        Notification.builder()
                                .id(entity.getId())
                                .userId(entity.getUserId())
                                .message(entity.getMessage())
                                .sent(entity.getSent())
                                .createdAt(entity.getCreatedAt())
                                .build());

    }

}
