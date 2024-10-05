package com.steamDemo.api.service;

import com.steamDemo.api.domain.Repositories.AuditRepository;
import com.steamDemo.api.domain.Transaction.Transaction;
import com.steamDemo.api.domain.Account.Account;
import com.steamDemo.api.domain.Audit.Audit;
import com.steamDemo.api.domain.Repositories.TransactionRepository;
import com.steamDemo.api.domain.Repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuditRepository auditRepository;

    @Transactional
    public Transaction createTransaction(UUID accountId, String paymentType, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        Transaction transaction = Transaction.createTransaction(paymentType, amount, account);
        Transaction savedTransaction = transactionRepository.save(transaction);
        logAudit("Transaction", savedTransaction.getId(), "CREATE", null, savedTransaction.toString());
        return savedTransaction;
    }

    @Transactional
    public void deleteTransaction(UUID transactionId) {
        Transaction existingTransaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
        transactionRepository.delete(existingTransaction);
        logAudit("Transaction", existingTransaction.getId(), "DELETE", existingTransaction.toString(), null);
    }

    private void logAudit(String entityName, UUID entityId, String operation, String oldValue, String newValue) {
        Audit audit = new Audit();
        audit.setEntityName(entityName);
        audit.setEntityId(entityId);
        audit.setOperation(Audit.Operation.valueOf(operation));
        audit.setTimestamp(LocalDateTime.now());
        audit.setChangedBy("system"); // Ou o usuário autenticado
        audit.setOldValue(oldValue);
        audit.setNewValue(newValue);
        auditRepository.save(audit);
    }
}
