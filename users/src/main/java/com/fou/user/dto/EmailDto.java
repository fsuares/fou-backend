package com.fou.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class EmailDto {
    private UUID userId;
    private String emailTo;
    private String subject;
    private String text;
}