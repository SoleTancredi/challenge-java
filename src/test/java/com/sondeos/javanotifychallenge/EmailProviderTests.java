package com.sondeos.javanotifychallenge;

import com.sondeos.javanotifychallenge.providers.EmailProvider;
import com.sondeos.javanotifychallenge.providers.dto.NotifyResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class EmailProviderTests {

    @Autowired
    EmailProvider emailProvider;


    @Test
    void sendEmail() {
        String destination = "challenge@example.com";
        String message = "Hello from Java Notify Challenge!";
        NotifyResultDto result = emailProvider.notify(destination, message);
        System.out.println(result.toString());
        assertEquals("sent", result.getStatus());
    }


    @Test
    void sendEmailWithInvalidDestination() {
        String destination = "challenge-invalid-email";
        String message = "Hello from Java Notify Challenge!";
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            emailProvider.notify(destination, message);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

    }

}
