package com.sondeos.javanotifychallenge.providers;

import com.sondeos.javanotifychallenge.providers.dto.NotifyPayloadDto;
import com.sondeos.javanotifychallenge.providers.dto.NotifyResultDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/* Esta clase y sus métodos pueden ser modificados si se requiere */

@Component
public class EmailProvider {


    public NotifyResultDto notify(String destination, String message){

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
    }

}
