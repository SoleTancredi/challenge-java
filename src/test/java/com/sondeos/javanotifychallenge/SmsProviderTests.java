package com.sondeos.javanotifychallenge;

import com.sondeos.javanotifychallenge.providers.SmsProvider;
import com.sondeos.javanotifychallenge.providers.dto.NotifyResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class SmsProviderTests {

    @Autowired
    SmsProvider smsProvider;


    @Test
    void sendSMS() {
        String destination = "1122334455";
        String message = "Hello from Java Notify Challenge!";
        NotifyResultDto result = smsProvider.notify( destination, message);
        System.out.println(result.toString());
        assertEquals("sent", result.getStatus());
    }

    @Test
    void sendSMSWithInvalidDestination() {
        String destination = "123";
        String message = "Hello from Java Notify Challenge!";
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            smsProvider.notify( destination, message);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void sendSMSWithInvalidMessage() {
        String destination = "1122334455";
        String message = "The Java Notify Challenge is designed to test not only your skills but your patience and persistence. You've shown incredible resolve, and we encourage you to continue pushing your limits.";
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            smsProvider.notify( destination, message);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

}
