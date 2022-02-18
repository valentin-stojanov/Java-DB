import java.sql.*;
import java.util.Properties;

public class _02_GetVillainsName {

    public static void main(String[] args) throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", prop);

        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(distinct minion_id) as `count`, v.name" +
                " FROM villains as v" +
                "     JOIN" +
                " minions_villains as mv ON v.id = mv.villain_id" +
                " GROUP BY v.name, mv.villain_id" +
                " HAVING `count` > 15" +
                " ORDER BY `count` DESC;");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            String villainName = resultSet.getString("name");
            String count = resultSet.getString("count");

            System.out.println(count + " " + villainName);
        }
    }
}
