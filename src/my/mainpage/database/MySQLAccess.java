/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.mainpage.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author Akash PC
 */
public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    /**
     * Checks if a given card-pin pair exists in database
     * @param cardNumber
     * @param pin
     * @return
     * @throws SQLException 
     */
    public boolean userExists(String cardNumber, String pin) throws SQLException{
        int count = 0;
        String queryCheck = "SELECT count(*) from customer WHERE customer.cardnumber = ? AND customer.pinnumber = ?";
            preparedStatement = connect.prepareStatement(queryCheck);
            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, pin);
            resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                count = resultSet.getInt(1);
            }
            
            close();
            
            System.out.println("COUNT:" + count);
            
            if(count == 0)
                return false;
            
            else if(count > 0)
                return true;
            
            return false;
    }
    
    public void createConnection() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/atmdb?"
                            + "user=xuser&password=halua");
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Returns the savings balance of a customer
     * @param cardNumber
     * @return
     * @throws Exception 
     */
    public int getSavings(String cardNumber) throws Exception {
        int amount = 0;
        String queryCheck = "SELECT customer.savingsbal from customer WHERE customer.cardnumber = ?";
        preparedStatement = connect.prepareStatement(queryCheck);
        preparedStatement.setString(1, cardNumber);
        resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            amount = resultSet.getInt(1);
        }
        
        return amount;
    }
    
    public void setSavings(String cardNumber, int balance) throws Exception {
        String queryCheck = "UPDATE customer SET savingsbal = ? WHERE cardnumber = ?";
        preparedStatement = connect.prepareStatement(queryCheck);
        preparedStatement.setString(1, Integer.toString(balance));
        preparedStatement.setString(2, cardNumber);
        preparedStatement.executeUpdate();
        System.out.println(resultSet);
        close();
    }

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        System.out.println("HI");
        // ResultSet is initially before the first data set
        while (resultSet.next()) {
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            String id = resultSet.getString("cardnumber");
            String name = resultSet.getString("pinnumber");
//            String summary = resultSet.getString("summary");
//            Date date = resultSet.getDate("datum");
//            String comment = resultSet.getString("comments");
            System.out.println("Id: " + id);
            System.out.println("name: " + name);
//            System.out.println("summary: " + summary);
//            System.out.println("Date: " + date);
//            System.out.println("Comment: " + comment);
        }
    }
    
    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}
