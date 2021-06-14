package sockets;

import model.Customer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSocketTaskTwo {

    private static int port = 9876;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Scanner scan = new Scanner(System.in);
        while (true)
        {
            System.out.println("Choose option:\n"
                    + "1 - display Customers in alphabetic order\n"
                    + "2 - display Customers which card number in interval of X and Y\n"
                    + "3 - exit");
            socket = new Socket(host.getHostName(), port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Sending request to Socket Server");
            int commandIndex = scan.nextInt();
            if (commandIndex == 3)
            {
                socket = new Socket(host.getHostName(), port);
                oos = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Sending close Request");
                oos.writeInt(commandIndex); oos.flush();
                break;
            }
            switch (commandIndex) {
                case 1:  {
                    String message = "" + commandIndex;
                    oos.writeBytes(message);
                    oos.flush();
                    break;
                }
                case 2: {
                    System.out.println("Enter X");
                    Integer x = scan.nextInt();
                    System.out.println("Enter Y");
                    Integer y = scan.nextInt();
                    String message = "" + commandIndex + "#" + x + "#" + y;
                    oos.writeBytes(message);
                    oos.flush();
                    break;
                }
            }
            System.out.println("Results: ");
            ois = new ObjectInputStream(socket.getInputStream());
            ArrayList<Customer> results = (ArrayList<Customer>) ois.readObject();
            for (Customer customer: results)
            {
                System.out.println(customer);
            }
            ois.close();
            oos.close();
            Thread.sleep(100);
        }
        oos.writeInt(3);
        System.out.println("Shutting down client!!");
    }
}

