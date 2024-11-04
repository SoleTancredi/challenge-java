package com.sondeos.javanotifychallenge.providers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ContactDto {
    @NotNull
    String id;
    @NotNull
    @Size(min = 2, max = 50, message = "The name must be between 1 and 50 characters..")
    String name;
    @NotNull
    @Size(min = 2, max = 50, message = "The last name must be between 1 and 50 characters.")
    String surname;

    @NotNull
    @Email(message = "The email does not have a valid format.")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "The email does not have a valid format."
    )
    private String email;

    @NotNull
    @Pattern(
            regexp = "^\\d{11}$",
            message = "The telephone number must contain exactly 11 digits."
    )
    private String phoneNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String toString(){
        return "Contact: {" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
