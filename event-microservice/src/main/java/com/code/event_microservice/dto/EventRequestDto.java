package com.code.event_microservice.dto;

public record EventRequestDto(int maxParticipants, int registeredParticipants, String date, String title, String description) {
}
