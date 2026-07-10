package com.tecsup.app.micro.notifications.application.eventhandler;

import com.tecsup.app.micro.events.DomainEvent;
import com.tecsup.app.micro.events.PaymentApprovedEvent;
import com.tecsup.app.micro.events.PaymentRejectedEvent;
import com.tecsup.app.micro.notifications.infraestructure.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentEventHandler {

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

    }

    public void handlePaymentRejected(PaymentRejectedEvent event) {

        log.info("[Kafka] Pago rechazado: {}", event);

    }

}
