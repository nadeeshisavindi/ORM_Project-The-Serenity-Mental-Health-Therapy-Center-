package org.example.orm_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TherapySessionDTO {
    private String id;
    private LocalDate sessionDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String notes;
    private String status;
    private String patientId;
    private String therapistId;
}