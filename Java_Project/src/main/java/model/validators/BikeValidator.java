package model.validators;

import model.Bike;
import model.BikeType;

import java.util.Arrays;
import java.util.Collection;

public class BikeValidator implements Validator<Bike> {

    @Override
    public void validate(Bike bike) {

        String message = "";

        message += bike.getPrice() <= 0.00 ? "The price should be bigger than 0!" : "";

        boolean anyMatch = Arrays.stream(BikeType.values())
                .anyMatch(bikeType -> bikeType.getBikeType().equalsIgnoreCase(bike.getType().getBikeType()));

        message += !anyMatch ? "The category you entered is not valid. Please enter: e.g. citybike, mountainbike, electricbike" : "";

        if (!message.equals("")) {
            throw new BikeShopException(message);
        }
    }

    @Override
    public void validateList(Iterable<Bike> list) {

        if (((Collection<Bike>) list).isEmpty()) {

            throw new BikeShopException("There are no bikes available");
        }
    }
}
