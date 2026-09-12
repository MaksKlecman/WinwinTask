package com.example.auth_api.service;


import com.example.auth_api.dto.ProcessResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.Map;

@Service
public class ProcessingService {

    private final RestTemplate restTemplate;

    public ProcessingService(RestTemplate restTemplate)
    {
        this.restTemplate = restTemplate;
    }

    @Value("${internal.token}")
    private String internalToken;

    @Value("${data-api.url}")
    private String dataApiUrl;


    public String callDataApi(String text) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Internal-Token", internalToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> requestEntity =
                    new HttpEntity<>(Map.of("text", text), headers);

            ResponseEntity<ProcessResponse> response = restTemplate.postForEntity(
                    dataApiUrl + "/api/transform",
                    requestEntity,
                    ProcessResponse.class
            );


            return response.getBody().getResult();
        } catch (Exception e) {

            e.printStackTrace();
            throw e;
        }
    }


}
