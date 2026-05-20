package org.example.orm_project.bo.custom.impl;

import org.example.orm_project.bo.custom.TherapySessionBO;
import org.example.orm_project.dao.DAOFactory;
import org.example.orm_project.dao.DAOTypes;
import org.example.orm_project.dao.custom.TherapySessionDAO;
import org.example.orm_project.entity.TherapySession;

import java.util.List;

public class TherapySessionBOImpl implements TherapySessionBO {

    private final TherapySessionDAO therapySessionDAO =
            (TherapySessionDAO) DAOFactory.getInstance().getDAO(DAOTypes.THERAPY_SESSION);

    @Override
    public boolean saveTherapySession(TherapySession session) throws Exception {
        return therapySessionDAO.save(session);
    }

    @Override
    public boolean updateTherapySession(TherapySession session) throws Exception {
        return therapySessionDAO.update(session);
    }

    @Override
    public boolean deleteTherapySession(String id) throws Exception {
        return therapySessionDAO.delete(id);
    }

    @Override
    public TherapySession searchTherapySession(String id) throws Exception {
        return therapySessionDAO.search(id);
    }

    @Override
    public List<TherapySession> getAllTherapySessions() throws Exception {
        return therapySessionDAO.getAll();
    }

    @Override
    public String generateNextSessionId() throws Exception {
        return "";
    }

    @Override
    public String generateNextTherapySessionId() throws Exception {
        return therapySessionDAO.generateNextId();
    }
}