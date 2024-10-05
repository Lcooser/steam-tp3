package com.steamDemo.api.domain.Transaction;

import com.steamDemo.api.domain.Account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    private UUID id;

    private String paymentType;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private boolean isValid;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public static Transaction createTransaction(String paymentType, BigDecimal amount, Account account) {
        validatePaymentType(paymentType);
        validateAmount(amount);
        Transaction transaction = new Transaction();
        transaction.setPaymentType(paymentType);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(account);
        transaction.setValid(false);
        return transaction;
    }

    public void completeTransaction() {
        this.isValid = true;
    }

    private static void validatePaymentType(String paymentType) {
        List<String> validPaymentTypes = List.of("DEBIT CARD", "CREDIT CARD", "BANK TRANSFER", "PIX");
        if (!validPaymentTypes.contains(paymentType.toUpperCase())) {
            throw new IllegalArgumentException("Invalid payment type: " + paymentType);
        }
    }

    private static void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }
}
