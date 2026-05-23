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
import org.example.orm_project.dto.tm.PatientTM;
import org.example.orm_project.entity.Patient;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private TextField txtNic;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;
    @FXML private TextArea txtMedicalHistory;
    @FXML private Label lblMessage;
    @FXML private Button btnSave;
    @FXML private TextField txtSearch;

    @FXML private TableView<PatientTM> tblPatient;
    @FXML private TableColumn<PatientTM, String> colId;
    @FXML private TableColumn<PatientTM, String> colName;
    @FXML private TableColumn<PatientTM, String> colNic;
    @FXML private TableColumn<PatientTM, String> colEmail;
    @FXML private TableColumn<PatientTM, String> colPhone;
    @FXML private TableColumn<PatientTM, Void> colAction;

    private final PatientBO patientBO =
            (PatientBO) BOFactory.getInstance().getBO(BOTypes.PATIENT);

    private final ObservableList<PatientTM> masterList = FXCollections.observableArrayList();
    private boolean isEditMode = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        setupSearch();
        loadTableData();
        generateNextId();
    }

    private void generateNextId() {
        try {
            txtId.setText(patientBO.generateNextPatientId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

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
        FilteredList<PatientTM> filtered = new FilteredList<>(masterList, p -> true);
        txtSearch.textProperty().addListener((obs, o, val) ->
                filtered.setPredicate(row -> {
                    if (val == null || val.isEmpty()) return true;
                    String lower = val.toLowerCase();
                    return row.getId().toLowerCase().contains(lower)
                            || row.getName().toLowerCase().contains(lower)
                            || row.getNic().toLowerCase().contains(lower)
                            || row.getEmail().toLowerCase().contains(lower);
                }));
        tblPatient.setItems(filtered);
    }

    private void loadTableData() {
        masterList.clear();
        try {
            for (Patient p : patientBO.getAllPatients()) {
                masterList.add(new PatientTM(
                        p.getId(), p.getName(), p.getNic(),
                        p.getEmail(), p.getPhone(), p.getMedicalHistory()
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
            Patient patient = new Patient(
                    txtId.getText().trim(),
                    txtName.getText().trim(),
                    txtNic.getText().trim(),
                    txtEmail.getText().trim(),
                    txtPhone.getText().trim(),
                    txtMedicalHistory.getText().trim(),
                    null, null
            );

            boolean result = isEditMode
                    ? patientBO.updatePatient(patient)
                    : patientBO.savePatient(patient);

            if (result) {
                showSuccess(isEditMode ? "Patient updated!" : "Patient saved!");
                loadTableData();
                handleClear();
            }
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    private void populateForm(PatientTM row) {
        isEditMode = true;
        txtId.setText(row.getId());
        txtName.setText(row.getName());
        txtNic.setText(row.getNic());
        txtEmail.setText(row.getEmail());
        txtPhone.setText(row.getPhone());
        txtMedicalHistory.setText(row.getMedicalHistory());
        btnSave.setText("Update");
    }

    private void handleDelete(PatientTM row) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete patient \"" + row.getName() + "\"?",
                ButtonType.YES, ButtonType.NO);
        alert.setHeaderText("Confirm Delete");
        alert.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                try {
                    if (patientBO.deletePatient(row.getId())) {
                        masterList.remove(row);
                        showSuccess("Patient deleted.");
                        generateNextId();
                    }
                } catch (Exception e) {
                    showError("Delete failed: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleClear() {
        isEditMode = false;
        txtName.clear();
        txtNic.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtMedicalHistory.clear();
        btnSave.setText("Save");
        generateNextId();
        hideMessage();
    }

    private boolean validateInputs() {
        if (txtName.getText().isBlank()) {
            showError("Name is required."); return false;
        }
        if (txtNic.getText().isBlank()) {
            showError("NIC is required."); return false;
        }
        if (!txtEmail.getText().isBlank()
                && !txtEmail.getText().matches("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$")) {
            showError("Invalid email format."); return false;
        }
        if (!txtPhone.getText().isBlank()
                && !txtPhone.getText().matches("^[0-9]{9,15}$")) {
            showError("Phone must be 9-15 digits."); return false;
        }
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