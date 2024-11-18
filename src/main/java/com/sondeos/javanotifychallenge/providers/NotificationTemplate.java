package com.sondeos.javanotifychallenge.providers;

import com.sondeos.javanotifychallenge.providers.dto.NotifyPayloadDto;
import com.sondeos.javanotifychallenge.providers.dto.NotifyResultDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
public abstract class NotificationTemplate{

    @CircuitBreaker(name = "notifyServiceCircuitBreaker", fallbackMethod = "fallbackNotify")
    @Retry(name = "notifyServiceRetry")
    public NotifyResultDto notify(String destination, String message) {
        NotifyPayloadDto payload = createPayload(destination, message);
        String url = getUrl();

        try {
            RestClient httpClient = RestClient.create();
            return httpClient.post()
                    .uri(url)
                    .header("Content-Type", "application/json")
                    .body(payload)
                    .retrieve()
                    .body(NotifyResultDto.class);

        } catch (HttpServerErrorException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                return new NotifyResultDto("Error", "Error interno del servidor (500). Intentando de nuevo.");
            }
            throw e;

        } catch (HttpClientErrorException e) {
            System.err.println("Error del cliente: " + e.getStatusCode());
            return new NotifyResultDto("Error", "Error del cliente: " + e.getStatusCode());

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return new NotifyResultDto("Error", "Ocurrió un error inesperado.");
        }

    }

    protected abstract NotifyPayloadDto createPayload(String destination, String message);
    protected abstract String getUrl();

    private NotifyResultDto fallbackNotify(String destination, String message, Throwable t) {
        System.err.println("Fallo en el servicio de notificación: " + t.getMessage());
        return new NotifyResultDto("Error", "El servicio está temporalmente fuera de servicio.");
    }
}
