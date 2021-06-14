package rmi;

import model.Customer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface RMIServer extends Remote {
    List<Customer> displayInAlphabetic();
    List<Customer> displayInIntervalOfCardNumber(Integer a, Integer b);
}

public class ServerRmiTaskTwo {
    public static void main(String[] args) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(123);
        RMIServer service = new Service();
        registry.rebind("exam", service);
        System.out.println("Server started!");
    }

    static class Service extends UnicastRemoteObject implements RMIServer {
        List<Customer> customers;

        Service() throws RemoteException {
            super();
            customers = new ArrayList<Customer>() {
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
        }

        @Override
        public List<Customer> displayInAlphabetic() {
            List<Customer> results = customers;
            Collections.sort(results);
            return results;
        }

        @Override
        public List<Customer> displayInIntervalOfCardNumber(Integer a, Integer b) {
            List<Customer> results = new ArrayList<>();
            for(Customer customer: customers) {
                if(customer.getCardNumber() > a && customer.getCardNumber() < b) {
                    results.add(customer);
                }
            }
            return results;
        }
    }
}

