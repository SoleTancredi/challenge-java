package com.sondeos.javanotifychallenge.providers.dto;

public class NotifyResultDto {

    String status;
    String uuid;

    public NotifyResultDto(String error, String s) {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String toString() {
        return "NotifyResult: {" +
                "status='" + status + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
