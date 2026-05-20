package org.example.orm_project.bo.custom;

import org.example.orm_project.bo.SuperBO;
import org.example.orm_project.entity.User;

import java.util.List;

public interface UserBO extends SuperBO {
    boolean saveUser(User user) throws Exception;
    boolean updateUser(User user) throws Exception;
    boolean deleteUser(String userId) throws Exception;
    User searchUser(String userId) throws Exception;
    List<User> getAllUsers() throws Exception;
    User getUserByUsername(String username) throws Exception;
}