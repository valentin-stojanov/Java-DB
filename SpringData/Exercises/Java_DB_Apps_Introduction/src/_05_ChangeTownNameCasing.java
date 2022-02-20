import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class _05_ChangeTownNameCasing {
    public static void main(String[] args) throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", prop);
        Scanner scanner = new Scanner(System.in);

        String country = scanner.nextLine();

        PreparedStatement preparedStatement = connection.prepareStatement("""
                update towns
                set name = UPPER(name)
                where country = ?;
                """);
        preparedStatement.setString(1, country);
        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows == 0){
            System.out.println("No town names were affected.");
            return;
        }

        PreparedStatement selectAllCities = connection.prepareStatement("SELECT name FROM towns WHERE country = ?");
        selectAllCities.setString(1, country);

        ResultSet resultSet = selectAllCities.executeQuery();
        String[] towns = new String[affectedRows];

        for (int i = 0; resultSet.next(); i++) {
            towns[i] = resultSet.getString("name");
        }

        System.out.printf("%d town names were affected.%n", affectedRows);
        System.out.println("[" + String.join(", ", towns) + "]");

    }
}
