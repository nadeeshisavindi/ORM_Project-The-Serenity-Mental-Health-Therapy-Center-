package org.example.orm_project.dao.custom;

import org.example.orm_project.dao.CrudDAO;
import org.example.orm_project.entity.TherapyProgram;

public interface TherapyProgramDAO extends CrudDAO<TherapyProgram> {

    long countAllPrograms() throws Exception;
}