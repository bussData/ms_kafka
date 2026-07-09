package com.tecsup.app.micro.payment.application.saga;

import com.tecsup.app.micro.events.EnrollmentRequestedEvent;
import com.tecsup.app.micro.events.PaymentApprovedEvent;
import com.tecsup.app.micro.events.PaymentRejectedEvent;
import com.tecsup.app.micro.payment.infrastructure.config.KafkaConfig;
import com.tecsup.app.micro.payment.infrastructure.event.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentSagaHandler {

    private final KafkaEventPublisher kafkaEventPublisher;
    private final Random random = new Random();

    @KafkaListener(
            topics = KafkaConfig.ENROLLMENT_REQUEST_TOPIC,  //"enrollment.requested",
            groupId = "payment-service-group"
    )
    @Transactional
    public void handleEnrollmentRequested(EnrollmentRequestedEvent event) {

        log.info("[PAYMENT] Procesando pago para enrollment");
        log.info("Enrollment requested: {}", event);

        // Simulamos process exitoso y fallidos

        try {

            Thread.sleep(1000 + random.nextInt(2000)); // 1-3 segundos

            boolean paymentSuccess =  random.nextInt(100) < 60;

            if(paymentSuccess) {
                log.info("[PAYMENT] Pago procesado exitosamente para enrollment ID: {}", event.getEnrollmentId());

                // Generas un codigo de transaccion
                String transactionId = "tx-" + UUID.randomUUID();

                // Generar el evento
                PaymentApprovedEvent processedEvent = new PaymentApprovedEvent(
                        Long.valueOf(event.getEnrollmentId()),
                        transactionId,
                        event.getAmount(),
                        LocalDateTime.now());

                kafkaEventPublisher.publish(processedEvent);

                log.info("[PAYMENT] Pago procesado exitosamente");

            } else {
                log.warn("❌ [PAYMENT] El pago falló para enrollment ID: {}", event.getEnrollmentId());

                // TO DO

                // Genera el event del error

                PaymentRejectedEvent failedEvent = new PaymentRejectedEvent(
                        Long.valueOf(event.getEnrollmentId()),
                        "PAYMENT_DECLINED",
                        "El pago fue rechazado por el proveedor, saldo insuficiente.",
                        LocalDateTime.now()
                );


                // Publica el evento fallido
                this.kafkaEventPublisher.publish(failedEvent);

                log.warn("📨 [PAYMENT] Evento PaymentFailed publicado");

            }


        } catch (InterruptedException e) {
            log.error("[PAYMENT] Error procesando pago", e);
        }



    }
}
