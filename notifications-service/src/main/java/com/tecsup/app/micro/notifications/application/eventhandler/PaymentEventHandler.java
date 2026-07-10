package com.tecsup.app.micro.notifications.application.eventhandler;

import com.tecsup.app.micro.events.DomainEvent;
import com.tecsup.app.micro.events.PaymentApprovedEvent;
import com.tecsup.app.micro.events.PaymentRejectedEvent;
import com.tecsup.app.micro.notifications.application.command.CreateNotificationCommand;
import com.tecsup.app.micro.notifications.infraestructure.config.KafkaConfig;
import com.tecsup.app.micro.notifications.infraestructure.controller.NotificationController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentEventHandler {

    private final NotificationController notificationCommandHandler;

    public PaymentEventHandler(NotificationController notificationCommandHandler) {
        this.notificationCommandHandler = notificationCommandHandler;
    }

    @KafkaListener(
            topics = KafkaConfig.PAYMENT_EVENTS_TOPIC,
            groupId = "payment-notifications-group"
    )
    public void handlePaymentEvents(DomainEvent event) {

        if (event instanceof PaymentApprovedEvent) {

            this.handlePaymentApproved((PaymentApprovedEvent) event);

        } else if (event instanceof PaymentRejectedEvent) {

            this.handlePaymentRejected((PaymentRejectedEvent) event);

        } else {

            throw new RuntimeException("Invalid event type " + event.getClass());

        }

    }

    public void handlePaymentApproved(PaymentApprovedEvent event) {

        log.info("[Kafka] Pago aprobado: {}", event);
        CreateNotificationCommand command = new CreateNotificationCommand(event.getEnrollmentId()
                , "Se aprobo pago de la matricula de id: "+event.getEnrollmentId(), true);
        notificationCommandHandler.createNotification(command);

    }

    public void handlePaymentRejected(PaymentRejectedEvent event) {

        log.info("[Kafka] Pago rechazado: {}", event);
        CreateNotificationCommand command = new CreateNotificationCommand(event.getEnrollmentId()
                , "Se rechazo pago de la matricula de id: "+event.getEnrollmentId()
                +" razon: "+event.getReason(), true);
        notificationCommandHandler.createNotification(command);

    }

}
