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
import org.example.orm_project.dto.tm.TherapistTM;
import org.example.orm_project.entity.Therapist;
import org.example.orm_project.util.JasperReportUtil;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TherapistController implements Initializable {

    @FXML private TextField txtId;
    @FXML private TextField txtName;
    @FXML private ComboBox<String> cmbSpecialization;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cmbAvailability;
    @FXML private Label lblMessage;
    @FXML private Button btnSave;
    @FXML private TextField txtSearch;

    @FXML private TableView<TherapistTM> tblTherapist;
    @FXML private TableColumn<TherapistTM, String> colId;
    @FXML private TableColumn<TherapistTM, String> colName;
    @FXML private TableColumn<TherapistTM, String> colSpecialization;
    @FXML private TableColumn<TherapistTM, String> colPhone;
    @FXML private TableColumn<TherapistTM, String> colEmail;
    @FXML private TableColumn<TherapistTM, Void> colAction;

    private final TherapistBO therapistBO =
            (TherapistBO) BOFactory.getInstance().getBO(BOTypes.THERAPIST);

    private final ObservableList<TherapistTM> masterList = FXCollections.observableArrayList();
    private boolean isEditMode = false;

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_REGEX = "^(\\+94|0)[0-9]{9}$";
    private static final String NAME_REGEX  = "^[A-Za-z .]{3,100}$";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupCombo();
        setupTable();
        setupSearch();
        loadData();
        generateId();
    }

    private void generateId() {
        try {
            txtId.setText(therapistBO.generateNextTherapistId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupCombo() {
        cmbSpecialization.getItems().addAll(
                "CBT",
                "Mindfulness",
                "DBT",
                "Group Therapy",
                "Family Counseling"
        );

        cmbAvailability.getItems().addAll("Available", "Busy", "On Leave");
    }

    private void setupTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colSpecialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));

        colAction.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button delBtn = new Button("Delete");
            private final HBox box = new HBox(6, editBtn, delBtn);

            {
                editBtn.setOnAction(e -> populateForm(getTableView().getItems().get(getIndex())));
                delBtn.setOnAction(e -> deleteRow(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });
    }

    private void setupSearch() {
        FilteredList<TherapistTM> filtered = new FilteredList<>(masterList, p -> true);

        txtSearch.textProperty().addListener((obs, o, v) -> {
            filtered.setPredicate(t -> {
                if (v == null || v.isEmpty()) return true;
                String key = v.toLowerCase();

                return t.getName().toLowerCase().contains(key)
                        || t.getEmail().toLowerCase().contains(key)
                        || t.getPhone().toLowerCase().contains(key)
                        || t.getSpecialization().toLowerCase().contains(key);
            });
        });

        tblTherapist.setItems(filtered);
    }

    private void loadData() {
        masterList.clear();
        try {
            List<Therapist> list = therapistBO.getAllTherapists();

            for (Therapist t : list) {
                // Constructor එකේ පිළිවෙළටම data පාස් කර ඇත (id, name, email, phone, specialization)
                masterList.add(new TherapistTM(
                        t.getId(),
                        t.getName(),
                        t.getEmail(),
                        t.getPhone(),
                        t.getSpecialization()
                ));
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    // save
    @FXML
    private void handleSave() {
        if (!validate()) return;

        try {
            Therapist t = new Therapist();
            t.setId(txtId.getText());
            t.setName(txtName.getText());
            t.setEmail(txtEmail.getText());
            t.setPhone(txtPhone.getText());
            t.setSpecialization(cmbSpecialization.getValue());

            boolean ok = isEditMode ?
                    therapistBO.updateTherapist(t) :
                    therapistBO.saveTherapist(t);

            if (ok) {
                showSuccess(isEditMode ? "Updated!" : "Saved!");
                loadData();
                clearForm();
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        TherapistTM selected = tblTherapist.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showError("Select a row first");
            return;
        }
        deleteRow(selected);
    }

    private void populateForm(TherapistTM t) {
        isEditMode = true;

        txtId.setText(t.getId());
        txtName.setText(t.getName());
        txtEmail.setText(t.getEmail());
        txtPhone.setText(t.getPhone());
        cmbSpecialization.setValue(t.getSpecialization());

        btnSave.setText("Update");
    }

    private void deleteRow(TherapistTM t) {
        try {
            therapistBO.deleteTherapist(t.getId());
            masterList.remove(t);
            showSuccess("Deleted!");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void handleReportView(javafx.event.ActionEvent event) {
        JasperReportUtil.showTherapistReport();
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    private void clearForm() {
        isEditMode = false;

        txtName.clear();
        txtPhone.clear();
        txtEmail.clear();
        cmbSpecialization.setValue(null);
        cmbAvailability.setValue(null);

        btnSave.setText("Save");
        generateId();
    }

    private boolean validate() {
        if (!txtName.getText().matches(NAME_REGEX)) return error("Invalid name");
        if (cmbSpecialization.getValue() == null) return error("Select specialization");
        if (!txtPhone.getText().matches(PHONE_REGEX)) return error("Invalid phone");
        if (!txtEmail.getText().matches(EMAIL_REGEX)) return error("Invalid email");

        return true;
    }

    private boolean error(String msg) {
        showError(msg);
        return false;
    }

    private void showError(String msg) {
        lblMessage.setText("⚠ " + msg);
        lblMessage.setStyle("-fx-text-fill:red;");
    }

    private void showSuccess(String msg) {
        lblMessage.setText("✔ " + msg);
        lblMessage.setStyle("-fx-text-fill:green;");
    }
}