package org.example.orm_project.bo.custom;

import org.example.orm_project.bo.SuperBO;
import org.example.orm_project.entity.Registration;

import java.util.List;

public interface RegistrationBO extends SuperBO {
    boolean saveRegistration(Registration registration) throws Exception;
    boolean updateRegistration(Registration registration) throws Exception;
    boolean deleteRegistration(String registrationId) throws Exception;
    Registration searchRegistration(String registrationId) throws Exception;
    List<Registration> getAllRegistrations() throws Exception;
    String generateNextRegistrationId() throws Exception;
}