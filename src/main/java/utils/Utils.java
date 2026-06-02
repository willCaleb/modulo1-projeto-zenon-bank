package utils;

import java.math.BigDecimal;

public class Utils {

    public static BigDecimal convertStringToBigDecimalWithValidationNotNull(String strValue, String fieldName) {
        if (strValue == null || strValue.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " shoud not be empty");
        }
        return new BigDecimal(strValue);
    }

}
