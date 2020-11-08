package org.lab.first;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Enter X value: ");
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        Testing test = new Testing(x);
        test.startTest();
    }
}
