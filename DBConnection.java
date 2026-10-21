import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Shared helper. Each member edits PASSWORD locally - do NOT commit your real password.
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/student_system";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password_here";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
