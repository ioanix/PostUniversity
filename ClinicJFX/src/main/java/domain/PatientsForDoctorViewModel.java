package domain;

public class PatientsForDoctorViewModel {

    private int doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private int numberOfPatients;
    private int time;
    private int price;


    public PatientsForDoctorViewModel(int doctorId, String doctorFirstName, String doctorLastName, int numberOfPatients, int time, int price) {

        this.doctorId = doctorId;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.numberOfPatients = numberOfPatients;
        this.time = time;
        this.price = price;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public int getNumberOfPatients() {
        return numberOfPatients;
    }

    public void setNumberOfPatients(int numberOfPatients) {
        this.numberOfPatients = numberOfPatients;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {

        return  doctorId + ". " +
                ", doctorFirstName='" + doctorFirstName + '\'' +
                ", doctorLastName='" + doctorLastName + '\'' +
                ", numberOfPatients=" + numberOfPatients +
                ", time=" + time +
                ", price=" + price;

    }
}
