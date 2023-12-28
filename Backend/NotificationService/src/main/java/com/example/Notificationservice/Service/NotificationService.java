package com.example.Notificationservice.Service;

import com.example.Notificationservice.Domain.User;

import java.util.List;

public interface NotificationService {

    void saveMessages(User message);

    void saveTaskMessage(User message);

    List<User> getMessagesGroupedByUser();

    List<String> getMessagesByEmail(String email);

}
