package org.lab.first;

import java.util.Scanner;

public class Dialog {
    public static int makeChoice(){
        System.out.println("What do you want to do?\n" +
                "(1) continue \n" +
                "(2) continue without prompt \n" +
                "(3) stop \n " +
                "Write the number: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        return choice;
    }
}
