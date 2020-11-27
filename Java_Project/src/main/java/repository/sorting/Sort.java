package repository.sorting;

import model.Bike;
import model.Customer;
import model.Sale;
import repository.dbFileRepositories.BikeDbRepository;
import repository.dbFileRepositories.CustomerDbRepository;
import repository.dbFileRepositories.SaleDbRepository;
import repository.sorting.impl.SortImpl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface Sort<T> {


    static Sort<Bike> bikeBy(String property) {

        try {

            String matchingField = property.equals("id") ? getIdMatchingField() : getDefaultBikeMatchingField(property);

            SortImpl<Long, Bike> sort = new SortImpl<>(new BikeDbRepository(), property);

            List<Bike> bikeList = StreamSupport.stream(sort.getEntities().spliterator(), false)
                    .sorted(new DynamicComparator(matchingField))
                    .collect(Collectors.toList());

            sort.setEntities(bikeList);

            return sort;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    static String getDefaultBikeMatchingField(String property) throws Exception {

        Class<?> bikeClass = Class.forName("model.Bike");

        Field[] fields = bikeClass.getDeclaredFields();

        return Arrays.stream(fields)
                .filter(f -> f.getName().equals(property))
                .findAny()
                .map(Field::getName)
                .orElseThrow(() -> new NoSuchElementException("Couldn't find any field with this name"));

    }

    static Sort<Customer> customerBy(String property) {

        try {

            String matchingField = property.equals("id") ? getIdMatchingField() : getDefaultCustomerMatchingField(property);

            SortImpl<Long, Customer> sort = new SortImpl<>(new CustomerDbRepository(), property);

            List<Customer> customerList = StreamSupport.stream(sort.getEntities().spliterator(), false)
                    .sorted(new DynamicComparator(matchingField))
                    .collect(Collectors.toList());

            sort.setEntities(customerList);

            return sort;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    static String getDefaultCustomerMatchingField(String property) throws ClassNotFoundException {

        Class<?> customerClass = Class.forName("model.Customer");

        Field[] fields = customerClass.getDeclaredFields();

        return Arrays.stream(fields)
                .filter(f -> f.getName().equals(property))
                .findAny()
                .map(Field::getName)
                .orElseThrow(() -> new NoSuchElementException("Couldn't find any field with this name"));

    }

    static Sort<Sale> saleBy(String property) {

        try {

            String matchingField = property.equals("id") ? getIdMatchingField() : getDefaultSaleMatchingField(property);

            SortImpl<Long, Sale> sort = new SortImpl<>(new SaleDbRepository(), property);

            List<Sale> saleList = StreamSupport.stream(sort.getEntities().spliterator(), false)
                    .sorted(new DynamicComparator(matchingField))
                    .collect(Collectors.toList());

            sort.setEntities(saleList);

            return sort;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    private static String getDefaultSaleMatchingField(String property) throws ClassNotFoundException {

        Class<?> saleClass = Class.forName("model.Sale");

        Field[] fields = saleClass.getDeclaredFields();

        return Arrays.stream(fields)
                .filter(f -> f.getName().equals(property))
                .findAny()
                .map(Field::getName)
                .orElseThrow(() -> new NoSuchElementException("Couldn't find any field with this name"));
    }

    private static String getIdMatchingField() throws ClassNotFoundException, NoSuchFieldException {

        String matchingField;

        Class<?> saleClass = Class.forName("model.Sale");

        Class<?> entityClass = saleClass.getSuperclass();

        matchingField = entityClass.getDeclaredField("id").getName();

        return matchingField;
    }

    void setEntities(Iterable<T> entities);

    Iterable<T> getEntities();

    Sort<T> and(Sort<T> sort);

    Sort<T> ascending();

    Sort<T> descending();
}
