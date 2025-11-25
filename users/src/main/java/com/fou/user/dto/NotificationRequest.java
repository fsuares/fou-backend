package com.fou.user.dto;
import lombok.Data;

@Data
public class NotificationRequest {
    private String subject;
    private String message;
}