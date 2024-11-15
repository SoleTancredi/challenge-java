package com.sondeos.javanotifychallenge.providers;

import com.sondeos.javanotifychallenge.providers.dto.NotifyPayloadDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("smsProvider")
public class SmsProvider extends NotificationTemplate {

    @Override
    protected NotifyPayloadDto createPayload(String destination, String message) {
        return new NotifyPayloadDto(destination, message);
    }

    @Override
    protected String getUrl() {
        return  "http://notify.showvlad.com/api/notify/sms";
    }
}
