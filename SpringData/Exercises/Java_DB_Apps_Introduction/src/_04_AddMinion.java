import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class _04_AddMinion {
    public static void main(String[] args) throws SQLException {
        Properties prop = new Properties();
        prop.setProperty("user", "root");
        prop.setProperty("password", "");

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", prop);
        Scanner scanner = new Scanner(System.in);

//        Minion: Robert 14 Berlin
//        Villain: Gru
        String[] minionInfo = scanner.nextLine().split("\\s+");
        String minionName = minionInfo[1];
        int minionAge = Integer.parseInt(minionInfo[2]);
        String minionTown = minionInfo[3];
        String villainName = scanner.nextLine().split("\\s+")[1];

        long townID = -1;

        PreparedStatement townName = connection.prepareStatement("select id from towns where name = ?;");
        townName.setString(1, minionTown);
        ResultSet resultSet = townName.executeQuery();

        if (!resultSet.next()) {
            PreparedStatement addTown = connection.prepareStatement("INSERT INTO towns SET name = ?;", new String[]{"id"});
            addTown.setString(1, minionTown);
            addTown.executeUpdate();

            ResultSet generatedKeys = addTown.getGeneratedKeys();
            generatedKeys.next();
            townID = generatedKeys.getLong(1);

            System.out.printf("Town %s was added to the database.%n", minionTown);
        } else {
            townID = resultSet.getLong("id");
        }

        PreparedStatement addMinion = connection.prepareStatement("INSERT INTO minions(name, age, town_id) values(?, ?, ?);", new String[]{"id"});
        addMinion.setString(1, minionName);
        addMinion.setInt(2, minionAge);
        addMinion.setLong(3, townID);
        addMinion.executeUpdate();

        ResultSet generatedKeys = addMinion.getGeneratedKeys();
        generatedKeys.next();
        long minionId = generatedKeys.getLong(1);

        long villainId = -1;

        PreparedStatement villainNameQuery = connection.prepareStatement("SELECT name FROM villains WHERE name = ?;");
        villainNameQuery.setString(1, villainName);
        ResultSet resultSetVillain = villainNameQuery.executeQuery();

        if (!resultSetVillain.next()) {
            PreparedStatement addVillain = connection.prepareStatement("INSERT INTO villains(name, evilness_factor) values(?, ?);", new String[]{"id"});
            addVillain.setString(1, villainName);
            addVillain.setString(2, "evil");
            addVillain.executeUpdate();

            ResultSet villainGeneratedKeys = addVillain.getGeneratedKeys();
            villainGeneratedKeys.next();
            villainId = villainGeneratedKeys.getLong(1);

            System.out.printf("Villain %s was added to the database.%n", villainName);
        } else {
            villainId = resultSet.getLong("id");
        }

        PreparedStatement minnions_villainsInsertQuery = connection.prepareStatement("INSERT INTO minions_villains(minion_id, villain_id) values(?, ?);");
        minnions_villainsInsertQuery.setLong(1, minionId);
        minnions_villainsInsertQuery.setLong(2, villainId);
        minnions_villainsInsertQuery.executeUpdate();

        System.out.printf("Successfully added %s to be minion of %s.", minionName, villainName);

        connection.close();

    }
}
