package com.example.Notificationservice.Repository;

import com.example.Notificationservice.Domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface notificationRepository extends MongoRepository<User, String> {
}
