package sample;

import domain.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.DoctorService;
import service.PatientService;
import service.PaymentService;
import service.UndoRedoService;

import java.security.KeyException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public class PaymentController {

    public TableView<Payment> tblPayments;

    public TableColumn idColumn;
    public TableColumn patientIdColumn;
    public TableColumn doctorIdColumn;
    public TableColumn cardNumberColumn;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colPrice;


    public ComboBox<Patient> patientComboBox;
    public ComboBox<Doctor> doctorComboBox;
    public TextField txtCardNumber;
    public TextField txtDate;
    public TextField txtTime;
    public TextField txtAmountPaid;

    public TextField txtPaymentSearch;

    public TextField txtStart;
    public TextField txtEnd;


    public Button btnAddPayment;
    public Button btnShowAllPayments;
    public Button btnDeletePayment;
    public Button btnShowPaymentsInARangeOfDays;
    public Button btnDeletePaymentsInARangeOfDays;
    public Button btnUndoPayment;

    public TableView<PatientsForDoctorViewModel> tblPatientsForDoctor;
    public TableColumn idDoctorColumn;
    public TableColumn doctorFirstNameColumn;
    public TableColumn doctorLastNameColumn;
    public TableColumn numberOfPatientsColumn;
    public TableColumn colTotalTime;
    public TableColumn colTotalPrice;



    private DoctorService doctorService;
    private PatientService patientService;
    private PaymentService paymentService;
    private UndoRedoService undoRedoService;


    private ObservableList<Payment> payments = FXCollections.observableArrayList();
    private ObservableList<PatientsForDoctorViewModel> patientsForDoctorList = FXCollections.observableArrayList();

    private ObservableList<Patient> patients = FXCollections.observableArrayList();
    private ObservableList<Doctor> doctors = FXCollections.observableArrayList();

    public void setServices(DoctorService doctorService, PatientService patientService, PaymentService paymentService, UndoRedoService undoRedoService) {

        this.doctorService = doctorService;
        this.patientService = patientService;
        this.paymentService = paymentService;
        this.undoRedoService = undoRedoService;
    }

    @FXML
    public void initialize() {

        Platform.runLater(() -> {

            payments.addAll(paymentService.getAll());
            tblPayments.setItems(payments);

            patientsForDoctorList.addAll(paymentService.showPatientsForDoctor());
            tblPatientsForDoctor.setItems(patientsForDoctorList);

            patients.addAll(patientService.getAll());
            patientComboBox.setItems(patients);

            doctors.addAll(doctorService.getAll());
            doctorComboBox.setItems(doctors);

        });
    }

    public void btnAddPaymentClick(ActionEvent actionEvent) {

        int patient_id = patientComboBox.getSelectionModel().getSelectedItem().getId();
        int doctor_id = doctorComboBox.getSelectionModel().getSelectedItem().getId();
        int cardNumber = Integer.parseInt(txtCardNumber.getText());
        String date = txtDate.getText();
        String time = txtTime.getText();
        double price = Double.parseDouble(txtAmountPaid.getText());

        try {

            paymentService.addPaymentService(patient_id, doctor_id, cardNumber, date, time, price);

            payments.clear();
            payments.addAll(paymentService.getAll());

            patientsForDoctorList.clear();
            patientsForDoctorList.addAll(paymentService.showPatientsForDoctor());

        } catch(IllegalArgumentException e) {

            Common.showValidationError(e.getMessage());

        } catch (RuntimeException rex) {

            Common.showValidationError(rex.getMessage());
            rex.printStackTrace();

        } catch (KeyException kex) {

            Common.showValidationError(kex.getMessage());

        }

    }

    public void btnShowAllPaymentsClick(ActionEvent actionEvent) {

        payments.clear();
        payments.addAll(paymentService.getAll());

    }

    public void btnDeletePaymentClick(ActionEvent actionEvent) {

        Payment selectedPayment = tblPayments.getSelectionModel().getSelectedItem();

        if (selectedPayment == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No payment selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the payment you want to delete");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete payment");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected payment");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            try {

                paymentService.deletePaymentService(selectedPayment.getId());

                payments.clear();
                payments.addAll(paymentService.getAll());

                patientsForDoctorList.clear();
                patientsForDoctorList.addAll(paymentService.showPatientsForDoctor());

            } catch (KeyException kex) {

                System.out.println(kex.getMessage());
            }
        }
    }


    public void btnShowPaymentsInARangeOfDays(ActionEvent actionEvent) {


        String start = txtStart.getText();
        String end = txtEnd.getText();

        try {

            List<Payment> paymentList = paymentService.getPaymentsInARangeOfDays(start, end);

            payments.clear();
            payments.addAll(paymentList);

        } catch (ParseException p) {

            System.out.println(p.getMessage());
        }

    }


    public void btnDeletePaymentsInARangeOfDays(ActionEvent actionEvent) {

        String start = txtStart.getText();
        String end = txtEnd.getText();

        try {

            paymentService.deletePaymentsInARangeOfDays(start, end);

            payments.clear();
            payments.addAll(paymentService.getAll());

            patientsForDoctorList.clear();
            patientsForDoctorList.addAll(paymentService.showPatientsForDoctor());


        } catch (KeyException key) {

            System.out.println(key.getMessage());

        } catch (ParseException p) {

            System.out.println(p.getMessage());
        }
    }


    public void btnUndoPaymentClick() {

        this.undoRedoService.undo();

        payments.clear();
        payments.addAll(paymentService.getAll());

        patientsForDoctorList.clear();
        patientsForDoctorList.addAll(paymentService.showPatientsForDoctor());

    }

}
