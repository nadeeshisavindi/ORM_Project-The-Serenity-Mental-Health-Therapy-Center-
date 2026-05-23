package org.example.orm_project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.orm_project.bo.BOFactory;
import org.example.orm_project.bo.BOTypes;
import org.example.orm_project.bo.custom.PaymentBO;
import org.example.orm_project.bo.custom.RegistrationBO;
import org.example.orm_project.dto.tm.PaymentTM;
import org.example.orm_project.entity.Payment;
import org.example.orm_project.entity.Registration;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    @FXML private Label lblMessage;
    @FXML private TextField txtId;
    @FXML private ComboBox<String> cmbRegistration;
    @FXML private DatePicker dpPaymentDate;
    @FXML private TextField txtAmount;
    @FXML private ComboBox<String> cmbStatus;
    @FXML private Button btnSave;
    @FXML private TextField txtSearch;

    @FXML private TableView<PaymentTM> tblPayment;
    @FXML private TableColumn<PaymentTM, String> colId;
    @FXML private TableColumn<PaymentTM, String> colRegistration;
    @FXML private TableColumn<PaymentTM, LocalDate> colDate;
    @FXML private TableColumn<PaymentTM, Double> colAmount;
    @FXML private TableColumn<PaymentTM, String> colStatus;
    @FXML private TableColumn<PaymentTM, Button> colAction;

    private final PaymentBO paymentBO =
            (PaymentBO) BOFactory.getInstance().getBO(BOTypes.PAYMENT);
    private final RegistrationBO registrationBO =
            (RegistrationBO) BOFactory.getInstance().getBO(BOTypes.REGISTRATION);

    private final ObservableList<PaymentTM> paymentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupColumns();
        loadRegistrations();
        cmbStatus.setItems(FXCollections.observableArrayList("PAID", "PENDING"));
        dpPaymentDate.setValue(LocalDate.now());
        loadNextId();
        loadPayments();
        setupSearch();
        setupTableRowClick();
    }



    private void setupColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colRegistration.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("actionButton"));
        tblPayment.setItems(paymentList);
    }



    private void loadNextId() {
        try {
            txtId.setText(paymentBO.generateNextPaymentId());
        } catch (Exception e) {
            showMessage("ID load failed: " + e.getMessage(), true);
        }
    }

    private void loadRegistrations() {
        try {
            List<Registration> list = registrationBO.getAllRegistrations();
            ObservableList<String> ids = FXCollections.observableArrayList();
            list.forEach(r -> ids.add(r.getId()));
            cmbRegistration.setItems(ids);
        } catch (Exception e) {
            showMessage("Registration load failed: " + e.getMessage(), true);
        }
    }

    private void loadPayments() {
        try {
            paymentList.clear();
            List<Payment> list = paymentBO.getAllPayments();
            for (Payment p : list) {
                Button deleteBtn = new Button("Delete");
                deleteBtn.setStyle("-fx-background-color:#e74c3c;-fx-text-fill:white;"
                        + "-fx-background-radius:6;-fx-cursor:hand;");
                String payId = p.getId();
                deleteBtn.setOnAction(e -> handleDelete(payId));

                paymentList.add(new PaymentTM(
                        p.getId(),
                        p.getRegistration().getId(),
                        p.getPaymentDate(),
                        p.getAmount(),
                        p.getStatus(),
                        deleteBtn
                ));
            }
        } catch (Exception e) {
            showMessage("Load failed: " + e.getMessage(), true);
        }
    }



    private void setupSearch() {
        txtSearch.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isBlank()) {
                tblPayment.setItems(paymentList);
                return;
            }
            String lower = newVal.toLowerCase();
            ObservableList<PaymentTM> filtered = FXCollections.observableArrayList();
            for (PaymentTM tm : paymentList) {
                if (tm.getId().toLowerCase().contains(lower)
                        || tm.getRegistrationId().toLowerCase().contains(lower)
                        || tm.getStatus().toLowerCase().contains(lower)) {
                    filtered.add(tm);
                }
            }
            tblPayment.setItems(filtered);
        });
    }

    // ── Table row → form fill ─────────────────────────────────────────────────

    private void setupTableRowClick() {
        tblPayment.setOnMouseClicked(e -> {
            PaymentTM selected = tblPayment.getSelectionModel().getSelectedItem();
            if (selected == null) return;
            txtId.setText(selected.getId());
            cmbRegistration.setValue(selected.getRegistrationId());
            dpPaymentDate.setValue(selected.getPaymentDate());
            txtAmount.setText(String.valueOf(selected.getAmount()));
            cmbStatus.setValue(selected.getStatus());
            btnSave.setText("Update");
        });
    }


    @FXML
    private void handleSave() {
        try {
            if (!validateInputs()) return;

            String regId = cmbRegistration.getValue();
            Registration registration = registrationBO.searchRegistration(regId);
            if (registration == null) {
                showMessage("Registration not found!", true);
                return;
            }

            Payment payment = new Payment(
                    txtId.getText(),
                    dpPaymentDate.getValue(),
                    Double.parseDouble(txtAmount.getText().trim()),
                    cmbStatus.getValue(),
                    registration
            );

            if (btnSave.getText().equals("Update")) {
                paymentBO.updatePayment(payment);
                showMessage("Payment updated successfully!", false);
            } else {
                paymentBO.savePayment(payment);
                showMessage("Payment saved successfully!", false);
            }

            handleClear();
            loadPayments();

        } catch (NumberFormatException e) {
            showMessage("Amount must be a valid number!", true);
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), true);
        }
    }

    private void handleDelete(String paymentId) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete payment " + paymentId + "?",
                ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                try {
                    paymentBO.deletePayment(paymentId);
                    showMessage("Payment deleted.", false);
                    loadPayments();
                    if (txtId.getText().equals(paymentId)) handleClear();
                } catch (Exception e) {
                    showMessage("Delete failed: " + e.getMessage(), true);
                }
            }
        });
    }

    @FXML
    private void handleClear() {
        txtId.clear();
        cmbRegistration.setValue(null);
        dpPaymentDate.setValue(LocalDate.now());
        txtAmount.clear();
        cmbStatus.setValue(null);
        lblMessage.setText("");
        btnSave.setText("Save");
        loadNextId();
    }

    @FXML
    private void handleReportView() {
        showMessage("Report feature — connect JasperReports here.", false);
    }



    private boolean validateInputs() {
        if (cmbRegistration.getValue() == null) {
            showMessage("Please select a Registration.", true); return false;
        }
        if (dpPaymentDate.getValue() == null) {
            showMessage("Please select a Payment Date.", true); return false;
        }
        if (txtAmount.getText().isBlank()) {
            showMessage("Please enter an Amount.", true); return false;
        }
        if (cmbStatus.getValue() == null) {
            showMessage("Please select a Status.", true); return false;
        }
        return true;
    }



    private void showMessage(String msg, boolean isError) {
        lblMessage.setText(msg);
        lblMessage.setStyle(isError
                ? "-fx-text-fill:#e74c3c;-fx-font-weight:bold;"
                : "-fx-text-fill:#1d9e75;-fx-font-weight:bold;");
    }
}