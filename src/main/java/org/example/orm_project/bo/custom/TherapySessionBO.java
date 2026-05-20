package org.example.orm_project.bo.custom;

import org.example.orm_project.bo.SuperBO;
import org.example.orm_project.entity.TherapySession;

import java.util.List;

public interface TherapySessionBO extends SuperBO {
    boolean saveTherapySession(TherapySession therapySession) throws Exception;
    boolean updateTherapySession(TherapySession therapySession) throws Exception;
    boolean deleteTherapySession(String sessionId) throws Exception;
    TherapySession searchTherapySession(String sessionId) throws Exception;
    List<TherapySession> getAllTherapySessions() throws Exception;
    String generateNextSessionId() throws Exception;

    String generateNextTherapySessionId() throws Exception;
}