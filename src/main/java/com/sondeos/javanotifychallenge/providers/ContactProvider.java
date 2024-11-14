package com.sondeos.javanotifychallenge.providers;

import com.sondeos.javanotifychallenge.config.CacheConfig;
import com.sondeos.javanotifychallenge.providers.dto.ContactDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

/* Esta clase y sus métodos pueden ser modificados si se requiere */

@Service
public class ContactProvider {
    private final RestClient httpClient = RestClient.create();
    private static final String BASE_URL = "http://notify.showvlad.com/api/contact/";

    @Cacheable(value = CacheConfig.CONTACTS_INFO_CACHE, unless = "#result == null")
    public ContactDto getContact(String id) {
        String url = BASE_URL + id;
        try {
            return httpClient.get()
                    .uri(url)
                    .retrieve()
                    .body(ContactDto.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                System.out.println("Contact NOT FOUND -> 404 : ID " + id);
                throw e;
            }
            throw new RuntimeException("Error al obtener el contacto: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error desconocido al obtener el contacto", e);
        }
    }
    }


