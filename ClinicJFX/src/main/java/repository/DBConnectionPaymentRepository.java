package repository;

import domain.Payment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnectionPaymentRepository implements IRepository<Payment> {

    private DBConnection dbConnection;
    private Connection conn;


    public DBConnectionPaymentRepository() {

        dbConnection = new DBConnection("clinic");
        conn = dbConnection.getConnection();
    }


    @Override
    public void create(Payment payment) {

        dbConnection.getConnection();

        String sql = "INSERT INTO Payment(patientId, doctorId, patientCardNumber, date, time, price) " +
                "VALUES('" + payment.getPatientId() + "', '" + payment.getDoctorId() + "', '" + payment.getPatientCardNumber() + "', " +
                "'" + payment.getDate() + "', '" + payment.getTime() + "', '" + payment.getPrice() + "')";

        try (Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }

        dbConnection.dbDisconnect();

    }

    @Override
    public Payment read(int id) {

        String sql = "SELECT * FROM Payment WHERE id = '" + id + "'";
        dbConnection.getConnection();

        try {

            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {

                Payment payment = new Payment();
                payment.setId(rs.getInt(1));
                payment.setPatientId(rs.getInt(2));
                payment.setDoctorId(rs.getInt(3));
                payment.setPatientCardNumber(rs.getInt(4));
                payment.setDate(rs.getString(5));
                payment.setTime(rs.getString(6));
                payment.setPrice(rs.getDouble(7));

                return payment;
            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();

        } finally {

            dbConnection.dbDisconnect();
        }

        return null;
    }

    @Override
    public List<Payment> readAll() {

        List<Payment> paymentList = new ArrayList<>();

        dbConnection.getConnection();

        try {

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Payment");

            while (rs.next()) {

                Payment payment = new Payment();
                payment.setId(rs.getInt(1));
                payment.setPatientId(rs.getInt(2));
                payment.setDoctorId(rs.getInt(3));
                payment.setPatientCardNumber(rs.getInt(4));
                payment.setDate(rs.getString(5));
                payment.setTime(rs.getString(6));
                payment.setPrice(rs.getDouble(7));

                paymentList.add(payment);
            }

            return paymentList;

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            dbConnection.dbDisconnect();
        }

        return null;
    }

    @Override
    public void update(Payment payment) {

        String sql = "UPDATE Payment SET patientId='" + payment.getPatientId() + "', " +
                "doctorId='" + payment.getDoctorId() + "', patientCardNumber='" + payment.getPatientCardNumber() + "', " +
                "date='" + payment.getDate() + "', time='" + payment.getTime() + "', price='" + payment.getPrice() + "' WHERE id = " + payment.getId();

        dbConnection.getConnection();

        try (Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }

        dbConnection.dbDisconnect();

    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM Payment WHERE id='" + id + "'";

        dbConnection.getConnection();

        try (Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }

        dbConnection.dbDisconnect();

    }
}
