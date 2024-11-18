package com.natanbsdev.picpaysimplificado.services;

import com.natanbsdev.picpaysimplificado.dtos.TransactionDTO;
import com.natanbsdev.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;



    public void createTransaction(TransactionDTO transactionDTO) {

    }
}
