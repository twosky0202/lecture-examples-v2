package com.growmighty.lectures.firstday.tangledmonolith.payment;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    public static Payment ready(BigDecimal amount) {
        Payment payment = new Payment();
        payment.amount = amount;
        payment.status = PaymentStatus.READY;
        return payment;
    }

//    public void changeStatus(PaymentStatus status) {
//        this.status = status;
//    }
    public void approve() {
        validateReady();
        this.status = PaymentStatus.PAID;
    }

    public void fail() {
        validateReady();
        this.status = PaymentStatus.FAILED;
    }

    private void validateReady() {
        if (this.status != PaymentStatus.READY) {
            throw new IllegalStateException("준비 상태에서만 결제가 가능합니다.");
        }
    }

}
