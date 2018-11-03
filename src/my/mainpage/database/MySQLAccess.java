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
 * @author Akash Kumar
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
    
    public void addRecord(String cardNumber, String pin) throws Exception {
        String query = " insert into customer (cardnumber, pinnumber, savingsbal, currentbal)"
        + " values (?, ?, ?, ?)";

        // create the mysql insert preparedstatement
        preparedStatement = connect.prepareStatement(query);
        preparedStatement.setString (1, cardNumber);
        preparedStatement.setString (2, pin);
        preparedStatement.setInt (3, 10000);
        preparedStatement.setInt (4, 0);

        // execute the preparedstatement
        preparedStatement.execute();

        close();
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
    
    /**
     * Returns the savings balance of a customer
     * @param cardNumber
     * @return
     * @throws Exception 
     */
    public int getCurrent(String cardNumber) throws Exception {
        int amount = 0;
        String queryCheck = "SELECT customer.currentbal from customer WHERE customer.cardnumber = ?";
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
        close();
    }
    
    public void setCurrent(String cardNumber, int balance) throws Exception {
        String queryCheck = "UPDATE customer SET currentbal = ? WHERE cardnumber = ?";
        preparedStatement = connect.prepareStatement(queryCheck);
        preparedStatement.setString(1, Integer.toString(balance));
        preparedStatement.setString(2, cardNumber);
        preparedStatement.executeUpdate();
        close();
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
