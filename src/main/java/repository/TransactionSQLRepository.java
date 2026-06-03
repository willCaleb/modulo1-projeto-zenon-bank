package repository;

import enums.EnumTransactionType;
import model.Transaction;
import model.TransactionCustomer;

import java.sql.*;
import java.util.Optional;

public class TransactionSQLRepository implements TransactionRepository {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/transactions_db";
    private static final String DB_USERNAME = "will";
    private static final String DB_PASSWORD = "jose123";

    @Override
    public Optional<Transaction> findByClientName(String clientName, Integer size, String[] dataLines) {

        String sql = "select * from transactions where name_orig = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setString(1, clientName);
            ResultSet rs = ps.executeQuery();

            if(!rs.next()) {
                return Optional.empty();
            }
            Transaction transaction =
                    new Transaction(
                            rs.getInt("step"),
                            EnumTransactionType.valueOf(
                                    rs.getString("type")
                            ),
                            rs.getBigDecimal("amount"),
                            new TransactionCustomer(
                                    rs.getString("name_orig"),
                                    rs.getBigDecimal("old_balance_orig"),
                                    rs.getBigDecimal("new_balance_orig")
                            ),
                            new TransactionCustomer(
                                    rs.getString("name_dest"),
                                    rs.getBigDecimal("old_balance_dest"),
                                    rs.getBigDecimal("new_balance_dest")
                            ),
                            rs.getBoolean("is_fraud"),
                            rs.getBoolean("is_flagged_fraud")
                    );
            return Optional.of(transaction);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Transaction> findByClientName(String clientName, String[] dataLines) {
        return Optional.empty();
    }

    @Override
    public void save(Transaction transaction) {
        String sql = "insert into transactions(step, type, amount, name_orig, old_balance_orig, new_balance_orig, name_dest, old_balance_dest, new_balance_dest, is_fraud, is_flagged_fraud)";
        sql += "values(?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, transaction.step());
            ps.setString(2, transaction.type().name());
            ps.setBigDecimal(3, transaction.amount());
            ps.setString(4, transaction.originCustomer().name());
            ps.setBigDecimal(5, transaction.originCustomer().oldBalance());
            ps.setBigDecimal(6, transaction.originCustomer().newBalance());
            ps.setString(7, transaction.receiverCustomer().name());
            ps.setBigDecimal(8, transaction.receiverCustomer().oldBalance());
            ps.setBigDecimal(9, transaction.receiverCustomer().newBalance());
            ps.setBoolean(10, transaction.isFraud());
            ps.setBoolean(11, transaction.isFlaggedFraud());

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
