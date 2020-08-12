package sample;


import domain.Patient;
import javafx.scene.control.TextField;

public class PatientEditController {

    public TextField patientFirstNameField;
    public TextField patientLastNameField;
    public TextField patientAgeField;
    public TextField consultationReasonField;
    public TextField phoneNumberField;


    public void editPatient(Patient patient) {

        patientFirstNameField.setText(patient.getPatientFirstName());
        patientLastNameField.setText(patient.getPatientLastName());
        patientAgeField.setText(Integer.toString(patient.getPatientAge()));
        consultationReasonField.setText(patient.getConsultationReason());
        phoneNumberField.setText(patient.getPhoneNumber());

    }

    public void updatePatient(Patient patient) {

        patient.setPatientFirstName(patientFirstNameField.getText());
        patient.setPatientLastName(patientLastNameField.getText());
        patient.setPatientAge(Integer.parseInt(patientAgeField.getText()));
        patient.setConsultationReason(consultationReasonField.getText());
        patient.setPhoneNumber(phoneNumberField.getText());

    }

}
