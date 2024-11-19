package com.natanbsdev.picpaysimplificado.services;

import com.natanbsdev.picpaysimplificado.domain.user.User;
import com.natanbsdev.picpaysimplificado.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.management.Notification;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;


    public void sendNotif(User user, String message ) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);
//        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);
//
//        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
//            System.out.println("erro ao enviar a notificação ");
//            throw new Exception("serviço de notificação está fora do ar");
//        }
    }
}
