import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class _09_IncreaseAgeStoredProcedure {
    public static void main(String[] args) throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", prop);
        Scanner scanner = new Scanner(System.in);

        PreparedStatement createStoredProcedure_usp_get_older = connection.prepareStatement("""
                CREATE PROCEDURE usp_get_older(minion_id INT)
                BEGIN
                UPDATE minions
                SET age = age + 1
                WHERE id = minion_id;
                END
                """);
        PreparedStatement createStoredProcedure_usp_get_name_age = connection.prepareStatement("""
                CREATE PROCEDURE usp_get_name_age(minion_id INT)
                BEGIN
                select name, age
                from minions
                where id = minion_id;
                END
                """);

        boolean usp_get_older_created = false;
        boolean usp_get_name_age_created = false;

        try {
            usp_get_older_created = createStoredProcedure_usp_get_older.execute();
            System.out.println("Procedure usp_get_older was created.");

            usp_get_name_age_created = createStoredProcedure_usp_get_name_age.execute();
            System.out.println("Procedure usp_get_name_age was created.");
        } catch (SQLException e) {
//            e.printStackTrace();
        }

        System.out.println("Enter minion id:");
        int minion_id = Integer.parseInt(scanner.nextLine());

        CallableStatement usp_get_older = connection.prepareCall("CALL usp_get_older(?)");
        usp_get_older.setInt(1, minion_id);
        usp_get_older.execute();

        CallableStatement usp_get_name_age = connection.prepareCall("CALL usp_get_name_age(?)");
        usp_get_name_age.setInt(1, minion_id);
        ResultSet resultSet = usp_get_name_age.executeQuery();
        resultSet.next();
        String minionName = resultSet.getString("name");
        int minionAge = resultSet.getInt("age");

        System.out.println(minionName + " " + minionAge);

        connection.close();
    }
}
