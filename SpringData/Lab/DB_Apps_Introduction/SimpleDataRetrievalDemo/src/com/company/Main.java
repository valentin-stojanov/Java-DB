package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username default (root): ");
        String user = sc.nextLine();
        user = user.equals("") ? "root" : user;
        System.out.println();

        System.out.print("Enter password default (empty):");
        String password = sc.nextLine().trim();
        System.out.println();

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/", props);
//                .getConnection("jdbc:mysql://192.168.0.2:3306/", props);  //For LAN connection.

//https://stackoverflow.com/questions/19101243/error-1130-hy000-host-is-not-allowed-to-connect-to-this-mysql-server
//       IF--> ERROR 1130 (HY000): Host '' is not allowed to connect to this MySQL server

//        CREATE USER 'root'@'ip_address' IDENTIFIED BY 'some_pass';
//        GRANT ALL PRIVILEGES ON *.* TO 'root'@'ip_address';


        boolean useStm = false;
        long time = 0;
        try {
            useStm = connection.createStatement().execute("USE soft_uni;");
//            useStm = connection.createStatement().execute("USE diablo_database;");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Start creating DB soft_uni...");
            long now = new Date().getTime();
            importSQL(connection, new FileInputStream(new File("./src/DB/soft_uni_database.sql")));
//            importSQL(connection, new FileInputStream(new File("./src/DB/diablo_database.sql")));
            long after = new Date().getTime();
            time = after - now;
            useStm = true;
        }

        if (useStm) {
            System.out.printf("Database was created successfully for %dms (~%ds)%n", time, time / 1000);
        }

        PreparedStatement stmt =
                connection.prepareStatement("SELECT * FROM employees WHERE salary > ?");

        System.out.println("Input salary filter (salary higher than):");
        String salary = sc.nextLine();
        stmt.setDouble(1, Double.parseDouble(salary));
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
        }
        connection.close();
    }

    public static void importSQL(Connection conn, InputStream in) throws SQLException {
        //https://stackoverflow.com/questions/1497569/how-to-execute-sql-script-file-using-jdbc
        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\r)?\n)|(--\n)");
        Statement st = null;
        try {
            st = conn.createStatement();
            while (s.hasNext()) {
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/")) {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }

                if (line.trim().length() > 0) {
                    st.execute(line);
                }
            }
        } finally {
            if (st != null) st.close();
        }
    }

}