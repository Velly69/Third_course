package first.lab.quickpaw;

import java.math.BigInteger;

/**
 * http://e-maxx.ru/algo/export_binary_pow
 */

public class QuickPow {
    public static BigInteger paw(BigInteger x, BigInteger y, BigInteger n) {
        // x^0 == 1
        if (n.equals(BigInteger.ZERO)) {
            return BigInteger.ONE;
        }

        // x^1 == a
        if (n.equals(BigInteger.ONE)) {
            return x;
        }
        BigInteger res = BigInteger.ONE;
        while (y.compareTo(BigInteger.ZERO) > 0) {
            if(y.and(BigInteger.ONE).compareTo(BigInteger.ZERO) > 0) {
                res = res.multiply(x);
            }
            x = x.multiply(x);
            y = y.shiftRight(1);
        }
        return res.mod(n);
    }

    public static void main(String[] args) {
        BigInteger res = paw(new BigInteger("18"), new BigInteger("75"), new BigInteger("11"));
        System.out.println(res);
    }
}
