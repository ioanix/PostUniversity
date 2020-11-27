package model;


public enum BikeType {

    CITYBIKE("CityBike"), MOUNTAINBIKE("MountainBike"), ELECTRICBIKE("ElectricBike");

    private String type;

    BikeType(String type) {

        this.type = type;
    }

    public String getBikeType() {

        return this.type;
    }
}
