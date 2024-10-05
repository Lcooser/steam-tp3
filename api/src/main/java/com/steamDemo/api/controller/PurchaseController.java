package com.steamDemo.api.controller;

import com.steamDemo.api.domain.Purchase.Purchase;
import com.steamDemo.api.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping
    public ResponseEntity<Purchase> createPurchase(@RequestParam UUID accountId, @RequestParam UUID gameId,
                                                   @RequestParam UUID transactionId) {
        Purchase purchase = purchaseService.createPurchase(accountId, gameId, transactionId);
        return new ResponseEntity<>(purchase, HttpStatus.CREATED);
    }

    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<Void> deletePurchase(@PathVariable UUID purchaseId) {
        purchaseService.deletePurchase(purchaseId);
        return ResponseEntity.noContent().build();
    }
}
