package sample;

import domain.Doctor;
import javafx.scene.control.TextField;

public class DoctorController {

    public TextField firstNameField;
    public TextField lastNameField;
    public TextField ageField;



    public void editDoctor(Doctor doctor) {

        firstNameField.setText(doctor.getFirstName());
        lastNameField.setText(doctor.getLastName());
        ageField.setText(Integer.toString(doctor.getAge()));
    }

    public void updateDoctor(Doctor doctor) {

        doctor.setFirstName(firstNameField.getText());
        doctor.setLastName(lastNameField.getText());
        doctor.setAge(Integer.parseInt(ageField.getText()));
    }
}
