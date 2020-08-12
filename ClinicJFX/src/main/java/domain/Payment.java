package domain;

import java.util.Objects;

public class Payment extends Entity {

    private int patientId;
    private int doctorId;
    private int patientCardNumber;
    private String date;
    private String time;
    private double price;


    public Payment() {

    }

    public Payment(int patientId, int doctorId, int patientCardNumber, String date, String time, double price) {

        this.patientId = patientId;
        this.doctorId = doctorId;
        this.patientCardNumber = patientCardNumber;
        this.date = date;
        this.time = time;
        this.price = price;
    }


    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientCardNumber() {
        return patientCardNumber;
    }

    public void setPatientCardNumber(int patientCardNumber) {
        this.patientCardNumber = patientCardNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {

        return "Payment{" +
                "id=" + id +
                ", doctorId=" + doctorId +
                ", patientId=" + patientId +
                ", patientCardNumber=" + patientCardNumber +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (patientId != payment.patientId) return false;
        if (doctorId != payment.doctorId) return false;
        if (patientCardNumber != payment.patientCardNumber) return false;
        if (Double.compare(payment.price, price) != 0) return false;
        if (date != null ? !date.equals(payment.date) : payment.date != null) return false;
        return time != null ? time.equals(payment.time) : payment.time == null;

    }

    @Override
    public int hashCode() {

        return Objects.hash(id, patientId, doctorId, patientCardNumber, date, time);
    }
}
