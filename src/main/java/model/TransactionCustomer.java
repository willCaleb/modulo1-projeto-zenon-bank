package model;

import java.math.BigDecimal;

public record TransactionCustomer(String nameOrig,
                                  BigDecimal oldBalanceOrg,
                                  BigDecimal newBalanceOrig) {
    public TransactionCustomer {
        if(oldBalanceOrg.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("oldBalance should be positive: " + oldBalanceOrg);
        if(newBalanceOrig.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("newBalanceOrigin should be positive: " + newBalanceOrig);
    }
}
