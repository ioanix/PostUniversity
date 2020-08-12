package sample;

import domain.Common;
import domain.Doctor;
import domain.Patient;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import service.DoctorService;
import service.PatientService;
import service.PaymentService;
import service.UndoRedoService;

import java.io.IOException;
import java.security.KeyException;
import java.util.List;
import java.util.Optional;

public class Controller {


    public TableView<Doctor> tblDoctors;

    public GridPane mainPanel;
    public TableColumn colIdDoctor;
    public TableColumn colDoctorFirstName;
    public TableColumn colDoctorLastName;
    public TableColumn colDoctorAge;

    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtAge;
    public TextField txtSearch;

    public Button btnAddDoctor;
    public Button btnShowAllDoctors;
    public Button btnEditDoctor;
    public Button btnSearch;
    public Button btnDelete;
    public Button btnPayments;
    public Button btnUndo;


    public TableView<Patient> tblPatients;

    public TableColumn colIdPatient;
    public TableColumn colPatientFirstName;
    public TableColumn colPatientLastName;
    public TableColumn colPatientAge;
    public TableColumn colConsultationReason;
    public TableColumn colPhoneNumber;

    public TextField txtPatientFirstName;
    public TextField txtPatientLastName;
    public TextField txtPatientAge;
    public ComboBox reasonComboBox;
    public TextField txtPhoneNumber;
    public TextField txtPatientSearch;

    public Button btnAddPatient;
    public Button btnShowAllPatients;
    public Button btnEditPatient;
    public Button btnSearchPatient;
    public Button btnDeletePatient;
    public Button btnShowPatientsBaseOnAge;
    public Button btnUndoPatient;

    private DoctorService doctorService;
    private PatientService patientService;
    private PaymentService paymentService;
    private UndoRedoService undoRedoService;


    private ObservableList<Doctor> doctors = FXCollections.observableArrayList();
    private ObservableList<Patient> patients = FXCollections.observableArrayList();


    public void setServices(DoctorService doctorService, PatientService patientService, PaymentService paymentService, UndoRedoService undoRedoService) {

        this.doctorService = doctorService;
        this.patientService = patientService;
        this.paymentService = paymentService;
        this.undoRedoService = undoRedoService;
    }

    @FXML
    public void initialize() {

        Platform.runLater(() -> {

            doctors.addAll(doctorService.getAll());
            tblDoctors.setItems(doctors);

            patients.addAll(patientService.getAll());
            tblPatients.setItems(patients);


        });
    }

    public void btnAddDoctorClick(ActionEvent actionEvent) {

        try {

            doctorService.addDoctorService(txtFirstName.getText(), txtLastName.getText(), Integer.parseInt(txtAge.getText()));

            doctors.clear();
            doctors.addAll(doctorService.getAll());

        } catch (RuntimeException e) {

            Common.showValidationError(e.getMessage());

        } catch (KeyException key) {

            key.printStackTrace();
        }

    }


    public void btnShowAllDoctorsClick(ActionEvent actionEvent) {

        doctors.clear();
        doctors.addAll(doctorService.getAll());
    }

    public void btnEditDoctorClick() {

        Doctor selectedDoctor = tblDoctors.getSelectionModel().getSelectedItem();

        if (selectedDoctor == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No doctor selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the doctor you want to edit");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit doctor");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/doctor.fxml"));

        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {

            e.printStackTrace();
            return;

        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        DoctorController doctorController = fxmlLoader.getController();

        doctorController.editDoctor(selectedDoctor);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            doctorController.updateDoctor(selectedDoctor);

            try {

                this.doctorService.updateDoctorService(selectedDoctor);

            } catch (KeyException key) {

                key.printStackTrace();
            }

            doctors.clear();
            doctors.addAll(doctorService.getAll());

        }

    }

    public void btnSearchClick(ActionEvent actionEvent) {

        String input = txtSearch.getText();

        List<Doctor> searchDoctorList = doctorService.searchDoctor(input);

        doctors.clear();
        doctors.addAll(searchDoctorList);

    }

