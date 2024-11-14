package com.sondeos.javanotifychallenge.services;

import com.sondeos.javanotifychallenge.exception.ContactNotFoundException;
import com.sondeos.javanotifychallenge.providers.*;
import com.sondeos.javanotifychallenge.providers.dto.ContactDto;
import com.sondeos.javanotifychallenge.repository.NotificationRepository;
import com.sondeos.javanotifychallenge.services.dto.NotificationProcessResult;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* Esta clase y sus métodos deben ser modificados para cumplir el challenge */

@Service
public class NotifyServiceImpl implements NotifyService{
    @Autowired
    ContactProvider contactProvider;
    @Autowired
    EmailProvider emailProvider;
    @Autowired
    SmsProvider smsProvider;

    /*
     * Procesa todas las notificaciones y devuelve un objeto con el número de notificaciones procesadas, enviadas y el tiempo de procesamiento
     */
    public NotificationProcessResult processNotifications() {
        //Inicializamos pool de hilos
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            // Iniciamos contador de tiempo
            long startTime = System.currentTimeMillis();

            // Iniciamos contador de notificaciones procesadas
            AtomicInteger processed = new AtomicInteger();

            // Iniciamos contador de notificaciones enviadas
            AtomicInteger sent = new AtomicInteger();

            // Pedimos las notificaciones y las recorremos para enviarlas
            NotificationRepository.getNotifications().forEach(n -> executorService.submit(() -> {
                // Procesamos cada notificación
                Boolean r = this.dispatchNotification(n.get("type"), n.get("contactId"), n.get("message"));

                // Incrementamos el contador de notificaciones procesadas
                processed.getAndIncrement();

                // Incrementamos el contador de notificaciones enviadas si el resultado fue exitoso
                if (r) {
                    sent.getAndIncrement();
                }
            }));

        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Timeout, close pending tasks");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println("The main thread was interrupted.");
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Calculamos el tiempo de procesamiento después de que todas las tareas terminen
        long duration = (System.currentTimeMillis() - startTime) / 1000;

        // Devolvemos el resultado con el número de notificaciones procesadas y enviadas y el tiempo de procesamiento
        return new NotificationProcessResult(processed.get(), sent.get(), duration);
    }

    /*
     * Debe procesar cada notificación e intentar enviarla.
     * Se pueden implementar clases, interfaces o utilidades adicionales para enviar las notificaciones
     * Debe retornar true si la notificación se envió correctamente, false en caso contrario
     */
    @CircuitBreaker(name = "notifyServiceImpl")
    @Retry(name = "notifyServiceImpl")
    public Boolean dispatchNotification(String type, String contactId, String message) {
        if (type != null && contactId != null && message != null) {
            try {
                ContactDto contactDto = contactProvider.getContact(contactId);

                if (contactDto == null) {
                    return false;
                }
                System.out.println("The contact is: " + contactDto.getName() + " ID: " + contactDto.getId() );

                return send(type, contactDto, message);

            } catch (ContactNotFoundException e) {
                return false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }

        return false;
    }

    //Logica envios
    private Boolean send(String type, ContactDto contactDto, String message) {
        if (type.equals("email")) {
           emailProvider.notify(contactDto.getEmail(), message);
           return true;
        } else if (type.equals("sms")) {
           smsProvider.notify(contactDto.getPhoneNumber(), message);
           return true;
        } else {
            System.out.println("Type unknown: " + type);
            return false;
        }
    }
}