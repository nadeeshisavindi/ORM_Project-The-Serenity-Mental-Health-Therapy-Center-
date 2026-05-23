package org.example.orm_project.util;

import org.example.orm_project.entity.User;

public class SessionManager {

    private static SessionManager instance;
    private User currentUser;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getRole() {
        return currentUser != null ? currentUser.getRole() : "";
    }

    public boolean isAdmin() {
        return "ADMIN".equals(getRole());
    }

    public boolean isTherapist() {
        return "THERAPIST".equals(getRole());
    }

    public void logout() {
        currentUser = null;
    }
}