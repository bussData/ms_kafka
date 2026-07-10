package com.tecsup.app.micro.enrollment.application.eventhandler;

import com.tecsup.app.micro.enrollment.application.command.EnrollmentCommandHandler;
import com.tecsup.app.micro.events.PaymentApprovedEvent;
import com.tecsup.app.micro.events.PaymentRejectedEvent;
import com.tecsup.app.micro.events.DomainEvent;
import com.tecsup.app.micro.enrollment.shared.infrastructure.config.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentEventHandler {

    private final EnrollmentCommandHandler enrollmentComandHandler;

    public PaymentEventHandler(EnrollmentCommandHandler enrollmentComandHandler) {
        this.enrollmentComandHandler = enrollmentComandHandler;
    }

    @KafkaListener(
            topics = KafkaConfig.PAYMENT_PROCESSED_TOPIC,
           groupId = "enrollment-group"         // Grupo de consumidores
    )

    /*public void handle(String json) {

        log.info("Mensaje recibido {}", json);

    }*/

   public void handleCourseEvents(DomainEvent event) {
        if (event instanceof PaymentApprovedEvent) {
            this.handleEnrollmentUpdateStatusApproved((PaymentApprovedEvent) event);
        }  else if (event instanceof PaymentRejectedEvent) {
            this.handleEnrollmentUpdateStatusRejected((PaymentRejectedEvent) event);
        } else {
        throw new RuntimeException("Invalid event type " + event.getClass());
        }
    }


    public void handleEnrollmentUpdateStatusApproved(PaymentApprovedEvent event)
    {
        log.info("Pago aprobado {}", event);
        enrollmentComandHandler.updateEnrollmentStatus(String.valueOf(event.getEnrollmentId()),"CONFIRMED");
        log.info("[Kafka] MATRICULA realizada: {}", event);

    }


    public void handleEnrollmentUpdateStatusRejected(PaymentRejectedEvent  event)
    {

        enrollmentComandHandler.updateEnrollmentStatus(String.valueOf(event.getEnrollmentId()),"CANCELLED");
        log.info("[Kafka] MATRICULA realizada: {}", event);

    }
}

