package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Relaxation implements Method{
    public void solve(double first, double second) {
        relaxationMethod(first, second);
    }

    private void relaxationMethod(double first, double second){
        List<Integer> function = new ArrayList<>(Arrays.asList(1, -3, -17, 22));
        List<Integer> derivative = new ArrayList<>(Arrays.asList(3, -6, -17));
        double m1 = calculateDerivate(derivative, second);
        double M1 = calculateDerivate(derivative, first);
        double t = 2 / (m1 + M1);
        double e = Math.pow(10, -4);
        double q = (M1 - m1) / (M1 + m1);
        double z0 = 0;
        double xstar = second;
        z0 = Math.abs(first - xstar);
        int n = (int) (Math.log(z0/e) / Math.log(1/q));
        n++;
        double xn = first; //x0
        double xn1 = 0;
        boolean found = true;
        int aposterior = 0;
        for (int i = 0; i < n; i++) {
            double fn = calculate(function, xn);
            xn1 = xn - t * fn;
            if(found && Math.abs(xn1 - xn) <= e){
                aposterior = i;
                found = false;
            }
            xn = xn1;
            System.out.format("%5d%20f\n", i+1, xn);
        }
        System.out.println("A priori: " + n);
        System.out.println("Aposterio: " + aposterior);
    }

    private static double calculateDerivate(List<Integer> derivate, double number){
        double result = 0;
        for(int i = derivate.size()-1, j = 0; i>= 0; i--, j++){
            result += derivate.get(i) * Math.pow(number, j);
        }
        return result;
    }

    private static double calculate(List<Integer> function, double number){
        double result = 0;
        for(int i = function.size()-1, j = 0; i>= 0; i--, j++){
            result += function.get(i) * Math.pow(number, j);
        }
        return result;
    }
}
