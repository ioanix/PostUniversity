package domain;

public class Consultation implements Reason {

    @Override
    public int getVisitTime() {

        return 30;
    }

    @Override
    public int getVisitPrice() {

        return 50;
    }
}
