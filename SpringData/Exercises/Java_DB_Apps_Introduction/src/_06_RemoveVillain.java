import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class _06_RemoveVillain {
    public static void main(String[] args) throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", prop);
        connection.setAutoCommit(false);

        Scanner scanner = new Scanner(System.in);

        int villainId = Integer.parseInt(scanner.nextLine());

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from minions_villains where villain_id = ?;");
            preparedStatement.setInt(1, villainId);

            int deletedRows = preparedStatement.executeUpdate();

            PreparedStatement getVillainName = connection.prepareStatement("SELECT name FROM villains WHERE id = ?;");
            getVillainName.setInt(1, villainId);
            ResultSet villainNameSet = getVillainName.executeQuery();
            villainNameSet.next();
            String villainName = villainNameSet.getString("name");

            connection.commit();

            System.out.printf("%s was deleted%n", villainName);
            System.out.printf("%d minions released", deletedRows);
        } catch (SQLException e) {
//            e.printStackTrace();
            connection.rollback();
            System.out.println("No such villain was found");
        } finally {
            connection.close();
        }

    }
}
