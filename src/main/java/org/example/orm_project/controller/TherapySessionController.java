//package org.example.orm_project.controller;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.transformation.FilteredList;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.HBox;
//
//import java.net.URL;
//import java.time.LocalDate;
//import java.util.ResourceBundle;
//
//public class TherapySessionController implements Initializable {
//
//    @FXML
//    private Label lblMessage;
//
//    @FXML
//    private TextField txtId;
//
//    @FXML
//    private ComboBox<String> cmbPatient;
//
//    @FXML
//    private ComboBox<String> cmbTherapist;
//
//    @FXML
//    private ComboBox<String> cmbProgram;
//
//    @FXML
//    private DatePicker dpSessionDate;
//
//    @FXML
//    private TextField txtStartTime;
//
//    @FXML
//    private TextField txtEndTime;
//
//    @FXML
//    private ComboBox<String> cmbStatus;
//
//    @FXML
//    private Button btnSave;
//
//    @FXML
//    private TextField txtSearch;
//
//    @FXML
//    private TableView<SessionTM> tblSession;
//
//    @FXML
//    private TableColumn<SessionTM, String> colId;
//
//    @FXML
//    private TableColumn<SessionTM, String> colPatient;
//
//    @FXML
//    private TableColumn<SessionTM, String> colTherapist;
//
//    @FXML
//    private TableColumn<SessionTM, LocalDate> colDate;
//
//    @FXML
//    private TableColumn<SessionTM, String> colStart;
//
//    @FXML
//    private TableColumn<SessionTM, String> colEnd;
//
//    @FXML
//    private TableColumn<SessionTM, String> colStatus;
//
//    @FXML
//    private TableColumn<SessionTM, Void> colAction;
//
//    private final ObservableList<SessionTM> masterList =
//            FXCollections.observableArrayList();
//
//    private boolean isEditMode = false;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//        loadComboBoxes();
//        setupTable();
//        setupSearch();
//        generateNextId();
//    }
//
//    private void loadComboBoxes() {
//
//        cmbPatient.getItems().addAll(
//                "P001 - Kasun",
//                "P002 - Nimal",
//                "P003 - Sahan"
//        );
//
//        cmbTherapist.getItems().addAll(
//                "T001 - Dr. Silva",
//                "T002 - Dr. Perera"
//        );
//
//        cmbProgram.getItems().addAll(
//                "CBT Program",
//                "Mindfulness Program",
//                "Anxiety Therapy"
//        );
//
//        cmbStatus.getItems().addAll(
//                "Scheduled",
//                "Completed",
//                "Cancelled"
//        );
//    }
//
//    private void generateNextId() {
//
//        int next = masterList.size() + 1;
//        txtId.setText(String.format("S%03d", next));
//    }
//
//    private void setupTable() {
//
//        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
//        colPatient.setCellValueFactory(new PropertyValueFactory<>("patient"));
//        colTherapist.setCellValueFactory(new PropertyValueFactory<>("therapist"));
//        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
//        colStart.setCellValueFactory(new PropertyValueFactory<>("startTime"));
//        colEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
//        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
//
//        colAction.setCellFactory(col -> new TableCell<>() {
//
//            private final Button btnEdit = new Button("Edit");
//            private final Button btnDelete = new Button("Delete");
//
//            private final HBox box = new HBox(5, btnEdit, btnDelete);
//
//            {
//
//                btnEdit.setStyle(
//                        "-fx-background-color:#2B3990;" +
//                                "-fx-text-fill:white;"
//                );
//
//                btnDelete.setStyle(
//                        "-fx-background-color:#e74c3c;" +
//                                "-fx-text-fill:white;"
//                );
//
//                btnEdit.setOnAction(e ->
//                        populateForm(getTableView().getItems().get(getIndex()))
//                );
//
//                btnDelete.setOnAction(e ->
//                        deleteSession(getTableView().getItems().get(getIndex()))
//                );
//            }
//
//            @Override
//            protected void updateItem(Void item, boolean empty) {
//
//                super.updateItem(item, empty);
//
//                setGraphic(empty ? null : box);
//            }
//        });
//
//        tblSession.setItems(masterList);
//    }
//
//    private void setupSearch() {
//
//        FilteredList<SessionTM> filtered =
//                new FilteredList<>(masterList, p -> true);
//
//        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> {
//
//            filtered.setPredicate(session -> {
//
//                if (newVal == null || newVal.isEmpty()) {
//                    return true;
//                }
//
//                String key = newVal.toLowerCase();
//
//                return session.getId().toLowerCase().contains(key)
//                        || session.getPatient().toLowerCase().contains(key)
//                        || session.getTherapist().toLowerCase().contains(key)
//                        || session.getStatus().toLowerCase().contains(key);
//            });
//        });
//
//        tblSession.setItems(filtered);
//    }
//
//    @FXML
//    private void handleSave() {
//
//        if (!validateInputs()) {
//            return;
//        }
//
//        SessionTM session = new SessionTM(
//
//                txtId.getText(),
//                cmbPatient.getValue(),
//                cmbTherapist.getValue(),
//                dpSessionDate.getValue(),
//                txtStartTime.getText(),
//                txtEndTime.getText(),
//                cmbStatus.getValue()
//        );
//
//        if (isEditMode) {
//
//            int selectedIndex =
//                    tblSession.getSelectionModel().getSelectedIndex();
//
//            masterList.set(selectedIndex, session);
//
//            showSuccess("Session updated!");
//
//        } else {
//
//            masterList.add(session);
//
//            showSuccess("Session saved!");
//        }
//
//        handleClear();
//    }
//
//    @FXML
//    private void handleDelete() {
//
//        SessionTM selected =
//                tblSession.getSelectionModel().getSelectedItem();
//
//        if (selected == null) {
//
//            showError("Select a session first!");
//            return;
//        }
//
//        deleteSession(selected);
//    }
//
//    private void deleteSession(SessionTM session) {
//
//        masterList.remove(session);
//
//        showSuccess("Session deleted!");
//    }
//
//    private void populateForm(SessionTM session) {
//
//        isEditMode = true;
//
//        txtId.setText(session.getId());
//        cmbPatient.setValue(session.getPatient());
//        cmbTherapist.setValue(session.getTherapist());
//        dpSessionDate.setValue(session.getDate());
//        txtStartTime.setText(session.getStartTime());
//        txtEndTime.setText(session.getEndTime());
//        cmbStatus.setValue(session.getStatus());
//
//        btnSave.setText("Update");
//    }
//
//    @FXML
//    private void handleClear() {
//
//        isEditMode = false;
//
//        cmbPatient.setValue(null);
//        cmbTherapist.setValue(null);
//        cmbProgram.setValue(null);
//        dpSessionDate.setValue(null);
//
//        txtStartTime.clear();
//        txtEndTime.clear();
//
//        cmbStatus.setValue(null);
//
//        btnSave.setText("Save");
//
//        generateNextId();
//
//        lblMessage.setText("");
//    }
//
//    private boolean validateInputs() {
//
//        if (cmbPatient.getValue() == null) {
//            showError("Select patient");
//            return false;
//        }
//
//        if (cmbTherapist.getValue() == null) {
//            showError("Select therapist");
//            return false;
//        }
//
//        if (dpSessionDate.getValue() == null) {
//            showError("Select date");
//            return false;
//        }
//
//        if (txtStartTime.getText().isEmpty()) {
//            showError("Enter start time");
//            return false;
//        }
//
//        if (txtEndTime.getText().isEmpty()) {
//            showError("Enter end time");
//            return false;
//        }
//
//        if (cmbStatus.getValue() == null) {
//            showError("Select status");
//            return false;
//        }
//
//        return true;
//    }
//
//    private void showError(String msg) {
//
//        lblMessage.setText("⚠ " + msg);
//
//        lblMessage.setStyle(
//                "-fx-text-fill:red;" +
//                        "-fx-font-weight:bold;"
//        );
//    }
//
//    private void showSuccess(String msg) {
//
//        lblMessage.setText("✔ " + msg);
//
//        lblMessage.setStyle(
//                "-fx-text-fill:green;" +
//                        "-fx-font-weight:bold;"
//        );
//    }
//}