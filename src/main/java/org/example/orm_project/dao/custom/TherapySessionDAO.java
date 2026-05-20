package org.example.orm_project.dao.custom;

import org.example.orm_project.dao.CrudDAO;
import org.example.orm_project.entity.TherapySession;

import java.time.LocalDate;
import java.util.List;

public interface TherapySessionDAO extends CrudDAO<TherapySession> {


    List<TherapySession> getSessionsByPatientId(String patientId) throws Exception;

    boolean hasConflict(String therapistId, LocalDate date, String time) throws Exception;


    boolean updateStatus(String sessionId, String status) throws Exception;
}