package com.sondeos.javanotifychallenge;

import com.sondeos.javanotifychallenge.services.NotifyService;
import com.sondeos.javanotifychallenge.services.dto.NotificationProcessResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotifyServiceImplTests {

    @Autowired
    NotifyService notifyService;

    @Test
    void processNotifications() {
        NotificationProcessResult result = notifyService.processNotifications();
        System.out.println(result.toString());
        assertEquals(200, result.getProcessed());
        assertTrue(result.getSent() >= 185);
        assertTrue(result.getDuration() < 30);
    }
}
