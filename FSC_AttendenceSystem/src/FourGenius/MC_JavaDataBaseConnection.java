package FourGenius;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 @author : deepal_suranga
 */
public class MC_JavaDataBaseConnection {

    public MC_JavaDataBaseConnection() {
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    private static String HOST = null;
    private static String DATABASE = null;
    private static String USER = null;
    private static String PASS = null;
    private static String PORT = null;

    //  Database credentials
    // public static void main(String[] args) {
    public static Connection myConnection() {

        //////////////////////////////////////////////////////////////////////
        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("src\\FourGenius\\db_pro.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("prot"));
            System.out.println(prop.getProperty("dbname"));
            System.out.println(prop.getProperty("username"));
            System.out.println(prop.getProperty("host"));

            HOST = prop.getProperty("host");
            PORT = prop.getProperty("prot");
            DATABASE = prop.getProperty("dbname");
            USER = prop.getProperty("username");
            PASS = prop.getProperty("password");

        } catch (IOException ex1) {
            JOptionPane.showMessageDialog(null, "ex1: " + ex1);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "e: " + e);
                }
            }
        }

        ////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;

        System.out.println("-------- MySQL JDBC Connection ------------");

        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex2) {
            System.out.println("Where is your MySQL JDBC Driver?");
            JOptionPane.showMessageDialog(null, "ex2: " + ex2);
            return null;
        }

        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException ex3) {
            System.out.println("Connection Failed! Check output console");
            JOptionPane.showMessageDialog(null, "Connection Failed!Please Check Your Internet Connection");
            JOptionPane.showMessageDialog(null, "ex3: " + ex3);
            return connection;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
            JOptionPane.showMessageDialog(null, "Failed to make connection!");
        }
        return connection;
    }

    public static String add_data_NoColumns(String table_name, String dataWithComa) {
        try {
            Statement statement = MC_JavaDataBaseConnection.myConnection().createStatement();
            String querySql = "INSERT INTO " + table_name + " VALUES (" + dataWithComa + ")".trim();
            statement.executeUpdate(querySql);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "MC_database:add_data_NoColumns::" + ex);
        }
        return null;
    }

    public static String add_data_WithColumns(String table_name, String ColumnsWithComa, String dataWithComa) {
        try {
            Statement statement = MC_JavaDataBaseConnection.myConnection().createStatement();
            String querySql = "INSERT INTO " + table_name + "(" + ColumnsWithComa + ") VALUES (" + dataWithComa + ")".trim();
            //System.out.println("Query is: "+querySql);
            statement.executeUpdate(querySql);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "MC_database:add_data_WithColumns::" + ex);
        }
        return null;
    }

    public static void update_data(String SqlQuery) {

        try {
            Statement statement=MC_JavaDataBaseConnection.myConnection().createStatement();
            statement.executeUpdate(SqlQuery);
        } catch (SQLException ex) {
            Logger.getLogger(MC_JavaDataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

//    public static void main(String[] args) {
//        MC_JavaDataBaseConnection.add_data_WithColumns("test","id,fname,lname,age", "5,'deeA','surA',10");
//    }
}
