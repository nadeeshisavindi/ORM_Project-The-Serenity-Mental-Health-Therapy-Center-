package org.example.orm_project.bo.custom;

import org.example.orm_project.bo.SuperBO;
import org.example.orm_project.entity.TherapyProgram;

import java.util.List;

public interface TherapyProgramBO extends SuperBO {
    boolean saveTherapyProgram(TherapyProgram therapyProgram) throws Exception;
    boolean updateTherapyProgram(TherapyProgram therapyProgram) throws Exception;
    boolean deleteTherapyProgram(String programId) throws Exception;
    TherapyProgram searchTherapyProgram(String programId) throws Exception;
    List<TherapyProgram> getAllTherapyPrograms() throws Exception;
    String generateNextProgramId() throws Exception;

    String generateNextTherapyProgramId() throws Exception;
}