package model.validators;

import model.Bike;
import model.Sale;

import java.time.LocalDate;
import java.util.Collection;

public class SaleValidator implements Validator<Sale> {

    @Override
    public void validate(Sale sale) {

        String message = "";

        message += sale.getSaleDate().isAfter(LocalDate.now()) ? "The sale date cannot be after the current date." : "";

        if (!message.equals("")) {
            throw new BikeShopException(message);
        }
    }

    @Override
    public void validateList(Iterable<Sale> list) {

        if (((Collection<Sale>) list).isEmpty()) {

            throw new BikeShopException("There are no sales available");
        }
    }
}
