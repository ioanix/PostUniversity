package model;

import java.util.Objects;

public class Bike extends Entity<Long> {

    private String name;
    private BikeType type;
    private Double price;

    public Bike() {
    }

    public Bike(String name, BikeType type, Double price) {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Bike(Long id, String name, BikeType type, Double price) {
        super.setId(id);
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Long getId() {

        return super.getId();
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public BikeType getType() {

        return type;
    }

    public void setType(BikeType type) {

        this.type = type;
    }

    public Double getPrice() {

        return price;
    }

    public void setPrice(Double price) {

        this.price = price;
    }

    @Override
    public String toString() {
        return "Bike{id='" + super.getId() + "\' " +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return Double.compare(bike.price, price) == 0 &&
                Objects.equals(name, bike.name) &&
                Objects.equals(type, bike.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), name, type, price);
    }
}


