package org.example.orm_project.dao.custom.impl;

import org.example.orm_project.dao.custom.TherapyProgramDAO;
import org.example.orm_project.db.FactoryConfiguration;
import org.example.orm_project.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {

    @Override
    public boolean save(TherapyProgram entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction tx = session.beginTransaction();

        session.persist(entity);

        tx.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(TherapyProgram entity) throws Exception {
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

        TherapyProgram program = session.get(TherapyProgram.class, id);
        if (program != null) {
            session.remove(program);
        }

        tx.commit();
        session.close();
        return true;
    }

    @Override
    public TherapyProgram search(String id) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();

        TherapyProgram program = session.get(TherapyProgram.class, id);

        session.close();
        return program;
    }

    @Override
    public List<TherapyProgram> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();

        List<TherapyProgram> list =
                session.createQuery("FROM TherapyProgram", TherapyProgram.class).list();

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
                "SELECT t.id FROM TherapyProgram t ORDER BY t.id DESC",
                String.class
        ).setMaxResults(1).uniqueResult();

        session.close();

        if (lastId == null) return "TP001";

        int num = Integer.parseInt(lastId.substring(2)) + 1;
        return String.format("TP%03d", num);
    }

    @Override
    public long countAllPrograms() throws Exception {
        return 0;
    }
}