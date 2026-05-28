package model;

import enums.EnumTransactionType;

import java.math.BigDecimal;

public record Transaction(Integer step,
                          EnumTransactionType type,
                          BigDecimal amount,
                          TransactionCustomer originCustomer,
                          TransactionCustomer receiverCustomer,
                          boolean isFraud,
                          boolean isFlaggedFraud) {

    public Transaction {
        if (step <= 0) throw new IllegalArgumentException("step should be positive " + step);
    }
}