    public void btnDeleteDoctorClick(ActionEvent actionEvent) {

        Doctor selectedDoctor = tblDoctors.getSelectionModel().getSelectedItem();

        if (selectedDoctor == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No doctor selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the doctor you want to delete");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete doctor");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected doctor?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            try {

                doctorService.deleteDoctorService(selectedDoctor.getId());
                doctors.clear();
                doctors.addAll(doctorService.getAll());

            } catch (KeyException kex) {

                kex.printStackTrace();
            }
        }

    }

    public void btnAddPatientClick(ActionEvent actionEvent) {

        try {

            String patientFirstName = txtPatientFirstName.getText();
            String patientLastName = txtPatientLastName.getText();
            int patientAge = Integer.parseInt(txtPatientAge.getText());
            String consultationReason = reasonComboBox.getSelectionModel().getSelectedItem().toString();
            String phoneNumber = txtPhoneNumber.getText();

            patientService.addPatientService(patientFirstName, patientLastName, patientAge, consultationReason, phoneNumber);

            patients.clear();
            patients.addAll(patientService.getAll());

        } catch (RuntimeException rex) {

            Common.showValidationError(rex.getMessage());

        } catch (KeyException kex) {

            Common.showValidationError(kex.getMessage());
            kex.printStackTrace();

        }
    }


    public void btnShowAllPatientsClick(ActionEvent actionEvent) {

        patients.clear();
        patients.addAll(patientService.getAll());
    }

    public void btnEditPatientClick() {

        Patient selectedPatient = tblPatients.getSelectionModel().getSelectedItem();

        if (selectedPatient == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No patient selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the patient you want to edit");
            alert.showAndWait();
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit patient");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/patient.fxml"));

        try {

            dialog.getDialogPane().setContent(fxmlLoader.load());

        } catch (IOException e) {

            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        PatientEditController patientEditController = fxmlLoader.getController();
        patientEditController.editPatient(selectedPatient);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            patientEditController.updatePatient(selectedPatient);

            try {

                this.patientService.updatePatientService(selectedPatient);

            } catch (KeyException kex) {

                kex.printStackTrace();
            }

            patients.clear();
            patients.addAll(patientService.getAll());
        }

    }

    public void btnSearchPatientClick(ActionEvent actionEvent) {

        String input = txtPatientSearch.getText();

        List<Patient> searchPatientList = patientService.searchPatient(input);

        patients.clear();
        patients.addAll(searchPatientList);

    }


    public void btnDeletePatientClick(ActionEvent actionEvent) {

        Patient selectedPatient = tblPatients.getSelectionModel().getSelectedItem();

        if (selectedPatient == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No patient selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select the patient you want to delete");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete patient");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the selected patient?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            try {

                patientService.deletePatientService(selectedPatient.getId());

                patients.clear();
                patients.addAll(patientService.getAll());

            } catch (KeyException kex) {

                kex.printStackTrace();
            }
        }

    }

    public void btnShowPayments(ActionEvent actionEvent) {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/payments.fxml"));
//        File file = new File("payments.fxml");
//        System.out.println("The absolute path is: " + file.getAbsolutePath());

        Parent view;

        try {

            view = fxmlLoader.load();

        } catch (IOException e) {

            System.out.println("Couldn't load the window");
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(view, 1350, 650);

        PaymentController paymentController = fxmlLoader.getController();
        paymentController.setServices(doctorService, patientService,paymentService, undoRedoService);

        Stage window = new Stage();
        window.setTitle("Payments");
        window.setScene(scene);
        window.show();

    }


    public void btnShowPatientsBasedOnAgeClick() {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/patients_ordered.fxml"));

        Parent view;

        try {

            view = fxmlLoader.load();

        } catch (IOException e) {

            System.out.println("Couldn't load the window");
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(view, 360, 400);

        PatientController patientController = fxmlLoader.getController();
        patientController.setServices(patientService);

        Stage window = new Stage();
        window.setTitle("Patients ordered by age category");
        window.setScene(scene);
        window.show();

    }


    public void btnUndoClick() {

        this.undoRedoService.undo();

        doctors.clear();
        doctors.addAll(doctorService.getAll());

    }

    public void btnUndoPatientClick() {

        this.undoRedoService.undo();

        patients.clear();
        patients.addAll(patientService.getAll());
    }

}




























