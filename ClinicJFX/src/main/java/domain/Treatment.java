package domain;

public class Treatment implements Reason {

    @Override
    public int getVisitTime() {

        return 40;
    }

    @Override
    public int getVisitPrice() {

        return 35;
    }
}
