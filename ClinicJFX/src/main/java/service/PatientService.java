package service;

import domain.*;
import repository.IRepository;

import java.security.KeyException;
import java.util.ArrayList;
import java.util.List;

public class PatientService {


    private IRepository<Patient> patientIRepository;
    private ClinicValidator clinicValidator;
    private UndoRedoService undoRedoService;


    public PatientService(IRepository<Patient> patientIRepository, ClinicValidator clinicValidator, UndoRedoService undoRedoService) {

        this.patientIRepository = patientIRepository;
        this.clinicValidator = clinicValidator;
        this.undoRedoService = undoRedoService;
    }


    public void addPatientService(String patientFirstName, String patientLastName, int patientAge, String consultationReason, String phoneNumber) throws KeyException {

        Patient patient = new Patient(patientFirstName, patientLastName, patientAge, consultationReason, phoneNumber);
        this.clinicValidator.validatePatient(patient);

        this.patientIRepository.create(patient);

        this.undoRedoService.addToUndo(new AddOperation<>(patientIRepository, patient));

    }

    public void updatePatientService(Patient patient) throws KeyException {

        this.patientIRepository.update(patient);
    }

    public List<Patient> getAll() {

        return patientIRepository.readAll();
    }

    public void deletePatientService(int id) throws KeyException {

        this.undoRedoService.addToUndo(new DeleteOperation<>(patientIRepository, patientIRepository.read(id)));

        this.patientIRepository.delete(id);

    }

    public List<Patient> searchPatient(String input) {

        List<Patient> patients = new ArrayList<>();

        for(Patient patient : patientIRepository.readAll()) {

            if(patient.getPatientFirstName().contains(input) || patient.getPatientLastName().contains(input) ||
                Integer.toString(patient.getPatientAge()).contains(input) || patient.getConsultationReason().contains(input) ||
                patient.getPhoneNumber().contains(input)) {

                patients.add(patient);
            }
        }

        return patients;
    }


    public List<PatientAgeViewModel> showSummaryOfPatientsBasedOnAge() {

        List<PatientAgeViewModel> patientsList = new ArrayList<>();

        int count = 0;
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;

        for(Patient patient : patientIRepository.readAll()) {

            if (patient.getPatientAge() == 0) {

                count++;
            }

            if (patient.getPatientAge() >= 1 && patient.getPatientAge() < 7) {

                count1++;
            }

            if (patient.getPatientAge() >= 7 && patient.getPatientAge() < 18) {

                count2++;
            }

            if (patient.getPatientAge() >= 18) {

                count3++;
            }
        }

        patientsList.add(new PatientAgeViewModel("Children(0-1) ", count));
        patientsList.add(new PatientAgeViewModel("Pupil(1-7) ", count1));
        patientsList.add(new PatientAgeViewModel("Students(7-18) ", count2));
        patientsList.add(new PatientAgeViewModel("Adults(>=18) ", count3));

        return patientsList;

    }
}
