package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IOUtil {

    public static void close(ResultSet resultSet) {

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
