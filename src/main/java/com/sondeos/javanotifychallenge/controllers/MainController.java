package com.sondeos.javanotifychallenge.controllers;

import com.sondeos.javanotifychallenge.notifications.Notification;
import com.sondeos.javanotifychallenge.notifications.NotificationRepository;
import com.sondeos.javanotifychallenge.services.NotifyService;
import com.sondeos.javanotifychallenge.services.dto.NotificationProcessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

@RestController
public class MainController {

    @Autowired
    NotifyService notifyService;


    @GetMapping("/")
    public String home() {
        return "Java Notify Challenge - Welcome!";
    }


    @GetMapping("/process")
    public String process() {

        NotificationProcessResult r = notifyService.processNotifications();

        return "{\"sent\": " + r.getSent() + ", \"duration\": " + r.getDuration() + "}";

    }

}
