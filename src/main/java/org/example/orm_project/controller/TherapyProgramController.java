package org.example.orm_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import org.example.orm_project.bo.BOFactory;
import org.example.orm_project.bo.BOTypes;
import org.example.orm_project.bo.custom.TherapistBO;
import org.example.orm_project.bo.custom.TherapyProgramBO;
import org.example.orm_project.dto.tm.TherapyProgramTM;
import org.example.orm_project.entity.Therapist;
import org.example.orm_project.entity.TherapyProgram;

import java.net.URL;
import java.util.ResourceBundle;

public class TherapyProgramController implements Initializable {

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtDuration;
    @FXML private TextField txtFee;
    @FXML private TextField txtDescription;
    @FXML private ComboBox<String> cmbTherapist;
    @FXML private Label lblMessage;
    @FXML private Button btnSave;
    @FXML private TextField txtSearch;

    @FXML private TableView<TherapyProgramTM> tblProgram;
    @FXML private TableColumn<TherapyProgramTM, String> colId;
    @FXML private TableColumn<TherapyProgramTM, String> colName;
    @FXML private TableColumn<TherapyProgramTM, Integer> colDuration;
    @FXML private TableColumn<TherapyProgramTM, Double> colFee;
    @FXML private TableColumn<TherapyProgramTM, String> colTherapist;
    @FXML private TableColumn<TherapyProgramTM, Void> colAction;

    private final TherapyProgramBO programBO =
            (TherapyProgramBO) BOFactory.getInstance().getBO(BOTypes.THERAPY_PROGRAM);
    private final TherapistBO therapistBO =
            (TherapistBO) BOFactory.getInstance().getBO(BOTypes.THERAPIST);

    private final ObservableList<TherapyProgramTM> masterList =
            FXCollections.observableArrayList();
    private boolean isEditMode = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        setupSearch();
        loadTherapists();
        loadTableData();
        generateNextId();
    }

    private void generateNextId() {
        try {
            txtId.setText(programBO.generateNextTherapyProgramId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTherapists() {
        try {
            cmbTherapist.getItems().clear();
            for (Therapist t : therapistBO.getAllTherapists())
                cmbTherapist.getItems().add(t.getId() + " - " + t.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("durationWeeks"));
        colFee.setCellValueFactory(new PropertyValueFactory<>("fee"));
        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapistId"));

        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button btnEdit   = new Button("Edit");
            private final Button btnDelete = new Button("Delete");
            private final HBox box = new HBox(6, btnEdit, btnDelete);
            {
                btnEdit.setStyle("-fx-background-color:#2B3990;-fx-text-fill:white;"
                        + "-fx-background-radius:5;-fx-padding:4 10;-fx-cursor:hand;-fx-font-size:11px;");
                btnDelete.setStyle("-fx-background-color:#e74c3c;-fx-text-fill:white;"
                        + "-fx-background-radius:5;-fx-padding:4 10;-fx-cursor:hand;-fx-font-size:11px;");
                btnEdit.setOnAction(e ->
                        populateForm(getTableView().getItems().get(getIndex())));
                btnDelete.setOnAction(e ->
                        handleDelete(getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });
    }

    private void setupSearch() {
        FilteredList<TherapyProgramTM> filtered = new FilteredList<>(masterList, p -> true);
        txtSearch.textProperty().addListener((obs, o, val) ->
                filtered.setPredicate(row -> {
                    if (val == null || val.isEmpty()) return true;
                    String lower = val.toLowerCase();
                    return row.getId().toLowerCase().contains(lower)
                            || row.getName().toLowerCase().contains(lower)
                            || row.getTherapistId().toLowerCase().contains(lower);
                }));
        tblProgram.setItems(filtered);
    }

    private void loadTableData() {
        masterList.clear();
        try {
            for (TherapyProgram p : programBO.getAllTherapyPrograms()) {
                masterList.add(new TherapyProgramTM(
                        p.getId(),
                        p.getName(),
                        p.getDescription(),
                        p.getDurationWeeks(),
                        p.getFee(),
                        p.getTherapist() != null ? p.getTherapist().getId() + " - " + p.getTherapist().getName() : ""
                ));
            }
        } catch (Exception e) {
            showError("Failed to load: " + e.getMessage());
        }
    }

    @FXML
    private void handleSave() {
        hideMessage();
        if (!validateInputs()) return;
        try {
            String therapistId = cmbTherapist.getValue() != null
                    ? cmbTherapist.getValue().split(" - ")[0] : null;
            Therapist therapist = therapistId != null
                    ? therapistBO.searchTherapist(therapistId) : null;

            TherapyProgram program = new TherapyProgram(
                    txtId.getText().trim(),
                    txtName.getText().trim(),
                    txtDescription.getText().trim(),
                    Integer.parseInt(txtDuration.getText().trim()),
                    Double.parseDouble(txtFee.getText().trim()),
                    therapist,
                    null
            );

            boolean result = isEditMode
                    ? programBO.updateTherapyProgram(program)
                    : programBO.saveTherapyProgram(program);

            if (result) {
                showSuccess(isEditMode ? "Program updated!" : "Program saved!");
                loadTableData();
                handleClear();
            }
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    private void populateForm(TherapyProgramTM row) {
        isEditMode = true;
        txtId.setText(row.getId());
        txtName.setText(row.getName());
        txtDescription.setText(row.getDescription());
        txtDuration.setText(String.valueOf(row.getDurationWeeks()));
        txtFee.setText(String.valueOf(row.getFee()));
        cmbTherapist.getItems().stream()
                .filter(s -> s.startsWith(row.getTherapistId().split(" - ")[0]))
                .findFirst().ifPresent(cmbTherapist::setValue);
        btnSave.setText("Update");
    }

    private void handleDelete(TherapyProgramTM row) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete program \"" + row.getName() + "\"?",
                ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Confirm Delete");
        alert.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                try {
                    if (programBO.deleteTherapyProgram(row.getId())) {
                        masterList.remove(row);
                        showSuccess("Program deleted.");
                        generateNextId();
                    }
                } catch (Exception e) {
                    showError("Delete failed: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleDelete() {}

    @FXML
    private void handleClear() {
        isEditMode = false;
        txtName.clear();
        txtDuration.clear();
        txtFee.clear();
        txtDescription.clear();
        cmbTherapist.setValue(null);
        btnSave.setText("Save");
        generateNextId();
        hideMessage();
    }

    @FXML
    private void handleReportView() {
        showSuccess("Report feature coming soon.");
    }

    private boolean validateInputs() {
        if (txtName.getText().isBlank()) {
            showError("Program name is required."); return false;
        }
        if (!txtDuration.getText().matches("^[0-9]+$")) {
            showError("Duration must be a whole number."); return false;
        }
        if (!txtFee.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$")) {
            showError("Fee must be a valid number."); return false;
        }
        return true;
    }

    private void showError(String msg) {
        lblMessage.setText("⚠ " + msg);
        lblMessage.setStyle("-fx-text-fill:#c0392b;-fx-font-size:13px;-fx-font-weight:bold;");
        lblMessage.setVisible(true); lblMessage.setManaged(true);
    }

    private void showSuccess(String msg) {
        lblMessage.setText("✔ " + msg);
        lblMessage.setStyle("-fx-text-fill:#27ae60;-fx-font-size:13px;-fx-font-weight:bold;");
        lblMessage.setVisible(true); lblMessage.setManaged(true);
    }

    private void hideMessage() {
        lblMessage.setVisible(false); lblMessage.setManaged(false);
    }
}