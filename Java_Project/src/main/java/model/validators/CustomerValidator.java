package model.validators;

import model.Customer;
import model.Sale;

import java.util.Collection;

public class CustomerValidator implements Validator<Customer> {

    @Override
    public void validate(Customer customer) {

        String message = "";

        message += !customer.getPhone().matches("[0-9]+") ? "The customer's phone number must contain only digits!\n" : "";

        message += customer.getPhone().length() != 10 ? "The customer's phone number must contain 10 digits!" : "";

        if (!message.equals("")) {
            throw new BikeShopException(message);
        }
    }

    @Override
    public void validateList(Iterable<Customer> list) {

        if (((Collection<Customer>) list).isEmpty()) {

            throw new BikeShopException("There are no customers available");
        }
    }
}
