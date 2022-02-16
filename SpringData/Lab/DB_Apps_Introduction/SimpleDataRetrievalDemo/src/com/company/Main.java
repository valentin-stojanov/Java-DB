package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
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
                .getConnection("jdbc:mysql://localhost:3306/soft_uni", props);

        //Create DB
        System.out.printf("Do you want to create DB soft_uni?%n <enter> y for 'yes' or pres <enter> for 'no'");
        String answerSoftUni = sc.nextLine();
        if (answerSoftUni.equals("y")){
            importSQL(connection, new FileInputStream(new File("./src/DB/soft_uni_database.sql")));
        }

/*
        System.out.printf("Do you want to create DB diablo_database? %n <enter> y for 'yes' or pres <enter> for 'no'");
        String answerDiablo = sc.nextLine();
        if (answerDiablo.equals("y")){
            importSQL(connection, new FileInputStream(new File("./src/DB/diablo_database.sql")));
        }
        boolean useStm = connection.createStatement().execute("USE soft_uni;");
*/

        PreparedStatement stmt =
                connection.prepareStatement("SELECT * FROM employees WHERE salary > ?");

        System.out.println("Input salary filter (salary higher than):");
        String salary = sc.nextLine();
        stmt.setDouble(1, Double.parseDouble(salary));
        ResultSet rs = stmt.executeQuery();

        while(rs.next()){
            System.out.println(rs.getString("first_name") + " " + rs.getString("last_name"));
        }
        connection.close();
    }

    public static void importSQL(Connection conn, InputStream in) throws SQLException
    {
        //https://stackoverflow.com/questions/1497569/how-to-execute-sql-script-file-using-jdbc
        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\r)?\n)|(--\n)");
        Statement st = null;
        try
        {
            st = conn.createStatement();
            while (s.hasNext())
            {
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/"))
                {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }

                if (line.trim().length() > 0)
                {
                    st.execute(line);
                }
            }
        }
        finally
        {
            if (st != null) st.close();
        }
    }

}