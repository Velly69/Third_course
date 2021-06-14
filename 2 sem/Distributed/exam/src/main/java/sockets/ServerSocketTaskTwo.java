package sockets;

import model.Customer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Callback
{
    public boolean shouldEnd = false;
}

class CustomerIterator implements Runnable
{
    private Socket socket;
    private Callback callback;
    private List<Customer> customers;

    public CustomerIterator(Socket socket, Callback callback, List<Customer> customers)
    {
        this.callback = callback;
        this.socket = socket;
        this.customers = customers;
    }

    @Override
    public void run() {
        try {
            InputStreamReader ois = new InputStreamReader(socket.getInputStream());
            System.out.println("Receiving input");
            BufferedReader reader = new BufferedReader(ois);
            String message = reader.readLine();
            String splitMessage[] = message.split("#");
            String commandIndex = splitMessage[0];
            System.out.println("Command " + splitMessage[0]);

            if (commandIndex.equals("3"))
            {
                System.out.println("Close command");
                callback.shouldEnd = true;
                return;
            }
            List<Customer> result = new ArrayList<>();
            switch (commandIndex) {
                case "1": {
                    result = customers;
                    Collections.sort(result);
                    break;
                }
                case "2": {
                    Integer x = Integer.parseInt(splitMessage[1]);
                    Integer y = Integer.parseInt(splitMessage[2]);
                    for(Customer customer: customers) {
                        if(customer.getCardNumber() > x && customer.getCardNumber() < y) {
                            result.add(customer);
                        }
                    }
                    break;
                }
            }
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(result);
            ois.close();
            oos.close();
            socket.close();
        }
        catch (IOException e) { }
    }
}

public class ServerSocketTaskTwo {
    private static ServerSocket server;

    private static int port = 9876;

    public static Callback c = new Callback();

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        server = new ServerSocket(port);
        List<Customer> customers =  new ArrayList<Customer>() {
            {
                add(new Customer("001", "Alex", "Totsky",
                        "Kyiv", 27362278, "326563"));
                add(new Customer("002", "Yana", "Skyrda",
                        "Cherkasy", 75525222, "326563"));
                add(new Customer("003", "Dasha", "Bondarets",
                        "Rovno", 30476352, "326563"));
                add (new Customer("004", "Max", "Chi",
                        "Chernovcy", 26363632, "326563"));
                add(new Customer("005", "Darina", "Ponomaryova",
                        "Dybai", 27237267, "326563"));
            }
        };

        while(!c.shouldEnd){
            System.out.println("Waiting for the client request");
            Socket socket = server.accept();
            CustomerIterator iterator = new CustomerIterator(socket, c, customers);
            iterator.run();
        }
        System.out.println("Shutting down Socket server!!");
        server.close();
    }
}

