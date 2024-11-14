package com.sondeos.javanotifychallenge;

import com.sondeos.javanotifychallenge.services.NotifyServiceImpl;
import com.sondeos.javanotifychallenge.services.dto.NotificationProcessResult;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotifyServiceImplTests {

    @Autowired
    NotifyServiceImpl notifyServiceImpl;

    @Test
    void processNotifications() {
        NotificationProcessResult result = notifyServiceImpl.processNotifications();
        System.out.println(result.toString());
        assertEquals(200, result.getProcessed());
        assertTrue(result.getSent() >= 185);
        assertTrue(result.getDuration() < 30);
    }
}
