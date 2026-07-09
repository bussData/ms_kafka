package com.tecsup.app.micro.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApprovedEvent extends DomainEvent {

    private Long enrollmentId;
    private String transactionId;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    @Override
    public String getKey() {
        return enrollmentId.toString();
    }

}
