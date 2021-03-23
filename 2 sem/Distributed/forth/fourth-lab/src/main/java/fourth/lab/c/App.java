package fourth.lab.c;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import static java.lang.System.*;

public class App {
    private static final String KYIV = "Kyiv";
    private static final String LVIV = "Lviv";
    private static final String CHERKASY = "Cherkasy";
    private static final String KHARKIV = "Kharkiv";

    public static void main(String... args) throws InterruptedException {
        BusScheduler schedule = new BusScheduler();
        Creator threadCreator = new Creator(new ReentrantReadWriteLock(false), schedule);
        threadCreator.addBusStop(KYIV);
        threadCreator.addBusStop(LVIV);
        threadCreator.addBusStop(CHERKASY);
        threadCreator.addBusStop(KHARKIV);
        threadCreator.addFlight(KYIV, LVIV, 150);
        threadCreator.addFlight(CHERKASY, LVIV, 170);
        threadCreator.addFlight(CHERKASY, KHARKIV, 120);
        threadCreator.addFlight(KYIV, CHERKASY, 50);
        threadCreator.changeFlightPrice(KYIV, CHERKASY, 80);
        out.println("Price for flight " + CHERKASY + " - " + KHARKIV + " = " +
                threadCreator.getFlightPrice(CHERKASY, KHARKIV));
        threadCreator.deleteFlight(CHERKASY, KYIV);
        threadCreator.deleteBusStop(LVIV);
    }
}
