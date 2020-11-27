package repository.dbFileRepositories;

import model.Bike;
import model.BikeType;
import model.Customer;
import model.Sale;
import model.validators.BikeShopException;
import model.validators.SaleValidator;
import model.validators.Validator;
import repository.IRepository;
import repository.paging.Page;
import repository.paging.Pageable;
import repository.paging.PagingAndSortingRepository;
import repository.paging.impl.PageImpl;
import repository.sorting.Sort;
import utils.IOUtil;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaleDbRepository implements PagingAndSortingRepository<Long, Sale> {

    private DbConnection dbConnection;
    private Validator<Sale> saleValidator;

    public SaleDbRepository() {

        this.dbConnection = new DbConnection();
        this.saleValidator = new SaleValidator();
    }

    public SaleDbRepository(Validator<Sale> saleValidator) {

        this.dbConnection = new DbConnection();
        this.saleValidator = saleValidator;
    }

    @Override
    public Optional<Sale> findOne(Long s_id) {

        if (s_id == null) {

            throw new IllegalArgumentException("id must not be null");
        }

        ResultSet resultSet = null;

        String sql = "select s_id, id, name, biketype, price, c_id, firstname, lastname, phone, city, street, number, date from bikes_customers_view where s_id = ?";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setLong(1, s_id);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                Long b_id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BikeType bikeType = BikeType.valueOf(resultSet.getString("biketype").toUpperCase());
                double price = resultSet.getDouble("price");
                Bike bike = new Bike(b_id, name, bikeType, price);

                Long c_id = resultSet.getLong("c_id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastname");
                String phone = resultSet.getString("phone");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String number = resultSet.getString("number");
                Customer customer = new Customer(c_id, firstName, lastName, phone, city, street, number);

                LocalDate date = Date.valueOf(String.valueOf(resultSet.getDate("date"))).toLocalDate();

                Sale sale = new Sale(s_id, bike, customer, date);

                return Optional.of(sale);
            }

        } catch (SQLException ex) {

            throw new BikeShopException("An error occured!");

        } finally {

            if (resultSet != null) {

                IOUtil.close(resultSet);
            }
        }

        return Optional.empty();
    }

    @Override
    public Iterable<Sale> findAll() {

        List<Sale> sales = new ArrayList<>();
        String sql = "select * from bikes_customers_view";

        try (ResultSet resultSet = dbConnection.getResultSet(sql)) {

            while (resultSet.next()) {

                Long b_id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                BikeType bikeType = BikeType.valueOf(resultSet.getString("biketype").toUpperCase());
                double price = resultSet.getDouble("price");
                Bike bike = new Bike(b_id, name, bikeType, price);

                Long c_id = resultSet.getLong("c_id");
                String firstName = resultSet.getString("firstname");
                String lastName = resultSet.getString("lastName");
                String phone = resultSet.getString("phone");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String number = resultSet.getString("number");
                Customer customer = new Customer(c_id, firstName, lastName, phone, city, street, number);

                Long id = resultSet.getLong("s_id");
                LocalDate date = Date.valueOf(String.valueOf(resultSet.getDate("date"))).toLocalDate();

                Sale sale = new Sale(id, bike, customer, date);
                sales.add(sale);
            }

        } catch (SQLException ex) {

            throw new BikeShopException("An error occured!");
        }

        return new ArrayList<>(sales);

    }

    @Override
    public Optional<Sale> save(Sale sale) {

        if (sale == null) {

            throw new IllegalArgumentException("sale must not be null");
        }

        this.saleValidator.validate(sale);

        String sql = "insert into sales (id_bike, id_customer, date) values (?, ?, ?)";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setLong(1, sale.getBike().getId());
            preparedStatement.setLong(2, sale.getCustomer().getId());
            preparedStatement.setDate(3, Date.valueOf(sale.getSaleDate()));

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            throw new BikeShopException("An error occured!");
        }

        return Optional.of(sale);
    }

    @Override
    public Optional<Sale> delete(Long s_id) {

        String sql = "delete from sales where s_id = ?";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setLong(1, s_id);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            throw new BikeShopException("An error occured!");
        }

        return findOne(s_id);
    }

    @Override
    public Optional<Sale> update(Sale sale) {

        if (sale == null) {

            throw new IllegalArgumentException("bike must not be null");
        }

        String sql = "update sales set id_bike = ?, id_customer = ?, date = ? where s_id = ?";

        try (PreparedStatement preparedStatement = dbConnection.getPreparedStatement(sql)) {

            preparedStatement.setLong(1, sale.getBike().getId());
            preparedStatement.setLong(2, sale.getCustomer().getId());
            preparedStatement.setDate(3, Date.valueOf(sale.getSaleDate()));
            preparedStatement.setLong(4, sale.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {

            throw new BikeShopException("An error occured!");
        }

        return Optional.of(sale);
    }

    @Override
    public Page<Sale> findAll(Pageable pageable) {

        return new PageImpl<>(pageable, this);
    }

    @Override
    public Iterable<Sale> sort(Sort<Sale> sort) {

        return sort.getEntities();
    }
}
