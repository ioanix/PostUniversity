package sample;

import domain.ClinicValidator;
import domain.Doctor;
import domain.Patient;
import domain.Payment;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.*;
import service.DoctorService;
import service.PatientService;
import service.PaymentService;
import service.UndoRedoService;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/sample.fxml"));

        Parent root = fxmlLoader.load();
        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        Controller controller = fxmlLoader.getController();

//        IRepository<Doctor> doctorRepository = new JSONFileRepository<>("doctors.txt", Doctor.class);
//        IRepository<Patient> patientRepository = new JSONFileRepository<>("patients.txt", Patient.class);
//        IRepository<Payment> paymentRepository = new JSONFileRepository<>("payments.txt", Payment.class);

        IRepository<Doctor> docRepo = new DBConnectionDoctorRepository();
        IRepository<Patient> patientRepo = new DBConnectionPatientRepository();
        IRepository<Payment> paymentRepo = new DBConnectionPaymentRepository();

        ClinicValidator clinicValidator = new ClinicValidator();

        UndoRedoService undoRedoService = new UndoRedoService();
        DoctorService doctorService = new DoctorService(docRepo, clinicValidator, undoRedoService);
        PatientService patientService = new PatientService(patientRepo, clinicValidator, undoRedoService);
        PaymentService paymentService = new PaymentService(paymentRepo, docRepo, patientRepo, clinicValidator, undoRedoService);

        controller.setServices(doctorService, patientService, paymentService, undoRedoService);

        Scene scene = new Scene(root, 1215, 650);
        //scene.getStylesheets().add("/styles.css");
        primaryStage.setTitle("Clinic Manager");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
