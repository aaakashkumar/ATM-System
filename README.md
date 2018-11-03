# ATM System

### Requirements

  - JDK 1.8
  - [MySQL](https://dev.mysql.com/downloads/mysql/)
  - [MySQL Connector/J 8.0.13](https://mvnrepository.com/artifact/mysql/mysql-connector-java/8.0.13)

### Instructions

  - Create the required database, table, and user
  - Add MySQL Connector/J to the classpath
  - Compile all .java files and run ATM.java

### Database
  - Create database `atmdb`
  - Create table `customer`
  - `customer` structure:
  
    | Column | Type|
    | ---------- | ---------- |
    | cardnumber | varchar(16) PK |
    | pinnumber  | varchar(4) |
    | savingsbal | int(11) |
    | currentbal | int(11) |
  - Create user:
    ```SQL
       CREATE USER 'xuser'@'localhost' IDENTIFIED BY 'halua';
    ```

### Directory Structure
```
Root folder
│   README.md
│
├───atm
│────ATM.java
│
└───my
    └───mainpage
        │   Admin.form
        │   Admin.java
        │   AdminView.form
        │   AdminView.java
        │   Balance.form
        │   Balance.java
        │   Deposit.form
        │   Deposit.java
        │   DepositCurrent.form
        │   DepositCurrent.java
        │   DepositSavings.form
        │   DepositSavings.java
        │   Help.form
        │   Help.java
        │   Login.form
        │   Login.java
        │   MainPageUI.form
        │   MainPageUI.java
        │   Transaction.form
        │   Transaction.java
        │   Withdrawal.form
        │   Withdrawal.java
        │   WithdrawalCurrent.form
        │   WithdrawalCurrent.java
        │   WithdrawalSavings.form
        │   WithdrawalSavings.java
        │
        └───database
                MySQLAccess.java
```
