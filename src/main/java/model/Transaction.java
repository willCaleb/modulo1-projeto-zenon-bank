package model;

import enums.EnumTransactionType;

import java.math.BigDecimal;

public record Transaction(Integer step,
                          EnumTransactionType type,
                          BigDecimal amount,
                          String nameOrig,
                          BigDecimal oldBalanceOrg,
                          BigDecimal newBalanceOrig,
                          String nameDest,
                          BigDecimal OldBalanceDest,
                          BigDecimal newBalanceDest,
                          Integer isFraud,
                          Integer isFlaggedFraud) {
}
