package com.code.event_microservice.service;

import com.code.event_microservice.dto.EmailRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name ="email-service", url = "http://localhost:8090/api/email")
public interface EmailServiceClient {

    @PostMapping("/send")
    void sendEmail(@RequestBody EmailRequestDto emailRequest);
}
