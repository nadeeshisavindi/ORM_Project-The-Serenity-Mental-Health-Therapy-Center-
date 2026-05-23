package org.example.orm_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registration")
public class Registration {

    @Id
    @Column(name = "id", length = 10)
    private String id;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Column(name = "amount_paid")
    private double amountPaid;


    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;


    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private TherapyProgram therapyProgram;


    @OneToMany(mappedBy = "registration", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;
}