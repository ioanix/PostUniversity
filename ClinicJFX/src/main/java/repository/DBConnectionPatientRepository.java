package repository;

import domain.Patient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnectionPatientRepository implements IRepository<Patient> {

    private DBConnection dbConnection;
    private Connection conn;


    public DBConnectionPatientRepository() {

        dbConnection = new DBConnection("clinic");
        conn = dbConnection.getConnection();
    }

    @Override
    public void create(Patient patient) {

        dbConnection.getConnection();

        String sql = "INSERT INTO Patient(patientFirstName, patientLastName, patientAge, consultationReason, phoneNumber) " +
                "VALUES('" + patient.getPatientFirstName() + "', '" + patient.getPatientLastName() + "', '" + patient.getPatientAge() + "', " +
                "'" + patient.getConsultationReason() + "', '" + patient.getPhoneNumber() + "')";

        try (Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }

        dbConnection.dbDisconnect();

    }

    @Override
    public Patient read(int id) {


        String sql = "SELECT * FROM Patient WHERE id = '" + id + "'";
        dbConnection.getConnection();

        try {

            ResultSet rs = conn.createStatement().executeQuery(sql);
            if (rs.next()) {

                Patient patient = new Patient();
                patient.setId(rs.getInt(1));
                patient.setPatientFirstName(rs.getString(2));
                patient.setPatientLastName(rs.getString(3));
                patient.setPatientAge(rs.getInt(4));
                patient.setConsultationReason(rs.getString(5));
                patient.setPhoneNumber(rs.getString(6));

                return patient;
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
    public List<Patient> readAll() {

        List<Patient> patientList = new ArrayList<>();

        dbConnection.getConnection();

        try {

            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Patient");

            while (rs.next()) {

                Patient patient = new Patient();
                patient.setId(rs.getInt(1));
                patient.setPatientFirstName(rs.getString(2));
                patient.setPatientLastName(rs.getString(3));
                patient.setPatientAge(rs.getInt(4));
                patient.setConsultationReason(rs.getString(5));
                patient.setPhoneNumber(rs.getString(6));

                patientList.add(patient);
            }

            return patientList;

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            dbConnection.dbDisconnect();
        }

        return null;
    }

    @Override
    public void update(Patient patient) {

        String sql = "UPDATE Patient SET patientFirstName='" + patient.getPatientFirstName() + "', " +
                "patientLastName='" + patient.getPatientLastName() + "', patientAge='" + patient.getPatientAge() + "', " +
                "consultationReason='" + patient.getConsultationReason() + "', " +
                "phoneNumber='" + patient.getPhoneNumber() + "' WHERE id = " + patient.getId();

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

        String sql = "DELETE FROM Patient WHERE id='" + id + "'";

        dbConnection.getConnection();

        try (Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);

        } catch (SQLException e) {

            e.printStackTrace();
        }

        dbConnection.dbDisconnect();

    }
}
