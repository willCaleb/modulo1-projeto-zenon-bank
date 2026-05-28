package model;

import java.math.BigDecimal;

public record TransactionCustomer(String name,
                                  BigDecimal oldBalance,
                                  BigDecimal newBalance) {
    public TransactionCustomer {
        if(oldBalance.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("oldBalance should be positive: " + oldBalance);
        if(newBalance.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("newBalanceOrigin should be positive: " + newBalance);
    }
}
