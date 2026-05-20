package org.example.orm_project.bo.custom;

import org.example.orm_project.bo.SuperBO;
import org.example.orm_project.entity.Therapist;

import java.util.List;

public interface TherapistBO extends SuperBO {
    boolean saveTherapist(Therapist therapist) throws Exception;
    boolean updateTherapist(Therapist therapist) throws Exception;
    boolean deleteTherapist(String therapistId) throws Exception;
    Therapist searchTherapist(String therapistId) throws Exception;
    List<Therapist> getAllTherapists() throws Exception;
    String generateNextTherapistId() throws Exception;
}