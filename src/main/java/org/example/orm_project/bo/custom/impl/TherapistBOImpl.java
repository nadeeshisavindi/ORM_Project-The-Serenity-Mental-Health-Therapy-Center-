package org.example.orm_project.bo.custom.impl;

import org.example.orm_project.bo.custom.TherapistBO;
import org.example.orm_project.dao.DAOFactory;
import org.example.orm_project.dao.DAOTypes;
import org.example.orm_project.dao.custom.TherapistDAO;
import org.example.orm_project.entity.Therapist;

import java.util.List;

public class TherapistBOImpl implements TherapistBO {

    private final TherapistDAO therapistDAO =
            (TherapistDAO) DAOFactory.getInstance().getDAO(DAOTypes.THERAPIST);

    @Override
    public boolean saveTherapist(Therapist therapist) throws Exception {
        return therapistDAO.save(therapist);
    }

    @Override
    public boolean updateTherapist(Therapist therapist) throws Exception {
        return therapistDAO.update(therapist);
    }

    @Override
    public boolean deleteTherapist(String therapistId) throws Exception {
        return therapistDAO.delete(therapistId);
    }

    @Override
    public Therapist searchTherapist(String therapistId) throws Exception {
        return therapistDAO.search(therapistId);
    }

    @Override
    public List<Therapist> getAllTherapists() throws Exception {
        return therapistDAO.getAll();
    }

    @Override
    public String generateNextTherapistId() throws Exception {
        return therapistDAO.generateNextId();
    }
}