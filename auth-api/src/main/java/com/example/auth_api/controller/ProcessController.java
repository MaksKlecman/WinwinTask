package com.example.auth_api.controller;

import com.example.auth_api.dto.ProcessRequest;
import com.example.auth_api.dto.ProcessResponse;
import com.example.auth_api.service.ProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {


    private final ProcessingService processingService;

    public ProcessController( ProcessingService processingService)
    {
        this.processingService = processingService;
    }

    @PostMapping("/api/process")
    public ResponseEntity<?> process(@RequestBody ProcessRequest request, Authentication authentication) {

        String email = authentication.getName();

        String result = processingService.callDataApi(request.getText());

        ProcessResponse response = new ProcessResponse(result);
        return ResponseEntity.ok(response);
    }
}
