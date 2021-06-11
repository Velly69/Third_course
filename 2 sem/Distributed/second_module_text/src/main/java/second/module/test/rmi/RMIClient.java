package second.module.test.rmi;

import second.module.test.ui.UI;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.text.ParseException;

public class RMIClient {
    public static void main(String[] args) throws IOException, ParseException, NotBoundException {
        String url = "//localhost:123/Module";
        RMIServer server = (RMIServer) Naming.lookup(url);
        System.out.println("RMI object found");
        UI ui = new UI(server);
        ui.interactWithUser(args);
    }
}
