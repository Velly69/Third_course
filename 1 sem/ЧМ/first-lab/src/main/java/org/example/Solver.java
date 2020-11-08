package org.example;

public class Solver {
    public static void main(String[] args) {
        System.out.println("Relaxation method: ");
        Method relaxation = new Relaxation();
        relaxation.solve(-4,-3);
        System.out.println("Modificated Newton method: ");
        Method newton = new ModificatedNewton();
        newton.solve(-4,-3);
    }
}

