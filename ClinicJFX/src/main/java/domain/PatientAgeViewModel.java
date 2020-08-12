package domain;

public class PatientAgeViewModel {

    private String category;
    private int numberOfPatients;


    public PatientAgeViewModel(String category, int numberOfPatients) {

        this.category = category;
        this.numberOfPatients = numberOfPatients;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getNumberOfPatients() {
        return numberOfPatients;
    }

    public void setNumberOfPatients(int numberOfPatients) {
        this.numberOfPatients = numberOfPatients;
    }
}
