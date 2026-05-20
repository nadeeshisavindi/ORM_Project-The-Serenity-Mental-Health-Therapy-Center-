package org.example.orm_project.bo.custom.impl;

import org.example.orm_project.bo.custom.UserBO;
import org.example.orm_project.dao.DAOFactory;
import org.example.orm_project.dao.DAOTypes;
import org.example.orm_project.dao.custom.UserDAO;
import org.example.orm_project.entity.User;

import java.util.List;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO =
            (UserDAO) DAOFactory.getInstance().getDAO(DAOTypes.USER);

    @Override
    public boolean saveUser(User user) throws Exception {
        return userDAO.save(user);
    }

    @Override
    public boolean updateUser(User user) throws Exception {
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(String userId) throws Exception {
        return userDAO.delete(userId);
    }

    @Override
    public User searchUser(String userId) throws Exception {
        return userDAO.search(userId);
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        return userDAO.getAll();
    }

    @Override
    public User getUserByUsername(String username) throws Exception {
        return userDAO.getUserByUsername(username);
    }
}