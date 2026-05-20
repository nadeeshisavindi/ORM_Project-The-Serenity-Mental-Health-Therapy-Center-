package org.example.orm_project.dao.custom;

import org.example.orm_project.dao.CrudDAO;
import org.example.orm_project.entity.User;

public interface UserDAO extends CrudDAO<User> {


    User searchByUsername(String username) throws Exception;


    boolean isUsernameExists(String username) throws Exception;

    User getUserByUsername(String username) throws Exception;
}