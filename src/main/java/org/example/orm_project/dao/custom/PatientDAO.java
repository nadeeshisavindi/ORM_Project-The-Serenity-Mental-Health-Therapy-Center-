package org.example.orm_project.dao.custom;

import org.example.orm_project.dao.CrudDAO;
import org.example.orm_project.entity.Patient;

import java.util.List;

public interface PatientDAO extends CrudDAO<Patient> {


    Patient searchByNic(String nic) throws Exception;

    List<Patient> getPatientsEnrolledInAllPrograms() throws Exception;
}