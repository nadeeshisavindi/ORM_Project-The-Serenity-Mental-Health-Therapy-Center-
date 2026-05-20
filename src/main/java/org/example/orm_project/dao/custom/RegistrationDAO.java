package org.example.orm_project.dao.custom;

import org.example.orm_project.dao.CrudDAO;
import org.example.orm_project.entity.Registration;

import java.util.List;

public interface RegistrationDAO extends CrudDAO<Registration> {


    List<Registration> getRegistrationsByPatientId(String patientId) throws Exception;

    // Patient already registered  check
    boolean isAlreadyRegistered(String patientId, String programId) throws Exception;
}