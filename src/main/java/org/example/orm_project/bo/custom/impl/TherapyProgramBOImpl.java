package org.example.orm_project.bo.custom.impl;

import org.example.orm_project.bo.custom.TherapyProgramBO;
import org.example.orm_project.dao.DAOFactory;
import org.example.orm_project.dao.DAOTypes;
import org.example.orm_project.dao.custom.TherapyProgramDAO;
import org.example.orm_project.entity.TherapyProgram;

import java.util.List;

public class TherapyProgramBOImpl implements TherapyProgramBO {

    private final TherapyProgramDAO therapyProgramDAO =
            (TherapyProgramDAO) DAOFactory.getInstance().getDAO(DAOTypes.THERAPY_PROGRAM);

    @Override
    public boolean saveTherapyProgram(TherapyProgram program) throws Exception {
        return therapyProgramDAO.save(program);
    }

    @Override
    public boolean updateTherapyProgram(TherapyProgram program) throws Exception {
        return therapyProgramDAO.update(program);
    }

    @Override
    public boolean deleteTherapyProgram(String id) throws Exception {
        return therapyProgramDAO.delete(id);
    }

    @Override
    public TherapyProgram searchTherapyProgram(String id) throws Exception {
        return therapyProgramDAO.search(id);
    }

    @Override
    public List<TherapyProgram> getAllTherapyPrograms() throws Exception {
        return therapyProgramDAO.getAll();
    }

    @Override
    public String generateNextProgramId() throws Exception {
        return "";
    }

    @Override
    public String generateNextTherapyProgramId() throws Exception {
        return therapyProgramDAO.generateNextId();
    }
}