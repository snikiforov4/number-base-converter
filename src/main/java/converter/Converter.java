package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Converter {

    private static final int PRECISION = 5;

    private final int sourceBase;
    private final int targetBase;

    public Converter(int sourceBase, int targetBase) {
        this.sourceBase = sourceBase;
        this.targetBase = targetBase;
    }

    public String convert(final String number) {
        String result;
        int indexOfDot = number.indexOf('.');
        if (indexOfDot > -1) {
            BigDecimal fractalPart = convertFractionalPartToDecimal(number.substring(indexOfDot + 1), sourceBase);
            String integerResult = new BigInteger(number.substring(0, indexOfDot), sourceBase).toString(targetBase);
            String fractalResult = convertFractionalPartFromDecimal(fractalPart, targetBase);
            result = integerResult + "." + fractalResult;
        } else {
            result = new BigInteger(number, sourceBase).toString(targetBase);
        }
        return result;
    }

    private static BigDecimal convertFractionalPartToDecimal(final String number,
                                                             final int sourceBase) {
        if (sourceBase == 10) {
            return new BigDecimal("0." + number);
        }
        BigDecimal result = BigDecimal.ZERO;
        BigDecimal base = BigDecimal.valueOf(sourceBase);
        for (int i = 0; i < number.length(); i++) {
            BigDecimal numberAtIndex = BigDecimal.valueOf(fromChar(number.charAt(i), sourceBase));
            BigDecimal multiplier = BigDecimal.ONE.divide(base.pow((i + 1)), 10, RoundingMode.CEILING);
            BigDecimal partialResult = numberAtIndex.multiply(multiplier);
            result = result.add(partialResult);
        }
        return result;
    }

    private static int fromChar(char c, int radix) {
        return Character.digit(c, radix);
    }

    private static String convertFractionalPartFromDecimal(final BigDecimal number,
                                                           final int targetBase) {
        StringBuilder result = new StringBuilder();
        BigDecimal base = BigDecimal.valueOf(targetBase);
        BigDecimal remainder = number;
        for (int i = 0; i < PRECISION && !remainder.equals(BigDecimal.ZERO); i++) {
            BigDecimal multiplyResult = remainder.multiply(base);
            int integerPart = multiplyResult.toBigInteger().intValueExact();
            remainder = multiplyResult.remainder(BigDecimal.ONE);
            result.append(toChar(integerPart, targetBase));
        }
        if (result.length() < PRECISION) {
            result.append('0');
        }
        return result.toString();
    }

    private static char toChar(int remainder, int radix) {
        return Character.forDigit(remainder, radix);
    }

}
