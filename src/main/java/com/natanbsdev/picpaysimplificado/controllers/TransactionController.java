package com.natanbsdev.picpaysimplificado.controllers;

import com.natanbsdev.picpaysimplificado.domain.user.transaction.Transaction;
import com.natanbsdev.picpaysimplificado.dtos.TransactionDTO;
import com.natanbsdev.picpaysimplificado.services.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transaction) {
        try {
            Transaction newTransaction = this.transactionService.createTransaction(transaction);
            return new ResponseEntity<>(newTransaction, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar transação: " + e.getMessage());
        }
    }
}