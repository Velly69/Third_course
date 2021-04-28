package third.lab.b;

public class App {
    public static void main(String[] args) {
        int numberOfVisitors = 10;

        Thread[] threads = new Thread[numberOfVisitors];
        Barber barber = new Barber();

        for (int i = 0; i < numberOfVisitors; i++) {
            threads[i] = new Thread(new Visitor(barber), "Visitor №" + i);
            threads[i].start();
        }

        for (int i =0; i<numberOfVisitors; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
