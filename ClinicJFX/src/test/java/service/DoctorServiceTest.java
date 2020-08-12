package service;

import domain.ClinicValidator;
import domain.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DBConnectionDoctorRepository;
import repository.IRepository;

import java.security.KeyException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoctorServiceTest {


    private IRepository<Doctor> doctorIRepository;
    private ClinicValidator clinicValidator;
    private UndoRedoService undoRedoService;
    private DoctorService doctorService;


    @BeforeEach
    public void setup() {

        doctorIRepository = new DBConnectionDoctorRepository();
        clinicValidator = new ClinicValidator();
        undoRedoService = new UndoRedoService();

        doctorService = new DoctorService(doctorIRepository, clinicValidator, undoRedoService);

    }

    @Test
    void addingAValidDoctor_should_saveThatDoctorToRepository() throws KeyException {

        Doctor doctor = new Doctor("dd1", "d2", 35);

        doctorService.addDoctorService(doctor.getFirstName(), doctor.getLastName(), doctor.getAge());

        assertEquals(1, doctorService.getAll().size());

    }

    @org.junit.jupiter.api.Test
    void updateDoctorService() throws KeyException {

        Doctor doctor = new Doctor("dd8", "d4", 38);

        doctorIRepository.create(doctor);
////        Doctor doctor1 = new Doctor("dd9", "d5", 39);

        doctorService.updateDoctorService(doctor);

        assertEquals("dd8", doctorIRepository.read(23).getFirstName());

    }

    @org.junit.jupiter.api.Test
    void deleteDoctorService() throws KeyException {

        doctorService.deleteDoctorService(20);

        assertEquals(0, doctorIRepository.readAll().size());

    }

    @org.junit.jupiter.api.Test
    void getAll() throws KeyException {

        Doctor doctor = new Doctor("dd2", "d2", 36);

        doctorService.addDoctorService(doctor.getFirstName(), doctor.getLastName(), doctor.getAge());

        List<Doctor> doctors = doctorService.getAll();

        assertEquals(2, doctors.size());
    }

    @org.junit.jupiter.api.Test
    void searchDoctor() {

        List<Doctor> entities = doctorService.searchDoctor("1");

        assertEquals(1, entities.size());
    }
}


























