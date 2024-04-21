package org.example;

import java.sql.*;

public class TransactionAtomicity {

    public static void main(String[] args) throws Exception {

        int accountFromId = 3;
        int accountToId = 4;
        double transferAmount = 100.0;

        // Atomic Transaction
        try {
            System.out.println("Atomic Transaction: ");
            transfer(accountFromId, accountToId, transferAmount, false);
        } catch (SQLException e) {
            System.err.println("Error during transfer 1: " + e.getMessage());
        }

        // Non - Atomic Transaction
        try {
            System.out.println("Non-Atomic Transaction: ");
            transfer(accountFromId, accountToId, transferAmount, true);
        } catch (SQLException e) {
            System.err.println("Error during transfer 2: " + e.getMessage());
        }
    }

    public static void transfer(int accountFromId, int accountToId, double transferAmount, boolean autoCommit) throws SQLException {
        Connection connection = null;
        PreparedStatement debitStmt = null;
        PreparedStatement creditStmt = null;

        try {
            connection = DriverManager.getConnection(
                    System.getenv("DB_CONNECTION_STRING"),
                    System.getenv("DB_USERNAME"),
                    System.getenv("DB_PASSWORD"));
            connection.setAutoCommit(autoCommit);

            // Debit the source account
            debitStmt = connection.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE id = ?");
            debitStmt.setDouble(1, transferAmount);
            debitStmt.setInt(2, accountFromId);
            int rowsUpdatedDebit = debitStmt.executeUpdate();

            // Credit the destination account
            creditStmt = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE id = ?");
            creditStmt.setDouble(1, transferAmount);
            creditStmt.setInt(2, accountToId);
            int rowsUpdatedCredit = creditStmt.executeUpdate();

            if (rowsUpdatedDebit != 1 || rowsUpdatedCredit != 1) {
                throw new SQLException("Transfer failed: Accounts might not exist or insufficient funds.");
            }

            // Commit the transaction if both updates successful
            connection.commit();
            System.out.println("Transfer successful!");

        } catch (SQLException e) {
            // Rollback the transaction if any exception occurs
            if (connection != null && !autoCommit) {
                 connection.rollback();
            }
            throw e; // Re-throw the exception for handling

        } finally {
            // Close resources regardless of success or failure
            displayBalance(accountFromId, connection);
            displayBalance(accountToId, connection);
            if (debitStmt != null) {
                debitStmt.close();
            }
            if (creditStmt != null) {
                creditStmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    private static void displayBalance(int accountId, Connection connection) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            stmt = connection.prepareStatement("SELECT balance FROM accounts WHERE id = ?");
            stmt.setInt(1, accountId); // Set the account ID parameter
            rs = stmt.executeQuery();

            // Check if a record is found for the account ID
            if (rs.next()) {
                double balance = rs.getDouble("balance"); // Extract the balance value
                System.out.println("Balance for account id " + accountId + " is = " + balance);
            } else {
                System.out.println("Account not found for ID: " + accountId);
            }
        } catch (SQLException e) {
            System.out.println("Exception occurred while getting balance of account " + accountId + " " + e.getMessage());
        }

    }
}

