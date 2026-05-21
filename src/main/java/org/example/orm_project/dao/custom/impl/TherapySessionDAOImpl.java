package org.example.orm_project.dao.custom.impl;

import org.example.orm_project.dao.custom.TherapySessionDAO;
import org.example.orm_project.config.FactoryConfiguration;
import org.example.orm_project.entity.TherapySession;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class TherapySessionDAOImpl implements TherapySessionDAO {

    @Override
    public boolean save(TherapySession entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        session.persist(entity);

        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(TherapySession entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        session.merge(entity);

        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        TherapySession sessionEntity = session.get(TherapySession.class, id);
        if (sessionEntity != null) {
            session.remove(sessionEntity);
        }

        tx.commit();
        session.close();
        return true;
    }

    @Override
    public TherapySession search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();

        TherapySession sessionEntity = session.get(TherapySession.class, id);

        session.close();
        return sessionEntity;
    }

    @Override
    public List<TherapySession> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();

        List<TherapySession> list =
                session.createQuery("FROM TherapySession", TherapySession.class).list();

        session.close();
        return list;
    }

    @Override
    public String getNextId() throws Exception {
        return "";
    }

    @Override
    public String generateNextId() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();

        String lastId = session.createQuery(
                "SELECT t.id FROM TherapySession t ORDER BY t.id DESC",
                String.class
        ).setMaxResults(1).uniqueResult();

        session.close();

        if (lastId == null) return "TS001";

        int num = Integer.parseInt(lastId.substring(2)) + 1;
        return String.format("TS%03d", num);
    }

    @Override
    public List<TherapySession> getSessionsByPatientId(String patientId) throws Exception {
        return List.of();
    }

    @Override
    public boolean hasConflict(String therapistId, LocalDate date, String time) throws Exception {
        return false;
    }

    @Override
    public boolean updateStatus(String sessionId, String status) throws Exception {
        return false;
    }
}