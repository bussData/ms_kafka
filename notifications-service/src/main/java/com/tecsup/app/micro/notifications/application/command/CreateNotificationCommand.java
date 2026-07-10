package com.tecsup.app.micro.notifications.application.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateNotificationCommand {

    private Long userId;
    private String message;

}
