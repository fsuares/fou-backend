package com.fou.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private java.util.UUID userId;
    private String emailTo;
    private String subject;
    private String text;
}