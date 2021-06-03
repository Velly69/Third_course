package first.lab.extendedeuclid;

import java.math.BigInteger;


/**
 * https://www.e-olymp.com/uk/blogs/posts/18
 */

public class ExtendedEuclid {
    public static class Result {
        private BigInteger firstCoefficient;
        private BigInteger secondCoefficient;
        private BigInteger gcd;

        public Result(BigInteger residue, BigInteger firstCoefficient, BigInteger secondCoefficient) {
            this.gcd = residue;
            this.firstCoefficient = firstCoefficient;
            this.secondCoefficient = secondCoefficient;
        }

        public BigInteger getFirstCoefficient() {
            return firstCoefficient;
        }

        public BigInteger getSecondCoefficient() {
            return secondCoefficient;
        }

        public BigInteger getGcd() {
            return gcd;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "firstCoef=" + firstCoefficient +
                    ", secondCoef=" + secondCoefficient +
                    ", gcd=" + gcd +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Result result = (Result) o;
            return firstCoefficient.equals(result.firstCoefficient) &&
                    secondCoefficient.equals(result.secondCoefficient) &&
                    gcd.equals(result.gcd);
        }
    }

    public static Result extendedEuclid(BigInteger a, BigInteger b) {
        if (b.compareTo(BigInteger.ZERO) == 0) {
            return new Result(a, BigInteger.ONE, BigInteger.ZERO);
        }
        Result r = extendedEuclid(b, a.mod(b));
        BigInteger x = r.getSecondCoefficient();
        BigInteger y = r.getFirstCoefficient().subtract(a.divide(b).multiply(x));
        return new Result(r.getGcd(), x, y);
    }

    public static void main(String[] args) {
        Result res = extendedEuclid(new BigInteger("23"), new BigInteger("47"));
        System.out.println(res);
    }
}
