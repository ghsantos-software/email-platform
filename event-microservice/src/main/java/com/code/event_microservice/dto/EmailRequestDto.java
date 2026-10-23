package com.code.event_microservice.dto;

public record EmailRequestDto(String to, String subject, String body) {
}
