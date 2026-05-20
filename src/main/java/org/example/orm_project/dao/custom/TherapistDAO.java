package org.example.orm_project.dao.custom;

import org.example.orm_project.dao.CrudDAO;
import org.example.orm_project.entity.Therapist;

import java.util.List;

public interface TherapistDAO extends CrudDAO<Therapist> {


    List<Therapist> searchBySpecialization(String specialization) throws Exception;
}