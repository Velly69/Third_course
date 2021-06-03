package first.lab.millerrabin;

import java.math.BigInteger;
import static first.lab.fermat.Fermat.BigIntegerRandom;


/**
 * https://ru.wikipedia.org/wiki/%D0%A2%D0%B5%D1%81%D1%82_%D0%9C%D0%B8%D0%BB%D0%BB%D0%B5%D1%80%D0%B0_%E2%80%94_%D0%A0%D0%B0%D0%B1%D0%B8%D0%BD%D0%B0
 */
public class MillerRabin {
    private static final BigInteger TWO = BigInteger.valueOf(2);

    public static boolean isPrime(BigInteger n, int iteration) {
        if (n.compareTo(BigInteger.ZERO) == 0 || n.compareTo(BigInteger.ONE) == 0) {
            return false;
        }
        if (n.compareTo(TWO) == 0) {
            return true;
        }
        if (n.mod(TWO).compareTo(BigInteger.ZERO) == 0){
            return false;
        }

        BigInteger t = n.subtract(BigInteger.ONE);
        int s = 0;
        while (t.mod(TWO).compareTo(BigInteger.ZERO) == 0){
            t = t.divide(TWO);
            s++;
        }

        for(int i = 0; i < iteration; ++i) {
            BigInteger a = BigIntegerRandom(TWO, n.subtract(TWO));
            BigInteger x = a.modPow(t, n);
            if(x.compareTo(BigInteger.ONE) == 0 || x.compareTo(n.subtract(BigInteger.ONE)) == 0) {
                continue;
            }
            boolean nextIteration = false;
            for(int j = 0; j < s-1; ++j) {
                x = x.multiply(x).mod(n);
                if(x.compareTo(BigInteger.ONE) == 0) {
                    return false;
                }
                if(x.compareTo(n.subtract(BigInteger.ONE)) == 0) {
                    nextIteration = true;
                    break;
                }
            }
            if(!nextIteration) {
                return false;
            }
        }
        return true;
    }
}
