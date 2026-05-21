package org.example.orm_project.dao.custom.impl;

import org.example.orm_project.dao.custom.RegistrationDAO;
import org.example.orm_project.entity.Registration;
import org.example.orm_project.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RegistrationDAOImpl implements RegistrationDAO {

    @Override
    public boolean save(Registration entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        session.persist(entity);

        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Registration entity) throws Exception {
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

        Registration reg = session.get(Registration.class, id);
        if (reg != null) {
            session.remove(reg);
        }

        tx.commit();
        session.close();
        return true;
    }

    @Override
    public Registration search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();

        Registration reg = session.get(Registration.class, id);

        session.close();
        return reg;
    }

    @Override
    public List<Registration> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();

        List<Registration> list = session.createQuery("FROM Registration", Registration.class).list();

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
                "SELECT r.id FROM Registration r ORDER BY r.id DESC",
                String.class
        ).setMaxResults(1).uniqueResult();

        session.close();

        if (lastId == null) return "R001";

        int num = Integer.parseInt(lastId.substring(1)) + 1;
        return String.format("R%03d", num);
    }

    @Override
    public List<Registration> getRegistrationsByPatientId(String patientId) throws Exception {
        return List.of();
    }

    @Override
    public boolean isAlreadyRegistered(String patientId, String programId) throws Exception {
        return false;
    }
}