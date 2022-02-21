import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;


public class _08_IncreaseMinionsAge {
    public static void main(String[] args) throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", prop);
        Scanner scanner = new Scanner(System.in);
        int[] ints = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();


        StringBuilder str = new StringBuilder("UPDATE minions SET name = LOWER(name), age = (age + 1) WHERE id IN(?);");
        for (int i = 0; i < ints.length -1; i++) {
            int lastIndex = str.lastIndexOf("?");
            str.insert(lastIndex +1, ", ?" );
        }

        PreparedStatement preparedStatement = connection.prepareStatement(str.toString());

        for (int i = 0; i < ints.length; i++) {
            preparedStatement.setInt(i+1, ints[i]);
        }

        preparedStatement.executeUpdate();

        PreparedStatement getNames = connection.prepareStatement("SELECT name, age FROM minions;");
        ResultSet resultSet = getNames.executeQuery();
        while (resultSet.next()){
            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt("age"));
        }

        connection.close();
    }
}
