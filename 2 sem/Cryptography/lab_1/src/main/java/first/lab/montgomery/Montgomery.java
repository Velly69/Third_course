package first.lab.montgomery;

import first.lab.extendedeuclid.ExtendedEuclid;
import first.lab.karatsuba.Karatsuba;
import java.math.BigInteger;

/**
 * https://www.youtube.com/watch?v=2UmQDKcelBQ
 */

public class Montgomery {
    private static BigInteger r;
    private static BigInteger r1;

    public static BigInteger countR(BigInteger n) {
        BigInteger r = BigInteger.ONE;
        return r.shiftLeft(n.bitLength());
    }

    private static BigInteger multiplicationHelp(BigInteger a, BigInteger b, final BigInteger n) {
        r = countR(n);
        BigInteger a1 = a.shiftLeft(r.bitLength()-1).mod(n); // a' = aR (mod n)
        BigInteger b1 = b.shiftLeft(r.bitLength()-1).mod(n); // b' = bR (mod n)
        BigInteger t = Karatsuba.multiply(a1, b1);
        ExtendedEuclid.Result euclidRes = ExtendedEuclid.extendedEuclid(r, n);
        if(euclidRes.getGcd().compareTo(BigInteger.ONE) != 0) {
            throw new ArithmeticException("N and R are not prime numbers");
        }
        BigInteger n1 = euclidRes.getSecondCoefficient().multiply(BigInteger.valueOf(-1));
        r1 = euclidRes.getFirstCoefficient();
        BigInteger residue = Karatsuba.multiply(t, n1).subtract(Karatsuba.multiply(t, n1)
                .shiftRight(r.bitLength()-1).shiftLeft(r.bitLength()-1));
        BigInteger u = t.add(Karatsuba.multiply(residue, n)).shiftRight(r.bitLength()-1);
        while(u.compareTo(n) >= 0) {
            u = u.subtract(n);
        }
        return u;
    }

    private static BigInteger pawHelp(BigInteger a, BigInteger e, final BigInteger n) {
        r = countR(n);
        BigInteger a1 = a.shiftLeft(r.bitLength()-1).mod(n);
        BigInteger x = BigInteger.ONE.shiftLeft(r.bitLength()-1).mod(n);
        for(int i = e.bitLength()-1; i >= 0; --i){
            x = multiplicationHelp(x, x, n);
            if(e.testBit(i)) {
                x = multiplicationHelp(a1, x, n);
            }
        }
        return multiplicationHelp(x, BigInteger.ONE, n);
    }

    public static BigInteger multiplication(BigInteger a, BigInteger b, BigInteger n) {
        BigInteger u = multiplicationHelp(a, b, n);
        return u.multiply(r1).mod(n);
    }

    public static BigInteger paw(BigInteger a, BigInteger e, BigInteger n) {
        BigInteger u = pawHelp(a, e, n);
        r = countR(n);
        ExtendedEuclid.Result eeRes = ExtendedEuclid.extendedEuclid(r, n);
        if(eeRes.getGcd().compareTo(BigInteger.ONE) != 0) {
            throw new ArithmeticException("N and R are not prime numbers");
        }
        r1 = eeRes.getFirstCoefficient();
        return u.multiply(r1).mod(n);
    }

    public static void main(String[] args) {
        BigInteger a = new BigInteger("18");
        BigInteger b = new BigInteger("75");
        BigInteger n = new BigInteger("11");
        BigInteger res = paw(a, b, n);
        System.out.println(res);
    }
}
