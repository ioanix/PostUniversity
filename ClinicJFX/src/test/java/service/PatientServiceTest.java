package service;

import domain.ClinicValidator;
import domain.Patient;
import domain.PatientAgeViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DBConnectionPatientRepository;
import repository.IRepository;

import java.security.KeyException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatientServiceTest {

    private IRepository<Patient> patientIRepository;
    private ClinicValidator clinicValidator;
    private UndoRedoService undoRedoService;
    private PatientService patientService;

    @BeforeEach
    public void setup() {

        patientIRepository = new DBConnectionPatientRepository();
        clinicValidator = new ClinicValidator();
        undoRedoService = new UndoRedoService();
        patientService = new PatientService(patientIRepository, clinicValidator, undoRedoService);
    }

    @Test
    void addingAValidPatient_should_saveThatPatientToRepository() throws KeyException {

        Patient patient = new Patient("pppp1", "ppp1", 25, "consultation", "1234");

        patientService.addPatientService(patient.getPatientFirstName(),
                                         patient.getPatientLastName(),
                                         patient.getPatientAge(),
                                         patient.getConsultationReason(),
                                         patient.getPhoneNumber());

        assertEquals(1, patientIRepository.readAll().size());

    }

    @Test
    void updatePatientService() throws KeyException {

//        Patient patient = new Patient("pppp2", "ppp2", 25, "consultation", "1234");

        patientService.updatePatientService(patientIRepository.read(9));

        assertEquals("pppp1", patientIRepository.read(9).getPatientFirstName());
    }

    @Test
    void getAll() throws KeyException {

        Patient patient = new Patient("pppp3", "ppp3", 26, "consultation", "1567");

        patientIRepository.create(patient);

        List<Patient> entities = patientService.getAll();

        assertEquals(2, entities.size());
    }

    @Test
    void deletePatientService() throws KeyException {

        patientService.deletePatientService(1);

        assertEquals(0, patientIRepository.readAll().size());
    }

    @Test
    void searchPatient() throws KeyException {

        Patient patient = new Patient("pppp5", "ppp5", 21, "consultation", "1546");

        patientIRepository.create(patient);

        List<Patient> entities = patientService.searchPatient("6");

        assertEquals(2, entities.size());
    }

    @Test
    void showSummaryOfPatientsBasedOnAge() {

        List<PatientAgeViewModel> entities = patientService.showSummaryOfPatientsBasedOnAge();

        assertEquals(3, entities.get(3).getNumberOfPatients());
    }
}