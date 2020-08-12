package domain;

public class ClinicValidator {


    public void validateDoctor(Doctor doctor) {

        String errorMessage = "";

        if (doctor.getAge() < 30 || doctor.getAge() > 65) {

            errorMessage += "The age must be a number between 30 and 65!\n";
        }

        if (doctor.getFirstName().length() != 3) {

            errorMessage += "First name must contain 3 characters\n";
        }

        if (doctor.getLastName().length() != 2) {

            errorMessage += "Last name must contain 2 characters\n";
        }

        if (!errorMessage.equals("")) {

            throw new ClinicException(errorMessage);
        }
    }

    public void validatePatient(Patient patient) {

        String errorMessage = "";

        if (patient.getPatientFirstName().length() != 5) {

            errorMessage += "The first name of the patient must contain 5 characters!\n";
        }

        if (patient.getPatientLastName().length() != 4) {

            errorMessage += "The last name of the patient must contain 4 characters!\n";
        }

        if (patient.getPatientAge() < 0 || patient.getPatientAge() > 85) {

            errorMessage += "The age of the patient must be between 0 and 85!\n";
        }

        if (!errorMessage.equals("")) {

            throw new ClinicException(errorMessage);
        }
    }


    public void validatePayment(Payment payment) {

        String errorMessage = "";

        if(payment.getPatientCardNumber() < 1000 || payment.getPatientCardNumber() > 9999) {

            errorMessage += "The card number must be a number of 4 digits\n";
        }

        if (!errorMessage.equals("")) {

            throw new ClinicException(errorMessage);
        }

    }
}
