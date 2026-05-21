package org.example.orm_project.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationTM {
    private String id;
    private String patientName;
    private String programName;
    private LocalDate registrationDate;
    private double amountPaid;
    private Button editButton;
    private Button deleteButton;
}