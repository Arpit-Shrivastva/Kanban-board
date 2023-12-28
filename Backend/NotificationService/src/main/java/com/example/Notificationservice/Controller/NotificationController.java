package com.example.Notificationservice.Controller;

import com.example.Notificationservice.Service.NotificationServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v3")
public class NotificationController {

    private final NotificationServiceImpl notificationService;


    @Autowired
    public NotificationController(NotificationServiceImpl notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/kanban/getemailmsg")
    public ResponseEntity getMessageByEmail(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute("claims");
        String emailId = claims.getSubject();
        return new ResponseEntity(notificationService.getMessagesByEmail(emailId), HttpStatus.OK);
    }
}
