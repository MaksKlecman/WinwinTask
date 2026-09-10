package com.example.data_api.controller;


import com.example.data_api.dto.TransformRequest;
import com.example.data_api.dto.TransformResponse;
import com.example.data_api.service.TransformService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransformController {

    private final TransformService transformService;

    public TransformController(TransformService transformService) {
        this.transformService = transformService;
    }

    @Value("${internal.token}")
    private String expectedToken;


    @PostMapping("/transform")
    public ResponseEntity<?> transform(
            @RequestHeader(value = "X-Internal-Token", required = false) String incomingToken
            , @RequestBody TransformRequest request
            )
    {
        if(expectedToken.equals(incomingToken))
        {
            String result = transformService.transform(request.getText());
            TransformResponse response = new TransformResponse(result);
            return ResponseEntity.ok(response);



        }
        else
        {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();


        }

    }



}
