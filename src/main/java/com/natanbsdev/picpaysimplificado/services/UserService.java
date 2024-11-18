package com.natanbsdev.picpaysimplificado.services;

import com.natanbsdev.picpaysimplificado.domain.user.User;
import com.natanbsdev.picpaysimplificado.domain.user.userType;
import com.natanbsdev.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validadeTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == userType.MERCHANT) {
            throw new Exception("o tipo logista não está autorizado para realizar transação");
        }
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("saldo insuficiente");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("usuario" +
                "não encontrado"));
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}
