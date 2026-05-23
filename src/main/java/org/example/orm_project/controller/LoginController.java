package org.example.orm_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private RadioButton rbAdmin;
    @FXML private RadioButton rbTherapist;
    @FXML private Label lblError;
    @FXML private Button btnLogin;

    @FXML
    public void handleLogin(ActionEvent event) {

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        String role = rbAdmin.isSelected() ? "ADMIN" : "THERAPIST";

        if (username.isEmpty() || password.isEmpty()) {
            lblError.setText("Username and Password required!");
            return;
        }


        if (username.equals("admin") && password.equals("123")) {

            loadDashboard();

        } else {
            lblError.setText("Invalid login!");
        }
    }

    private void loadDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/orm_project/fxml/dashboard.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}