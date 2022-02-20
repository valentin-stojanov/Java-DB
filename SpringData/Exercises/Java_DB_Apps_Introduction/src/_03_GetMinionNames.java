import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.SimpleTimeZone;

public class _03_GetMinionNames {
    public static void main(String[] args) throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", prop);

        PreparedStatement statement = connection.prepareStatement("""
                            select v.name as villain_name, m.name as minion_name, m.age
                            from villains as v
                            join minions_villains as mv
                            on v.id = mv.villain_id
                            join minions as m
                            on m.id = mv.minion_id
                            where v.id = ?;
                """);

        Scanner scanner = new Scanner(System.in);
        int villainId = Integer.parseInt(scanner.nextLine());

        statement.setInt(1, villainId);
        ResultSet resultSet = statement.executeQuery();

        StringBuilder str = new StringBuilder();

        for (int i = 1; resultSet.next(); i++) {
            if (i == 1) {
                str.append(String.format("Villain: %s%n%d %s %d%n", resultSet.getString("villain_name"), i, resultSet.getString("minion_name"), resultSet.getInt("age")));
            } else {
                str.append(String.format("%d %s %d%n", i, resultSet.getString("minion_name"), resultSet.getInt("age")));
            }
        }
        connection.close();

        if (str.isEmpty()){
            System.out.printf("No villain with ID %d exists in the database.", villainId);
            return;
        }

        System.out.println(str.toString().trim());
    }
}
