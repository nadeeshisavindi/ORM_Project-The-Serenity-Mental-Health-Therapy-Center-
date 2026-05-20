package org.example.orm_project.bo.custom.impl;

import org.example.orm_project.bo.custom.RegistrationBO;
import org.example.orm_project.dao.DAOFactory;
import org.example.orm_project.dao.DAOTypes;
import org.example.orm_project.dao.custom.RegistrationDAO;
import org.example.orm_project.entity.Registration;

import java.util.List;

public class RegistrationBOImpl implements RegistrationBO {

    private final RegistrationDAO registrationDAO =
            (RegistrationDAO) DAOFactory.getInstance().getDAO(DAOTypes.REGISTRATION);

    @Override
    public boolean saveRegistration(Registration registration) throws Exception {
        return registrationDAO.save(registration);
    }

    @Override
    public boolean updateRegistration(Registration registration) throws Exception {
        return registrationDAO.update(registration);
    }

    @Override
    public boolean deleteRegistration(String registrationId) throws Exception {
        return registrationDAO.delete(registrationId);
    }

    @Override
    public Registration searchRegistration(String registrationId) throws Exception {
        return registrationDAO.search(registrationId);
    }

    @Override
    public List<Registration> getAllRegistrations() throws Exception {
        return registrationDAO.getAll();
    }

    @Override
    public String generateNextRegistrationId() throws Exception {
        return registrationDAO.generateNextId();
    }
}