package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientRmiTaskTwo {
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        int choice = 1000;
        int x, y;
        Scanner in = new Scanner(System.in);
        try {
            RMIServer st = (RMIServer) Naming.lookup("//localhost:123/exam");
            System.out.println("Choose option:\n"
                    + "1 - display Customers in alphabetic order\n"
                    + "2 - display Customers which card number in interval of X and Y\n");
            choice = in.nextInt();
            switch (choice) {
                case 1:
                    System.out.println(st.displayInAlphabetic());
                    break;
                case 2:
                    System.out.print("Enter X value: ");
                    x = in.nextInt();
                    System.out.print("Enter Y value: ");
                    y = in.nextInt();
                    System.out.println(st.displayInIntervalOfCardNumber(x, y));
                    break;
            }
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
