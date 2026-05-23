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
import org.example.orm_project.bo.custom.PatientBO;
import org.example.orm_project.bo.custom.TherapistBO;
import org.example.orm_project.bo.custom.TherapyProgramBO;
import org.example.orm_project.bo.custom.TherapySessionBO;
import org.example.orm_project.dto.tm.SessionTM;
import org.example.orm_project.entity.Patient;
import org.example.orm_project.entity.Therapist;
import org.example.orm_project.entity.TherapyProgram;
import org.example.orm_project.entity.TherapySession;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class TherapySessionController implements Initializable {

    @FXML private Label lblMessage;
    @FXML private TextField txtId;
    @FXML private ComboBox<String> cmbPatient;
    @FXML private ComboBox<String> cmbTherapist;
    @FXML private ComboBox<String> cmbProgram;
    @FXML private DatePicker dpSessionDate;
    @FXML private TextField txtStartTime;
    @FXML private TextField txtEndTime;
    @FXML private TextField txtNotes;
    @FXML private ComboBox<String> cmbStatus;
    @FXML private Button btnSave;
    @FXML private TextField txtSearch;

    @FXML private TableView<SessionTM> tblSession;
    @FXML private TableColumn<SessionTM, String> colId;
    @FXML private TableColumn<SessionTM, String> colPatient;
    @FXML private TableColumn<SessionTM, String> colTherapist;
    @FXML private TableColumn<SessionTM, LocalDate> colDate;
    @FXML private TableColumn<SessionTM, String> colStart;
    @FXML private TableColumn<SessionTM, String> colEnd;
    @FXML private TableColumn<SessionTM, String> colStatus;
    @FXML private TableColumn<SessionTM, Void> colAction;

    private final TherapySessionBO sessionBO =
            (TherapySessionBO) BOFactory.getInstance().getBO(BOTypes.THERAPY_SESSION);
    private final PatientBO patientBO =
            (PatientBO) BOFactory.getInstance().getBO(BOTypes.PATIENT);
    private final TherapistBO therapistBO =
            (TherapistBO) BOFactory.getInstance().getBO(BOTypes.THERAPIST);
    private final TherapyProgramBO programBO =
            (TherapyProgramBO) BOFactory.getInstance().getBO(BOTypes.THERAPY_PROGRAM);

    private final ObservableList<SessionTM> masterList = FXCollections.observableArrayList();
    private boolean isEditMode = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        setupSearch();
        loadComboBoxes();
        loadTableData();
        generateNextId();
        dpSessionDate.setValue(LocalDate.now());
    }

    private void generateNextId() {
        try {
            txtId.setText(sessionBO.generateNextTherapySessionId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadComboBoxes() {
        try {
            cmbPatient.getItems().clear();
            for (Patient p : patientBO.getAllPatients())
                cmbPatient.getItems().add(p.getId() + " - " + p.getName());

            cmbTherapist.getItems().clear();
            for (Therapist t : therapistBO.getAllTherapists())
                cmbTherapist.getItems().add(t.getId() + " - " + t.getName());

            cmbProgram.getItems().clear();
            List<TherapyProgram> programs = programBO.getAllTherapyPrograms();
            for (TherapyProgram p : programs)
                cmbProgram.getItems().add(p.getId() + " - " + p.getName());

            cmbStatus.setItems(FXCollections.observableArrayList(
                    "SCHEDULED", "COMPLETED", "CANCELLED"));

        } catch (Exception e) {
            showError("Combo load failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patient"));
        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapist"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

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
                        handleDeleteRow(getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        tblSession.setItems(masterList);
    }

    private void setupSearch() {
        FilteredList<SessionTM> filtered = new FilteredList<>(masterList, p -> true);
        txtSearch.textProperty().addListener((obs, o, val) ->
                filtered.setPredicate(row -> {
                    if (val == null || val.isEmpty()) return true;
                    String key = val.toLowerCase();
                    return row.getId().toLowerCase().contains(key)
                            || row.getPatient().toLowerCase().contains(key)
                            || row.getTherapist().toLowerCase().contains(key)
                            || row.getStatus().toLowerCase().contains(key);
                }));
        tblSession.setItems(filtered);
    }

    private void loadTableData() {
        masterList.clear();
        try {
            for (TherapySession s : sessionBO.getAllTherapySessions()) {
                masterList.add(new SessionTM(
                        s.getId(),
                        s.getPatient() != null
                                ? s.getPatient().getId() + " - " + s.getPatient().getName() : "",
                        s.getTherapist() != null
                                ? s.getTherapist().getId() + " - " + s.getTherapist().getName() : "",
                        s.getSessionDate(),
                        s.getStartTime() != null ? s.getStartTime().toString() : "",
                        s.getEndTime()   != null ? s.getEndTime().toString()   : "",
                        s.getStatus()
                ));
            }
        } catch (Exception e) {
            showError("Load failed: " + e.getMessage());
        }
    }

    @FXML
    private void handleSave() {
        hideMessage();
        if (!validateInputs()) return;
        try {
            String patientId   = cmbPatient.getValue().split(" - ")[0];
            String therapistId = cmbTherapist.getValue().split(" - ")[0];

            Patient   patient   = patientBO.searchPatient(patientId);
            Therapist therapist = therapistBO.searchTherapist(therapistId);

            TherapySession session = new TherapySession(
                    txtId.getText().trim(),
                    dpSessionDate.getValue(),
                    LocalTime.parse(txtStartTime.getText().trim()),
                    LocalTime.parse(txtEndTime.getText().trim()),
                    txtNotes.getText().trim(),
                    cmbStatus.getValue(),
                    patient,
                    therapist
            );

            boolean result = isEditMode
                    ? sessionBO.updateTherapySession(session)
                    : sessionBO.saveTherapySession(session);

            if (result) {
                showSuccess(isEditMode ? "Session updated!" : "Session saved!");
                loadTableData();
                handleClear();
            }
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    private void populateForm(SessionTM row) {
        isEditMode = true;
        txtId.setText(row.getId());
        dpSessionDate.setValue(row.getDate());
        txtStartTime.setText(row.getStartTime());
        txtEndTime.setText(row.getEndTime());
        cmbStatus.setValue(row.getStatus());
        cmbPatient.getItems().stream()
                .filter(s -> s.startsWith(row.getPatient().split(" - ")[0]))
                .findFirst().ifPresent(cmbPatient::setValue);
        cmbTherapist.getItems().stream()
                .filter(s -> s.startsWith(row.getTherapist().split(" - ")[0]))
                .findFirst().ifPresent(cmbTherapist::setValue);
        btnSave.setText("Update");
    }

    private void handleDeleteRow(SessionTM row) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete session \"" + row.getId() + "\"?",
                ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Confirm Delete");
        alert.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                try {
                    if (sessionBO.deleteTherapySession(row.getId())) {
                        masterList.remove(row);
                        showSuccess("Session deleted.");
                        generateNextId();
                    }
                } catch (Exception e) {
                    showError("Delete failed: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleDelete() {
        SessionTM selected = tblSession.getSelectionModel().getSelectedItem();
        if (selected == null) { showError("Select a session first!"); return; }
        handleDeleteRow(selected);
    }

    @FXML
    private void handleClear() {
        isEditMode = false;
        cmbPatient.setValue(null);
        cmbTherapist.setValue(null);
        cmbProgram.setValue(null);
        dpSessionDate.setValue(LocalDate.now());
        txtStartTime.clear();
        txtEndTime.clear();
        txtNotes.clear();
        cmbStatus.setValue(null);
        btnSave.setText("Save");
        generateNextId();
        hideMessage();
    }

    private boolean validateInputs() {
        if (cmbPatient.getValue() == null)   { showError("Select a patient.");          return false; }
        if (cmbTherapist.getValue() == null) { showError("Select a therapist.");        return false; }
        if (dpSessionDate.getValue() == null){ showError("Select a date.");             return false; }
        if (txtStartTime.getText().isBlank()){ showError("Enter start time (HH:MM)."); return false; }
        if (txtEndTime.getText().isBlank())  { showError("Enter end time (HH:MM).");   return false; }
        if (cmbStatus.getValue() == null)    { showError("Select a status.");           return false; }
        return true;
    }

    private void showError(String msg) {
        lblMessage.setText("⚠ " + msg);
        lblMessage.setStyle("-fx-text-fill:#c0392b;-fx-font-size:13px;-fx-font-weight:bold;");
        lblMessage.setVisible(true);
        lblMessage.setManaged(true);
    }

    private void showSuccess(String msg) {
        lblMessage.setText("✔ " + msg);
        lblMessage.setStyle("-fx-text-fill:#27ae60;-fx-font-size:13px;-fx-font-weight:bold;");
        lblMessage.setVisible(true);
        lblMessage.setManaged(true);
    }

    private void hideMessage() {
        lblMessage.setVisible(false);
        lblMessage.setManaged(false);
    }
}