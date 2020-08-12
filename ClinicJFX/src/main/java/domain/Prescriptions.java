package domain;

public class Prescriptions implements Reason {

    @Override
    public int getVisitTime() {

        return 20;
    }

    @Override
    public int getVisitPrice() {

        return 20;
    }
}
