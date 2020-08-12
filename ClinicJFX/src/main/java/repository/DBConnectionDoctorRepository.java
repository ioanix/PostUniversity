package repository;

import domain.Doctor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConnectionDoctorRepository implements IRepository<Doctor>  {

    private DBConnection dbConnection;
    private Connection conn;

    //private QueryRunner dbAccess = new QueryRunner();


    public DBConnectionDoctorRepository() {

        dbConnection = new DBConnection("clinic");
        conn = dbConnection.getConnection();
    }


    @Override
    public void create(Doctor doctor) {

        dbConnection.getConnection();

//        try {
//
//            dbAccess.insert(conn, "INSERT INTO Doctor(firstName, lastName, age) VALUES(? ? ? ?)",
//                            new ScalarHandler<BigDecimal>(), doctor.getFirstName(), doctor.getLastName(), doctor.getAge());
//
//        } catch(Exception e) {}

        String sql = "INSERT INTO Doctor(firstName, lastName, age) VALUES('"+doctor.getFirstName()+"', '"+doctor.getLastName()+"', '"+doctor.getAge()+"')";

        try (Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);

        } catch(SQLException e) {

            e.printStackTrace();
        }

        dbConnection.dbDisconnect();
    }

    @Override
    public Doctor read(int id) {

        String sql = "SELECT * FROM Doctor WHERE id = '"+id+"'";
        dbConnection.getConnection();

        try {

            ResultSet rs = conn.createStatement().executeQuery(sql);

            if(rs.next()) {

                Doctor doctor = new Doctor();
                doctor.setId(rs.getInt(1));
                doctor.setFirstName(rs.getString(2));
                doctor.setLastName(rs.getString(3));
                doctor.setAge(rs.getInt(4));

                return doctor;
            }

        } catch(SQLException e) {

            System.out.println(e.getMessage());
            e.printStackTrace();

        } finally {

            dbConnection.dbDisconnect();
        }

        return null;
    }

    @Override
    public List<Doctor> readAll() {


        List<Doctor> docList = new ArrayList<>();

        dbConnection.getConnection();

        try {

            ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM Doctor");

            while(resultSet.next()) {

                Doctor doctor = new Doctor();
                doctor.setId(resultSet.getInt(1));
                doctor.setFirstName(resultSet.getString(2));
                doctor.setLastName(resultSet.getString(3));
                doctor.setAge(resultSet.getInt(4));

                docList.add(doctor);
            }

            return docList;

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            dbConnection.dbDisconnect();
        }

        return null;

    }

    @Override
    public void update(Doctor doctor) {

        String sql = "UPDATE Doctor SET firstName='"+doctor.getFirstName()+"', " +
                     "lastName='"+doctor.getLastName()+"', age='"+doctor.getAge()+"' WHERE id = "+doctor.getId();

        dbConnection.getConnection();

        try (Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);

        } catch(SQLException e) {

            e.printStackTrace();
        }

        dbConnection.dbDisconnect();

    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM Doctor WHERE id='"+id+"'";

        dbConnection.getConnection();

        try (Statement statement = conn.createStatement()) {

            statement.executeUpdate(sql);

        } catch(SQLException e) {

            e.printStackTrace();
        }

        dbConnection.dbDisconnect();

    }
}
