package sample;

import domain.PatientAgeViewModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import service.PatientService;

public class PatientController {

    public TableView<PatientAgeViewModel> tblPatientsOrdered;

    public TableColumn categoryColumn;
    public TableColumn numberOfPatientsColumn;

    private PatientService patientService;

    private ObservableList<PatientAgeViewModel> patients = FXCollections.observableArrayList();

    public void setServices(PatientService patientService) {

        this.patientService = patientService;
    }

    public void initialize() {

        Platform.runLater(() -> {

            patients.addAll(patientService.showSummaryOfPatientsBasedOnAge());
            tblPatientsOrdered.setItems(patients);

        });
    }

}
