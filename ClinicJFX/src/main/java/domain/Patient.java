package domain;

import java.util.Objects;

public class Patient extends Entity {

    private String patientFirstName;
    private String patientLastName;
    private int patientAge;
    private String consultationReason;
    private String phoneNumber;


    public Patient() {

    }

    public Patient(String patientFirstName, String patientLastName, int patientAge, String consultationReason, String phoneNumber) {

        this.patientFirstName = patientFirstName;
        this.patientLastName = patientLastName;
        this.patientAge = patientAge;
        this.consultationReason = consultationReason;
        this.phoneNumber = phoneNumber;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public int getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }

    public String getConsultationReason() {
        return consultationReason;
    }

    public void setConsultationReason(String consultationReason) {
        this.consultationReason = consultationReason;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {

        return id + ". " + patientFirstName + " " + patientLastName + " " + patientAge + " years " + consultationReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;

        if (patientAge != patient.patientAge) return false;
        if (patientFirstName != null ? !patientFirstName.equals(patient.patientFirstName) : patient.patientFirstName != null)
            return false;
        if (patientLastName != null ? !patientLastName.equals(patient.patientLastName) : patient.patientLastName != null)
            return false;
        if (consultationReason != null ? !consultationReason.equals(patient.consultationReason) : patient.consultationReason != null)
            return false;
        return phoneNumber != null ? phoneNumber.equals(patient.phoneNumber) : patient.phoneNumber == null;

    }

    @Override
    public int hashCode() {

        return Objects.hash(id, patientFirstName, patientLastName, patientAge, consultationReason, phoneNumber);
    }
}
