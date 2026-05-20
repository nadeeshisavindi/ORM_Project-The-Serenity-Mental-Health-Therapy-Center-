package org.example.orm_project.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DashboardController {

    @FXML
    private AnchorPane contentPane;

    @FXML
    private VBox mainContent;

    @FXML
    void navigateToDashboard(ActionEvent event) {

        mainContent.getChildren().clear();
    }


    @FXML
    void navigateToPatient(ActionEvent event) {
        loadPage("patient.fxml");
    }

    @FXML
    void navigateToTherapists(ActionEvent event) {
        loadPage("therapists.fxml");
    }

    @FXML
    void navigateToSessions(ActionEvent event) {
        loadPage("therapy_session_scheduling.fxml");
    }

    @FXML
    void navigateToPrograms(ActionEvent event) {
        loadPage("therapy_program_management.fxml");
    }

    @FXML
    void navigateToPayment(ActionEvent event) {
        loadPage("payment.fxml");
    }

    @FXML
    void navigateToUser(ActionEvent event) {
        loadPage("user.fxml");
    }

    @FXML
    void logout(ActionEvent event) {
        System.out.println("Logout clicked");
    }

    private void loadPage(String fileName) {
        try {
            String fullPath = "/org/example/orm_project/fxml/" + fileName;
            var url = getClass().getResource(fullPath);

            System.out.println("Loading: " + fullPath);
            System.out.println("URL: " + url);

            if (url == null) {
                System.err.println("FXML file not found: " + fileName);
                return;
            }

            AnchorPane pane = FXMLLoader.load(url);
            mainContent.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
