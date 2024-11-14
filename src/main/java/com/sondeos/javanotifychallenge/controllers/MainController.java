package com.sondeos.javanotifychallenge.controllers;

import com.sondeos.javanotifychallenge.providers.ContactProvider;
import com.sondeos.javanotifychallenge.providers.dto.ContactDto;
import com.sondeos.javanotifychallenge.services.NotifyServiceImpl;
import com.sondeos.javanotifychallenge.services.dto.NotificationProcessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class MainController {

    @Autowired
    NotifyServiceImpl notifyServiceImpl;

    @GetMapping("/")
    public String home() {
        return "Java Notify Challenge - Welcome!";
    }


    @GetMapping("/process")
    public String process() {

        NotificationProcessResult r = notifyServiceImpl.processNotifications();

        return "{ \"processed\": " + r.getProcessed() + ",  \"sent\": " + r.getSent() + ", \"duration\": " + r.getDuration() + "}";

    }
}
