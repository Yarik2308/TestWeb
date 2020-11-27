import java.net.URL;
import java.sql.*;

/**
 * Dec  5, 2012
 * Dec 24, 2015
 *
 * @author Kovshov
 */
public class TestDB {

    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new org.sqlite.JDBC());

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL resource = classloader.getResource("firstDB.s3db");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Resource = " + resource.toString());

        Connection conn = DriverManager.getConnection("jdbc:sqlite:"+resource.toString());
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM number");
        System.out.println("*************************************************");
        while (rs.next()) {
            String sId = rs.getString("id");
            String sName = rs.getString("name");
            System.out.println(" " + sId + " " + sName);
        }
        System.out.println("*************************************************");
    }
}
