package com.natanbsdev.picpaysimplificado.services;

import com.natanbsdev.picpaysimplificado.domain.user.User;
import com.natanbsdev.picpaysimplificado.domain.user.UserType;
import com.natanbsdev.picpaysimplificado.dtos.UserDTO;
import com.natanbsdev.picpaysimplificado.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void validadeTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT) {
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

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }
}
