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
        loadPage("dashbord2.fxml");
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
    void navigateToRegistration(ActionEvent event) {
        loadPage("registration.fxml");
    }
    @FXML
    void navigateToPrograms(ActionEvent event) {
        loadPage("therapy_program.fxml");
    }

    @FXML
    void navigateToSessions(ActionEvent event) {
        loadPage("therapy_session.fxml");
    }

   

    @FXML
    void navigateToPayment(ActionEvent event) {
        loadPage("payment.fxml");
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/orm_project/fxml/login.fxml"));
            AnchorPane root = loader.load();

            contentPane.getScene().setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
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




//
//
//
//
//
//
//package org.example.orm_project.controller;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.VBox;
//
//import java.io.IOException;
//
//public class DashboardController {
//
//    @FXML
//    private BorderPane contentPane; // FXML එකේ root එක BorderPane නිසා AnchorPane වෙනුවට BorderPane කළා
//
//    @FXML
//    private VBox bodyPane; // FXML එකේ මැද තියෙන VBox එකේ fx:id එක
//
//    @FXML
//    void navigateToDashboard(ActionEvent event) {
//        loadPage("dashbord2.fxml");
//    }
//
//    @FXML
//    void navigateToPatient(ActionEvent event) {
//        loadPage("patient.fxml");
//    }
//
//    @FXML
//    void navigateToTherapists(ActionEvent event) {
//        loadPage("therapist.fxml"); // ඔයාගේ FXML ෆයිල් එකේ නම මෙතනට දෙන්න
//    }
//
//    @FXML
//    void navigateToSessions(ActionEvent event) {
//        loadPage("therapy_session_scheduling.fxml");
//    }
//
//    @FXML
//    void navigateToPrograms(ActionEvent event) {
//        loadPage("therapy_program_management.fxml");
//    }
//
//    @FXML
//    void navigateToPayment(ActionEvent event) {
//        loadPage("payment.fxml");
//    }
//
//    @FXML
//    void logout(ActionEvent event) {
//        try {
//            // Login එකට යනකොට path එක නිවැරදිව තැබීම
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/orm_project/fxml/login.fxml"));
//            Parent root = loader.load();
//
//            contentPane.getScene().setRoot(root);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * සියලුම පේජ් මැද කොටසට (bodyPane) ලෝඩ් කරන සහ
//     * ඒවා auto full screen stretch කරන පොදු මෙතඩ් එක.
//     */
//    private void loadPage(String fileName) {
//        try {
//            // ඔයාගේ FXML ෆයිල් තියෙන නිවැරදිම folder path එක
//            String fullPath = "/org/example/orm_project/fxml/" + fileName;
//            var url = getClass().getResource(fullPath);
//
//            System.out.println("Loading: " + fullPath);
//
//            if (url == null) {
//                System.err.println("❌ FXML file not found: " + fullPath);
//                return;
//            }
//
//            // පේජ් එක load කිරීම
//            Parent pane = FXMLLoader.load(url);
//
//            // කලින් තිබුණු පේජ් එක අයින් කරලා අලුත් එක දානවා
//            bodyPane.getChildren().clear();
//            bodyPane.getChildren().add(pane);
//
//            // 💡 වැදගත්ම කොටස: ලෝඩ් කරන පේජ් එක ඉතිරි වෙන මුළු ඉඩ පුරාම
//            // auto stretch (Full Size) වෙන්න කියලා VBox එකට කියනවා.
//            VBox.setVgrow(pane, Priority.ALWAYS);
//
//        } catch (IOException e) {
//            System.err.println("❌ Error loading page: " + fileName);
//            e.printStackTrace();
//        }
//    }
//}