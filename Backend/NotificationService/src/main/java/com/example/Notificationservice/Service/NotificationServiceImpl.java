package com.example.Notificationservice.Service;

import com.example.Notificationservice.Domain.User;
import com.example.Notificationservice.Repository.notificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final User user = new User();


    private final notificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(notificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void saveMessages(User message) {
        String user = message.getEmail();
        List<String> userMessages = message.getMessage();

        User updateUser = notificationRepository.findById(user).orElse(null);

        if (updateUser == null) {
            updateUser = new User();
            updateUser.setEmail(user);
            updateUser.setMessage(new ArrayList<>());
        }

        updateUser.getMessage().addAll(userMessages);
        notificationRepository.save(updateUser);
    }

    @Override
    public void saveTaskMessage(User message) {
        String user = message.getEmail();
        List<String> usermessage = message.getMessage();

        User Updateuser = notificationRepository.findById(user).orElse(null);
        if (Updateuser == null) {
            Updateuser = new User();
            Updateuser.setEmail(user);
            Updateuser.setMessage(new ArrayList<>());
        }

        Updateuser.getMessage().addAll(usermessage);
        notificationRepository.save(Updateuser);
    }

    @Override
    public List<User> getMessagesGroupedByUser() {
        return notificationRepository.findAll();
    }

    @Override
    public List<String> getMessagesByEmail(String email) {
        List<String> messages = notificationRepository.findById(email).get().getMessage();

        if (messages == null) {
            return new ArrayList<>();
        } else {
            return messages;
        }
    }

}


