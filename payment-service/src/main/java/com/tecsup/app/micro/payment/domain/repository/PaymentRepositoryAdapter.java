package com.tecsup.app.micro.payment.domain.repository;

import com.tecsup.app.micro.payment.domain.model.Payment;
import com.tecsup.app.micro.payment.infrastructure.persitence.PaymentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {
    private final PaymentJpaRepository repository;

    @Override
    public Payment save(Payment payment) {

        PaymentEntity entity = PaymentEntity.builder()
                .id(payment.getId() == null ? null : Long.valueOf(payment.getId()))
                .enrollmentId(Long.valueOf(payment.getEnrollmentId()))
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paidAt(payment.getCreatedAt())
                .build();

        PaymentEntity saved = repository.save(entity);

        return Payment.builder()
                .id(saved.getId().toString())
                .enrollmentId(saved.getEnrollmentId())
                .amount(saved.getAmount())
                .status(saved.getStatus())
                .createdAt(saved.getPaidAt())
                .build();
    }

    @Override
    public Optional<Payment> findById(String id) {

        return repository.findById(Long.valueOf(id))
                .map(this::toDomain);
    }

    @Override
    public Optional<Payment> findByEnrollmentId(String enrollmentId) {

        return repository.findByEnrollmentId(Long.valueOf(enrollmentId))
                .map(this::toDomain);
    }

    private Payment toDomain(PaymentEntity entity) {

        return Payment.builder()
                .id(entity.getId().toString())
                .enrollmentId(entity.getEnrollmentId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .createdAt(entity.getPaidAt())
                .build();
    }
}
