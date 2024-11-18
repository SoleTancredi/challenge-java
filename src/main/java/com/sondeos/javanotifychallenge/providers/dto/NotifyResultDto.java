package com.sondeos.javanotifychallenge.providers.dto;

public class NotifyResultDto {

    String status;
    String id;

    public NotifyResultDto(String error, String s) {
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return "NotifyResult: {" +
                "status='" + status + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

}
