package org.example.orm_project.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTM {
    private String id;
    private String registrationId;
    private LocalDate paymentDate;
    private double amount;
    private String status;
    private Button actionButton;
}