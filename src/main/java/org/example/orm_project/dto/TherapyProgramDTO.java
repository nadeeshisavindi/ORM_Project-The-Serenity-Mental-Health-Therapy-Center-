package org.example.orm_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TherapyProgramDTO {
    private String id;
    private String name;
    private String description;
    private int durationWeeks;
    private double fee;
    private String therapistId;
}