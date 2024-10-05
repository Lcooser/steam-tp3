package com.steamDemo.api.domain.Purchase;

import com.steamDemo.api.domain.Account.Account;
import com.steamDemo.api.domain.Game.Game;
import com.steamDemo.api.domain.Transaction.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "purchases")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue
    private UUID id;

    private LocalDateTime purchaseDate;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    public static Purchase createPurchase(Account account, Game game, Transaction transaction) {
        if (transaction == null || !transaction.isValid()) {
            throw new IllegalStateException("Cannot create purchase: transaction is not completed.");
        }
        Purchase purchase = new Purchase();
        purchase.setAccount(account);
        purchase.setGame(game);
        purchase.setTransaction(transaction);
        purchase.setPurchaseDate(LocalDateTime.now());
        return purchase;
    }
}
