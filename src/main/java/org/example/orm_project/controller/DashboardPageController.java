package org.example.orm_project.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class DashboardPageController {

    @FXML
    private Label dateLabel;

    @FXML
    private Label totalPatients;

    @FXML
    private Label totalTherapists;

    @FXML
    private Label sessionsToday;

    @FXML
    private TableView<?> recentSessionsTable;

    @FXML
    public void initialize() {


        dateLabel.setText("Wednesday, May 20, 2026");

        totalPatients.setText("25");
        totalTherapists.setText("8");
        sessionsToday.setText("5");
    }
}