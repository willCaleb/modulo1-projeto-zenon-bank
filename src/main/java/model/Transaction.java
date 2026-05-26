package model;

import enums.EnumTransactionType;

import java.math.BigDecimal;

public record Transaction(Integer step,
                          EnumTransactionType type,
                          BigDecimal amount,
                          TransactionCustomer customer,
                          TransactionReceiverCustomer receiverCustomer,
                          Integer isFraud,
                          Integer isFlaggedFraud) {

    public Transaction {
        if (step <= 0) throw new IllegalArgumentException("step should be positive " + step);
    }
}
