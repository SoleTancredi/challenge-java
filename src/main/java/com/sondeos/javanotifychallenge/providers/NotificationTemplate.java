package com.sondeos.javanotifychallenge.providers;

import com.sondeos.javanotifychallenge.providers.dto.NotifyPayloadDto;
import com.sondeos.javanotifychallenge.providers.dto.NotifyResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

public abstract class NotificationTemplate{
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

        } catch (HttpClientErrorException e) {
            return handleHttpClientErrorException(e);
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return new NotifyResultDto("Error", "Ocurrió un error inesperado.");
        }
    }

    protected abstract NotifyPayloadDto createPayload(String destination, String message);
    protected abstract String getUrl();

    private NotifyResultDto handleHttpClientErrorException(HttpClientErrorException e) {
        if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
            System.out.println("---------- Destino inválido");
            throw e;
        } else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            System.err.println("Error 500");
        } else {
            System.err.println("Error HTTP: " + e.getStatusCode());
        }
        return new NotifyResultDto("Error", "No se pudo enviar la notificación debido a un error en la solicitud.");
    }
}
