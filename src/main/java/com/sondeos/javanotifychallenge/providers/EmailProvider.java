package com.sondeos.javanotifychallenge.providers;

import com.sondeos.javanotifychallenge.providers.dto.NotifyPayloadDto;
import com.sondeos.javanotifychallenge.providers.dto.NotifyResultDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

/* Esta clase y sus métodos pueden ser modificados si se requiere */

@Component
public class EmailProvider {
    public NotifyResultDto notify(String destination, String message) {
        NotifyPayloadDto payload = new NotifyPayloadDto(destination, message);
        RestClient httpClient = RestClient.create();
        String url = "http://notify.showvlad.com/api/notify/email";

        try {
            NotifyResultDto result = httpClient.post()
                    .uri(url)
                    .header("Content-Type", "application/json")
                    .body(payload)
                    .retrieve()
                    .body(NotifyResultDto.class);

            return result;

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                System.err.println("Error 500: EMAIL PROVIDER");
            } else {
                System.err.println("Error HTTP: " + e.getStatusCode());
            }
            return new NotifyResultDto("Error", "No se pudo enviar la notificación debido a un error del servidor.");

        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            return new NotifyResultDto("Error", "Ocurrió un error inesperado.");
        }
    }



  /*  public NotifyResultDto notify(String destination, String message){

        NotifyPayloadDto payload = new NotifyPayloadDto(destination,message);

        RestClient httpClient = RestClient.create();
        String url = "http://notify.showvlad.com/api/notify/email";

        NotifyResultDto result = httpClient.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .body(payload)
                .retrieve()
                .body(NotifyResultDto.class);

        return result;
    }*/

}
