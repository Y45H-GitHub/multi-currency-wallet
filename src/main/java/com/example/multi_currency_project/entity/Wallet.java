package com.example.multi_currency_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter@Setter
@EntityListeners(AuditingEntityListener.class)
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID walletId;

    @Column(nullable = false, updatable = false)
    private UUID userId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;
    // in prod - calculate balance based on the transaction history

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private CurrencyEnum currency;

    @Version
    private Long version;

//    AUDIT FILED

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false, updatable = false)
    private Instant updatedAt;

}
