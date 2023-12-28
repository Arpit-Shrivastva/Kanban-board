package com.example.Notificationservice.Consumer;

import com.example.Notificationservice.Domain.User;
import com.example.Notificationservice.Service.NotificationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer {

    private final NotificationServiceImpl notificationService;

    @Autowired
    public Consumer(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = "notification_Key_1")
    public void Consumer(String jsonfile) {
        // Deserialize the JSON into the User object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(jsonfile, User.class);
            notificationService.saveMessages(user);
            System.out.println("Message: " + user.getMessage().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "notification_Key")
    public void Consumer1(String jsonfile) {
        // Deserialize the JSON into the User object
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(jsonfile, User.class);
            notificationService.saveTaskMessage(user);
            System.out.println("Message: " + user.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
