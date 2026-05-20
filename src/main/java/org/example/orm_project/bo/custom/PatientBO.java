package org.example.orm_project.bo.custom;

import org.example.orm_project.bo.SuperBO;
import org.example.orm_project.entity.Patient;

import java.util.List;

public interface PatientBO extends SuperBO {
    boolean savePatient(Patient patient) throws Exception;
    boolean updatePatient(Patient patient) throws Exception;
    boolean deletePatient(String patientId) throws Exception;
    Patient searchPatient(String patientId) throws Exception;
    List<Patient> getAllPatients() throws Exception;
    String generateNextPatientId() throws Exception;
}