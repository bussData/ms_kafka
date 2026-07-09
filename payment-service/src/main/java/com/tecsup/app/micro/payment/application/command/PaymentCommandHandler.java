package com.tecsup.app.micro.payment.application.command;

import com.tecsup.app.micro.events.EnrollmentCreatedEvent;
import com.tecsup.app.micro.events.EnrollmentRequestedEvent;
import com.tecsup.app.micro.events.PaymentApprovedEvent;
import com.tecsup.app.micro.events.PaymentRejectedEvent;
import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.domain.model.PaymentStatus;
import com.tecsup.app.micro.payment.domain.repository.PaymentRepository;
import com.tecsup.app.micro.payment.infrastructure.event.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentCommandHandler {

    private final PaymentRepository paymentRepository;
    private final KafkaEventPublisher kafkaEventPublisher;
    private final Random random = new Random();

    public Payment createPayment(CreatePaymentCommand command)  {

        Payment payment = Payment.create(
                command.getEnrollmentId(),
                command.getAmount()
        );
        try {
            Thread.sleep(1000 + random.nextInt(2000)); // 1-3 segundos

            boolean approved = random.nextInt(100) < 60;

            if (approved) {

                log.info("Pago aprobado.");
                 // Generas un codigo de transaccion
                String transactionId = "tx-" + UUID.randomUUID();

                // Generar el evento
                PaymentApprovedEvent processedEvent = new PaymentApprovedEvent(
                        Long.valueOf(payment.getEnrollmentId()),
                        transactionId,
                        payment.getAmount(),
                        LocalDateTime.now());

                kafkaEventPublisher.publish(processedEvent);

                log.info("[PAYMENT] Pago procesado exitosamente");

            } else {
                log.warn("❌ [PAYMENT] El pago falló para enrollment ID: {}", payment.getEnrollmentId());

                // TO DO

                // Genera el event del error

                PaymentRejectedEvent failedEvent = new PaymentRejectedEvent(
                        Long.valueOf(payment.getEnrollmentId()),
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
        return payment;
    }

}
