package com.natanbsdev.picpaysimplificado.services;

import com.natanbsdev.picpaysimplificado.domain.user.User;
import com.natanbsdev.picpaysimplificado.domain.user.transaction.Transaction;
import com.natanbsdev.picpaysimplificado.dtos.TransactionDTO;
import com.natanbsdev.picpaysimplificado.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    //comunicacao HTTP entre servicos
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User sender = this.userService.findUserById(transactionDTO.senderId());
        User receiver = this.userService.findUserById(transactionDTO.receiverId());

        userService.validadeTransaction(sender, transactionDTO.value());

        boolean isAuthorized = this.authorizeTransaction(sender, transactionDTO.value());
        if (!this.authorizeTransaction(sender, transactionDTO.value())){
            throw new Exception("transação não autorizada");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDTO.value());
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transactionDTO.value()));
        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));

        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotif(sender, "Transação realizada com sucesso!");
        this.notificationService.sendNotif(receiver, "Você recebeu uma nova transação!");


        return newTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        try {
            ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(
                    "https://util.devi.tools/api/v2/authorize", Map.class);

            if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = authorizationResponse.getBody();
                return isAuthorizationSuccessful(responseBody);
            }
        } catch (Exception ex) {

            System.err.println("Erro ao autorizar transação: " + ex.getMessage());
        }

        return false;
    }

    private boolean isAuthorizationSuccessful(Map<String, Object> responseBody) {
        if (responseBody == null) {
            return false;
        }

        String status = (String) responseBody.get("status");
        Map<String, Object> data = (Map<String, Object>) responseBody.get("data");

        return "success".equals(status) &&
                data != null &&
                Boolean.TRUE.equals(data.get("authorization"));
    }
}
