package service;

import domain.AddOperation;
import domain.ClinicValidator;
import domain.DeleteOperation;
import domain.Doctor;
import repository.IRepository;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;

public class DoctorService {

    private IRepository<Doctor> doctorIRepository;
    private ClinicValidator clinicValidator;
    private UndoRedoService undoRedoService;


    public DoctorService(IRepository<Doctor> doctorIRepository, ClinicValidator clinicValidator, UndoRedoService undoRedoService) {

        this.doctorIRepository = doctorIRepository;
        this.clinicValidator = clinicValidator;
        this.undoRedoService = undoRedoService;

    }


    public void addDoctorService(String firstName, String lastName, int age) throws KeyException{

        Doctor doctor = new Doctor(firstName, lastName, age);
        this.clinicValidator.validateDoctor(doctor);

        this.doctorIRepository.create(doctor);

        this.undoRedoService.addToUndo(new AddOperation<>(doctorIRepository, doctor));

    }

    public void updateDoctorService(Doctor doctor) throws KeyException{

        this.doctorIRepository.update(doctor);
    }


    public void deleteDoctorService(int id) throws KeyException {

        this.undoRedoService.addToUndo(new DeleteOperation<>(doctorIRepository, doctorIRepository.read(id)));

        this.doctorIRepository.delete(id);
    }


    public List<Doctor> getAll() {

        return doctorIRepository.readAll();
    }


    public List<Doctor> searchDoctor(String input) {

        List<Doctor> doctorsList = new ArrayList<>();

        for (Doctor doctor : this.doctorIRepository.readAll()) {

            if (doctor.getFirstName().contains(input) || doctor.getLastName().contains(input) ||
                    Integer.toString(doctor.getAge()).contains(input)) {

                doctorsList.add(doctor);
            }
        }

        return doctorsList;
    }
}
