package ui;

import model.*;
import model.validators.BikeShopException;
import service.BikeService;
import service.CustomerService;
import service.SaleService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.StreamSupport;

public class Console {

    private BikeService bikeService;
    private CustomerService customerService;
    private SaleService saleService;

    private Scanner in = new Scanner(System.in);

    public Console(BikeService bikeService, CustomerService customerService, SaleService saleService) {

        this.bikeService = bikeService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    private void showMainMenu() {

        System.out.println("1. Bikes");
        System.out.println("2. Customers");
        System.out.println("3. Sales");
        System.out.println("x. Exit");
        System.out.println("Option: ");
    }

    private void showBikesMenu() {

        System.out.println("1. Add bike");
        System.out.println("2. Print all bikes");
        System.out.println("3. Search bike by id");
        System.out.println("4. Delete bike");
        System.out.println("5. Update bike");
        System.out.println("6. Show the most expensive bike");
        System.out.println("7. Show bikes based on categories");
        System.out.println("8. Search bike by category");
        System.out.println("9. Show bikes ordered by price");
        System.out.println("10. Show bikes paging");
        System.out.println("11. Sort bikes by...");
        System.out.println("12. Sort bikes by... in descending order");
        System.out.println("13. Sort bikes by first property in descending order and by second property in ascending order");
        System.out.println("14. Return to the main menu");
        System.out.println("Option: ");

    }

    private void showCustomersMenu() {

        System.out.println("1. Add customer");
        System.out.println("2. Print all customers");
        System.out.println("3. Search customer by id");
        System.out.println("4. Delete customer");
        System.out.println("5. Update customer");
        System.out.println("6. Show customers from a specific city");
        System.out.println("7. Show customers ordered by last name");
        System.out.println("8. Show customers paging");
        System.out.println("9. Sort customers by...");
        System.out.println("10. Sort customers by... in descending order");
        System.out.println("11. Sort customers by first property in descending order and by second property in ascending order");
        System.out.println("12. Return to the main menu");
        System.out.println("Option: ");

    }

    private void showSalesMenu() {

        System.out.println("1. Add sale");
        System.out.println("2. Print all sales");
        System.out.println("3. Search sale by id");
        System.out.println("4. Delete sale");
        System.out.println("5. Update sale");
        System.out.println("6. Show customers ordered by the number of purchases");
        System.out.println("7. Show sales ordered by sale date");
        System.out.println("8. Show sales paging");
        System.out.println("9. Sort sales by...");
        System.out.println("10. Sort sales by... in descending order");
        System.out.println("11. Sort sales by first property in descending order and by second property in ascending order");
        System.out.println("12. Return to the main menu");
        System.out.println("Option: ");

    }

    private void bikes_menu() {

        while (true) {

            this.showBikesMenu();

            String option = in.nextLine();
            switch (option) {
                case "1" -> this.handleAddBike();

                case "2" -> this.handleShowAllBikes();

                case "3" -> this.handleSearchBikeById();

                case "4" -> this.handleDeleteBike();

                case "5" -> this.handleUpdateBike();

                case "6" -> this.handleShowBikeWithMaxPrice();

                case "7" -> this.handleShowBikesGroupedByCategory();

                case "8" -> this.handleSearchBikeByCategory();

                case "9" -> this.handleShowBikesOrderedByPrice();

                case "10" -> this.showBikesPaging();

                case "11" -> this.sortBikes();

                case "12" -> this.sortBikesDescending();

                case "13" -> this.sortBikesByTwoProperties();

                case "14" -> {
                    return;
                }
                default -> System.out.println("Invalid order");
            }
        }
    }



    private void customers_menu() {

        while (true) {

            this.showCustomersMenu();

            String option = in.nextLine();
            switch (option) {

                case "1" -> this.handleAddCustomer();

                case "2" -> this.handleShowAllCustomers();

                case "3" -> this.handleSearchCustomerById();

                case "4" -> this.handleDeleteCustomer();

                case "5" -> this.handleUpdateCustomer();

                case "6" -> this.handleShowCustomersFromASpecificCity();

                case "7" -> this.handleShowCustomersOrderedByLastName();

                case "8" -> this.showCustomersPaging();

                case "9" -> this.sortCustomers();

                case "10" -> this.sortCustomersDescending();

                case "11" -> this.sortCustomersByTwoProperties();

                case "12" -> {
                    return;
                }
                default -> System.out.println("Invalid order");
            }
        }
    }

    private void sales_menu() {

        while (true) {

            this.showSalesMenu();

            String option = in.nextLine();
            switch (option) {

                case "1" -> this.handleAddSale();

                case "2" -> this.handleShowAllSales();

                case "3" -> this.handleSearchSaleById();

                case "4" -> this.handleDeleteSale();

                case "5" -> this.handleUpdateSale();

                case "6" -> this.handleShowCustomersOrderedByNumberOfPurchases();

                case "7" -> this.handleShowSalesOrderedBySaleDate();

                case "8" -> this.showSalesPaging();

                case "9" -> this.sortSales();

                case "10" -> this.sortSalesDescending();

                case "11" -> this.sortSalesByTwoProperties();

                case "12" -> {
                    return;
                }

                default -> System.out.println("Invalid order");
            }
        }
    }

    public void runUserInterface() {

        while (true) {

            showMainMenu();

            String option = in.nextLine();
            switch (option) {
                case "1" -> this.bikes_menu();

                case "2" -> this.customers_menu();

                case "3" -> this.sales_menu();

                case "x" -> {
                    return;
                }

                default -> System.out.println("Invalid order");
            }
        }
    }

    private void sortSalesByTwoProperties() {

        try {

            System.out.println("Enter the first field by which you want to sort the sales: ");

            String firstField = in.nextLine();

            System.out.println("Enter the second field by which you want to sort the sales: ");

            String secondField = in.nextLine();

            StreamSupport.stream(this.saleService.getSalesSortedByFirstFieldDescendingAndBySecondFieldAscending(firstField, secondField).spliterator(), false)
                    .forEach(System.out::println);

        } catch (RuntimeException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void sortSalesDescending() {

        try {

            System.out.println("Enter the field by which you want to sort the sales: ");

            String field = in.nextLine();

            StreamSupport.stream(this.saleService.getSalesSortedInDescendingOrder(field).spliterator(), false)
                    .forEach(System.out::println);

        } catch (RuntimeException e) {

            System.out.println(e.getMessage());
        }
    }

    private void sortSales() {

        try {

            System.out.println("Enter the field by which you want to sort the sales: ");

            String field = in.nextLine();

            StreamSupport.stream(this.saleService.getSalesSorted(field).spliterator(), false)
                    .forEach(System.out::println);

        } catch (RuntimeException e) {

            System.out.println(e.getMessage());
        }
    }

    private void showSalesPaging() {

        System.out.println("enter page size: ");
        int size = in.nextInt();
        saleService.setPageSize(size);

        System.out.println("enter 'n' - for next; 'x' - for exit: ");

        while (true) {

            String option = in.next();

            switch (option) {

                case "x" -> {
                    System.out.println("exit");
                    in.nextLine();
                    return;
                }
                case "n" -> {
                    List<Sale> sales = saleService.getNextSales();

                    String message = sales.isEmpty() ? "No more sales" : "";

                    System.out.println(message);

                    sales.forEach(System.out::println);
                }
                default -> {
                    return;
                }
            }
        }
    }

    private void sortCustomersByTwoProperties() {

        try {

            System.out.println("Enter the first field by which you want to sort the customers: ");

            String firstField = in.nextLine();

            System.out.println("Enter the second field by which you want to sort the customers: ");

            String secondField = in.nextLine();

            StreamSupport.stream(this.customerService.getCustomersSortedByFirstFieldDescendingAndBySecondFieldAscending(firstField, secondField).spliterator(), false)
                    .forEach(System.out::println);

        } catch (RuntimeException e) {

            System.out.println(e.getMessage());
        }
    }

    private void sortCustomersDescending() {

        try {

            System.out.println("Enter the field by which you want to sort the customers: ");

            String field = in.nextLine();

            StreamSupport.stream(this.customerService.getCustomersSortedInDescendingOrder(field).spliterator(), false)
                    .forEach(System.out::println);

        } catch (RuntimeException e) {

            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
    }

    private void sortCustomers() {

        try {

            System.out.println("Enter the field by which you want to sort the bikes: ");

            String field = in.nextLine();

            StreamSupport.stream(this.customerService.getCustomersSorted(field).spliterator(), false)
                    .forEach(System.out::println);

        } catch (RuntimeException e) {

            System.out.println(e.getMessage());
        }
    }

    private void showCustomersPaging() {

        System.out.println("enter page size: ");
        int size = in.nextInt();
        customerService.setPageSize(size);

        System.out.println("enter 'n' - for next; 'x' - for exit: ");

        while (true) {

            String option = in.next();

            switch (option) {

                case "x" -> {
                    System.out.println("exit");
                    in.nextLine();
                    return;
                }

                case "n" -> {
                    List<Customer> customers = customerService.getNextCustomers();

                    String message = customers.isEmpty() ? "No more customers" : "";

                    System.out.println(message);

                    customers.forEach(System.out::println);
                }

                default -> {
                    return;
                }
            }
        }
    }

    private void sortBikesByTwoProperties() {

        try {

            System.out.println("Enter the first field by which you want to sort the bikes: ");

            String firstField = in.nextLine();

            System.out.println("Enter the second field by which you want to sort the bikes: ");

            String secondField = in.nextLine();

            StreamSupport.stream(this.bikeService.getBikesSortedByFirstFieldDescendingAndBySecondFieldAscending(firstField, secondField).spliterator(), false)
                    .forEach(System.out::println);

        } catch (RuntimeException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void sortBikesDescending() {

        try {

            System.out.println("Enter the field by which you want to sort the bikes: ");

            String field = in.nextLine();

            StreamSupport.stream(this.bikeService.getBikesSortedInDescendingOrder(field).spliterator(), false)
                    .forEach(System.out::println);

        } catch (RuntimeException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void sortBikes() {

        try {

            System.out.println("Enter the field by which you want to sort the bikes: ");

            String field = in.nextLine();

            StreamSupport.stream(this.bikeService.getBikesSorted(field).spliterator(), false)
                    .forEach(System.out::println);

        } catch (RuntimeException e) {

            System.out.println(e.getMessage());
        }
    }

    private void showBikesPaging() {

        System.out.println("enter page size: ");
        int size = in.nextInt();
        bikeService.setPageSize(size);

        System.out.println("enter 'n' - for next; 'x' - for exit: ");

        while (true) {

            String option = in.next();

            switch (option) {

                case "x" -> {
                    System.out.println("exit");
                    in.nextLine();
                    return;
                }

                case "n" -> {
                    List<Bike> bikes = bikeService.getNextBikes();

                    String message = bikes.isEmpty() ? "No more bikes" : "";

                    System.out.println(message);

                    bikes.forEach(System.out::println);
                }

                default -> {
                    return;
                }
            }
        }
    }

    private void handleShowSalesOrderedBySaleDate() {

        List<Sale> sales = this.saleService.showSalesOrderedBySaleDate();

        sales.forEach(System.out::println);
    }

    private void handleShowCustomersOrderedByLastName() {

        List<Customer> customers = this.customerService.showCustomersOrderedByLastName();

        customers.forEach(System.out::println);
    }

    private void handleShowCustomersOrderedByNumberOfPurchases() {

        List<CustomerPurchasesViewModel> customerPurchases = this.saleService.showCustomersOrderedByNumberOfPurchases();

        customerPurchases.forEach(System.out::println);
    }

    private void handleShowBikesOrderedByPrice() {

        Iterable<Bike> bikesOrderedByPrice = this.bikeService.showBikesOrderedByPrice();

        bikesOrderedByPrice.forEach(System.out::println);
    }

    private void handleUpdateSale() {

        try {

            System.out.println("Id: ");
            Long s_id = in.nextLong();

            System.out.println("Bike id: ");
            Long b_id = in.nextLong();
            in.nextLine();

            System.out.println("Customer id: ");
            Long c_id = in.nextLong();
            in.nextLine();

            System.out.println("Date of the sale: ");
            LocalDate saleDate = LocalDate.parse(in.nextLine());

            Optional<Sale> sale = this.saleService.updateSaleService(s_id, b_id, c_id, saleDate);

            sale.ifPresentOrElse(s -> System.out.println("Sale with id = " + s_id + " updated successfully!"),
                    () -> System.out.println("Nothing to update"));

        } catch (BikeShopException | IllegalArgumentException bex) {

            System.out.println(bex.getMessage());
        }
    }

    private void handleDeleteSale() {

        try {

            System.out.println("Id of the sale you want to delete: ");
            Long s_id = in.nextLong();
            in.nextLine();

            this.saleService.deleteSaleService(s_id);

            System.out.println("Sale with id = " + s_id + " deleted successfully!");

        } catch (BikeShopException | IllegalArgumentException bex) {

            System.out.println(bex.getMessage());
        }
    }

    private void handleAddSale() {

//        System.out.println("Id: ");
//        Long s_id = in.nextLong();
//        in.nextLine();
        try {

            System.out.println("Bike id: ");
            Long b_id = in.nextLong();
            in.nextLine();

            System.out.println("Customer id: ");
            Long c_id = in.nextLong();
            in.nextLine();

            System.out.println("Date of the sale: ");
            LocalDate saleDate = LocalDate.parse(in.nextLine());

            this.saleService.addSaleService(b_id, c_id, saleDate);

        } catch (BikeShopException | IllegalArgumentException bex) {

            System.out.println(bex.getMessage());
        }
    }

    private void handleShowAllSales() {

        try {

            StreamSupport.stream(this.saleService.getAll().spliterator(), false)
                    .forEach(System.out::println);

        } catch (BikeShopException bex) {

            System.out.println(bex.getMessage());
        }
    }

    private void handleSearchSaleById() {

        try {

            System.out.println("Id: ");
            Long id = in.nextLong();
            in.nextLine();

            this.saleService.findOneSale(id).ifPresent(sale -> System.out.println(sale.toString()));

        } catch (BikeShopException | IllegalArgumentException bex) {

            System.out.println(bex.getMessage());
        }
    }

    private void handleSearchCustomerById() {

        try {

            System.out.println("Id: ");
            Long id = in.nextLong();
            in.nextLine();

            this.customerService.findOneCustomer(id).ifPresent(customer -> System.out.println(customer.toString()));

        } catch (BikeShopException | IllegalArgumentException bex) {

            System.out.println(bex.getMessage());
        }

    }

    private void handleSearchBikeById() {

        try {

            System.out.println("Id: ");
            Long id = in.nextLong();
            in.nextLine();

            this.bikeService.findOneBike(id).ifPresent(bike -> System.out.println(bike.toString()));

        } catch (BikeShopException | IllegalArgumentException bex) {

            System.out.println(bex.getMessage());
        }

    }

    private void handleUpdateCustomer() {

        try {

            System.out.println("Id: ");
            Long id = in.nextLong();
            in.nextLine();

            System.out.println("First name: ");
            String firstName = in.nextLine();

            System.out.println("Last name: ");
            String lastName = in.nextLine();

            System.out.println("Phone: ");
            String phone = in.nextLine();

            System.out.println("City: ");
            String city = in.nextLine();

            System.out.println("Street: ");
            String street = in.nextLine();

            System.out.println("Number: ");
            String number = in.nextLine();

            Optional<Customer> customer = this.customerService.updateCustomerService(
                    id,
                    firstName,
                    lastName,
                    phone,
                    city,
                    street,
                    number);

            customer.ifPresentOrElse(c -> System.out.println("Customer with id " + id + " updated successfully!"),
                    () -> System.out.println("Nothing to update"));

        } catch (BikeShopException | IllegalArgumentException bex) {

            System.out.println(bex.getMessage());
        }

    }

    private void handleUpdateBike() {

        try {

            System.out.println("Id :");
            Long id = in.nextLong();
            in.nextLine();

            System.out.println("Name: ");
            String name = in.nextLine();

            System.out.println("Bike type: ");
            BikeType type = BikeType.valueOf(in.nextLine().toUpperCase());

            System.out.println("Price: ");
            double price = in.nextDouble();
            in.nextLine();

            Optional<Bike> bike = this.bikeService.updateBikeService(id, name, type, price);

            bike.ifPresentOrElse(b -> System.out.println("Bike with id " + id + " updated successfully!"),
                    () -> System.out.println("Nothing to update"));

        } catch (BikeShopException | IllegalArgumentException bex) {

            System.out.println(bex.getMessage());
        }
    }

    private void handleDeleteCustomer() {

        try {

            System.out.println("Enter the id of the customer you want to delete: ");

            Long id = in.nextLong();
            in.nextLine();

            this.customerService.deleteCustomerService(id);

            System.out.println("Customer with id = " + id + " deleted successfully!");

        } catch (BikeShopException | IllegalArgumentException bex) {

            System.out.println(bex.getMessage());
        }
    }

    private void handleDeleteBike() {

        try {

            System.out.println("Enter the id of the bike you want to delete: ");

            Long id = in.nextLong();
            in.nextLine();

            this.bikeService.deleteBikeService(id);

            System.out.println("Bike with id " + id + " deleted successfully!");

        } catch (BikeShopException | IllegalArgumentException bex) {

            System.out.println(bex.getMessage());
        }
    }

    private void handleSearchBikeByCategory() {

        try {

            System.out.println("Enter the category: ");
            String category = in.nextLine();

            List<Bike> bikes = this.bikeService.searchBikeByCategory(category);

            bikes.stream()
                    .forEach(System.out::println);

        } catch (BikeShopException e) {

            System.out.println(e.getMessage());

        }
    }

    private void handleShowCustomersFromASpecificCity() {

        System.out.println("Please enter city: ");
        String city = in.nextLine();

        List<Customer> customers = this.customerService.showCustomersFromASpecificCity(city);

        customers.stream()
                .forEach(System.out::println);
    }

    private void handleShowBikesGroupedByCategory() {

        Map<BikeType, List<Bike>> bikeTypeListMap = this.bikeService.showBikesGroupedByCategory();

        bikeTypeListMap.forEach((type, bikes) -> {
            System.out.println(type);
            bikes.forEach(System.out::println);
            System.out.println();
        });
    }

    private void handleShowBikeWithMaxPrice() {

        List<Bike> bikes = this.bikeService.showBikeWithMaxPrice();
        bikes.stream()
                .forEach(System.out::println);
    }

    private void handleShowAllBikes() {

        try {

            StreamSupport.stream(this.bikeService.getAll().spliterator(), false)
                    .forEach(System.out::println);

        } catch (BikeShopException bex) {

            System.out.println(bex.getMessage());

        }
    }

    private void handleAddBike() {

        try {

//            System.out.println("Id: ");
//            Long id = in.nextLong();
//            in.nextLine();

            System.out.println("Name: ");
            String name = in.nextLine();

            System.out.println("Bike type: ");

            String type = in.nextLine();
            boolean anyMatch = Arrays.stream(BikeType.values())
                    .anyMatch(bikeType -> bikeType.getBikeType().equalsIgnoreCase(type.toUpperCase()));

            if (!anyMatch) {

                System.out.println("The category you entered is not valid. Please enter: e.g. citybike, mountainbike, electricbike");
                return;
            }

            BikeType bikeType = BikeType.valueOf(type.toUpperCase());

            System.out.println("Price: ");
            double price = in.nextDouble();
            in.nextLine();

            this.bikeService.addBikeService(
                    name,
                    bikeType,
                    price
            );

        } catch (BikeShopException | IllegalArgumentException bex) {
            System.out.println(bex.getMessage());
        } catch (Exception exception) {
            System.out.println("Unknown error");
        }
    }

    private void handleShowAllCustomers() {

        try {

            StreamSupport.stream(this.customerService.getAll().spliterator(), false)
                    .forEach(customer -> System.out.println(customer));  // can be replaced with method reference

        } catch (BikeShopException bex) {

            System.out.println(bex.getMessage());
        }

    }

    private void handleAddCustomer() {

        try {

//            System.out.println("Id: ");
//            Long id = in.nextLong();
//            in.nextLine();

            System.out.println("First name: ");
            String firstName = in.nextLine();

            System.out.println("Last name: ");
            String lastName = in.nextLine();

            System.out.println("Phone: ");
            String phone = in.nextLine();

            System.out.println("City: ");
            String city = in.nextLine();

            System.out.println("Street: ");
            String street = in.nextLine();

            System.out.println("Number: ");
            String number = in.nextLine();

            this.customerService.addCustomerService(
                    firstName,
                    lastName,
                    phone,
                    city,
                    street,
                    number
            );
        } catch (BikeShopException | IllegalArgumentException bex) {
            System.out.println(bex.getMessage());
        } catch (Exception exception) {
            System.out.println("Unknown error");
        }
    }
}
