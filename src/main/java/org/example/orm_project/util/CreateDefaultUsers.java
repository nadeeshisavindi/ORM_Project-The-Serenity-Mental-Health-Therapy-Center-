package org.example.orm_project.util;

import org.example.orm_project.bo.BOFactory;
import org.example.orm_project.bo.BOTypes;
import org.example.orm_project.bo.custom.UserBO;
import org.example.orm_project.entity.User;
import org.mindrot.jbcrypt.BCrypt;

public class CreateDefaultUsers {

    public static void main(String[] args) throws Exception {

        UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOTypes.USER);

        // ── Admin ──────────────────────────────────────────
        User admin = new User();
        admin.setId("U001");
        admin.setUsername("admin");
        admin.setPassword(BCrypt.hashpw("admin123", BCrypt.gensalt()));
        admin.setRole("ADMIN");
        userBO.saveUser(admin);
        System.out.println("✔ Admin created  →  username: admin  |  password: admin123");

        // ── Therapist ──────────────────────────────────────
        User therapist = new User();
        therapist.setId("U002");
        therapist.setUsername("therapist");
        therapist.setPassword(BCrypt.hashpw("therapist123", BCrypt.gensalt()));
        therapist.setRole("THERAPIST");
        userBO.saveUser(therapist);
        System.out.println("✔ Therapist created  →  username: therapist  |  password: therapist123");

        System.out.println("\nDone! Now login with these credentials.");
    }
}