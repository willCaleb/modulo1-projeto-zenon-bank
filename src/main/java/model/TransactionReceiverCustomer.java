package model;

import java.math.BigDecimal;

public record TransactionReceiverCustomer(String nameDest,
                                          BigDecimal OldBalanceDest,
                                          BigDecimal newBalanceDest) {

    public TransactionReceiverCustomer {
        if (OldBalanceDest.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("oldBalanceDest should be positive: " + OldBalanceDest);
        if (newBalanceDest.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("newBalanceDest should be positive: " + newBalanceDest);
    }
}
