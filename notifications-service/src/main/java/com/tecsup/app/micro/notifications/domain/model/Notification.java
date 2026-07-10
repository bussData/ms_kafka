package com.tecsup.app.micro.notifications.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    private Long id;

    private Long userId;

    private String message;

    private Boolean sent;

    private LocalDateTime createdAt;

}
