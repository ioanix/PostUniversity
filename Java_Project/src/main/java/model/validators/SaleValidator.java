package model.validators;

import model.Sale;

import java.time.LocalDate;
import java.util.Collection;

public class SaleValidator extends Validator<Sale> {

//    @Override
//    public void validate(Sale sale) {
//
//        String message = "";
//
//        message += sale.getSaleDate().isAfter(LocalDate.now()) ? "The sale date cannot be after the current date." : "";
//
//        if (!message.equals("")) {
//            throw new BikeShopException(message);
//        }
//    }

    @Override
    public void validateList(Iterable<Sale> list) {

        if (((Collection<Sale>) list).isEmpty()) {

            throw new BikeShopException("There are no sales available");
        }
    }

    @Override
    protected Error<Sale> createError() {

        return Error.errorIf(sale -> sale.getSaleDate().isAfter(LocalDate.now()),
                    "The sale date cannot be after the current date");

    }
}
