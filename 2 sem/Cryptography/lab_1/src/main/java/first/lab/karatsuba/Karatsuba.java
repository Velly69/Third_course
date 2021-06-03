package first.lab.karatsuba;

import java.math.BigInteger;

/**
 * https://www.youtube.com/watch?v=JCbZayFr9RE
 */

public class Karatsuba {
    public static BigInteger multiply(BigInteger x, BigInteger y) {
        if(x.compareTo(BigInteger.TEN) < 0 || y.compareTo(BigInteger.TEN) < 0) {
            return x.multiply(y);
        }
        int N = Math.min(x.bitLength(), y.bitLength());
        N = N/2;
        BigInteger a = x.shiftRight(N);
        BigInteger b = x.subtract(a.shiftLeft(N));
        BigInteger c = y.shiftRight(N);
        BigInteger d = y.subtract(c.shiftLeft(N));

        BigInteger ac = multiply(a, c); // #1 (ac)
        BigInteger bd = multiply(b, d); // #2 (bd)
        BigInteger abcd = multiply(a.add(b), c.add(d)); // #3 (a+b)(c+d)=(ac + ad + bc + bd)

        return bd.add((abcd.subtract(ac).subtract(bd)).shiftLeft(N)).add(ac.shiftLeft(N*2));
        // bd + (ad + bc) ^ + ac ^^
    }
}
