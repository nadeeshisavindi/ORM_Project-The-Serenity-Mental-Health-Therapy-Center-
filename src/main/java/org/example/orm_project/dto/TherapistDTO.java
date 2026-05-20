package org.example.orm_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TherapistDTO {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String specialization;
}