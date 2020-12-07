package model.validators;

import model.Customer;

import java.util.Collection;

public class CustomerValidator extends Validator<Customer> {

//    @Override
//    public void validate(Customer customer) {
//
//        String message = "";
//
//        message += !customer.getPhone().matches("[0-9]+") ? "The customer's phone number must contain only digits!\n" : "";
//
//        message += customer.getPhone().length() != 10 ? "The customer's phone number must contain 10 digits!" : "";
//
//        if (!message.equals("")) {
//            throw new BikeShopException(message);
//        }
//    }

    @Override
    public void validateList(Iterable<Customer> list) {

        if (((Collection<Customer>) list).isEmpty()) {

            throw new BikeShopException("There are no customers available");
        }
    }

    @Override
    protected Error<Customer> createError() {

        return Error.<Customer>errorIf(customer -> customer.getFirstName().equals(""), "name is null")
                .and(Error.errorIf(customer -> customer.getPhone().length() != 10,
                          "the customer's phone must contain 10 digits"))
                .and(Error.errorIf(customer -> !customer.getPhone().matches(("[0-9]+")),
                          "the customer's phone must contain only digits"));
    }
}
