package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModificatedNewton implements Method{
    public void solve(double first, double second) {
        modificated(first, second);
    }

    private void modificated(double first, double second) {
        List<Integer> function = new ArrayList<>(Arrays.asList(1, -3, -17, 22));
        List<Integer> derivativeFirst = new ArrayList<>(Arrays.asList(3, -6, -17));

        double x0 = second;
        double e = Math.pow(10, -4);
        double xn = x0;
        int counter = 0;
        double fDerivateZero = calculateDerivate(derivativeFirst, xn); //x0 - first
        double xn1 = x0 - calculate(function, x0) / fDerivateZero;
        for (; Math.abs(xn1-xn) > e; counter++) {
            xn = xn1;
            double fn = calculate(function, xn);
            xn1 = xn - fn / fDerivateZero;
            System.out.format("%5d%20f\n", counter, xn);
        }
        System.out.println("A priori: " + counter);
    }
    public static double log2(double x){
        return (Math.log(x)/ Math.log(2));
    }

    private static double calculate(List<Integer> function, double number){
        double result = 0;
        for(int i = function.size()-1, j = 0; i>= 0; i--, j++){
            result += function.get(i) * Math.pow(number, j);
        }
        return result;
    }

    private double calculateDerivate(List<Integer> derivativeFirst, double number){
        double result = 0;
        for(int i = derivativeFirst.size()-1, j = 0; i>= 0; i--, j++){
            result += derivativeFirst.get(i) * Math.pow(number, j);
        }
        return result;
    }

}
