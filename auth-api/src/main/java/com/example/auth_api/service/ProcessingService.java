package com.example.auth_api.service;


import com.example.auth_api.dto.ProcessResponse;
import com.example.auth_api.model.ProcessingLog;
import com.example.auth_api.model.User;
import com.example.auth_api.repository.ProcessRepository;
import com.example.auth_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ProcessingService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final ProcessRepository processRepository;

    public ProcessingService(RestTemplate restTemplate, UserRepository userRepository, ProcessRepository processRepository)

    {

        this.restTemplate = restTemplate;
        this.processRepository = processRepository;
        this.userRepository = userRepository;

    }

    @Value("${internal.token}")
    private String internalToken;

    @Value("${data-api.url}")
    private String dataApiUrl;


    public String processText(String email, String text)
    {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


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

        String result = response.getBody().getResult();

        ProcessingLog log = new ProcessingLog();
        log.setUserId(user.getId());
        log.setInputText(text);
        log.setOutputText(result);
        log.setCreatedAt(LocalDateTime.now());
        processRepository.save(log);



            return result;

    }


}
