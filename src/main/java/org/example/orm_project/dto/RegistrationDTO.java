package org.example.orm_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private String id;
    private LocalDate registrationDate;
    private double amountPaid;
    private String patientId;
    private String programId;
}