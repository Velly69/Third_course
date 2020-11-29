package org.lab.first;


import java.util.Optional;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int x = Utils.getX();
        Manager manager = new Manager();
        long[] startTime = new long[]{System.nanoTime()};
        manager.run(x, new ConcurrentBiConsumer<Optional<Double>, Manager.Status>() {

            @Override
            public void acceptOnce(Optional<Double> result, Manager.Status status) {
                long duration = (System.nanoTime() - startTime[0]) / 1000000;
                System.out.println("Result: F(x) * G(x) = " + (result.isPresent() ? result.get() : "undefined"));
                System.out.println("Status: " + status.name());
                System.out.println("(took " + duration + " ms)");
                System.exit(0);
            }
        });
    }
}

