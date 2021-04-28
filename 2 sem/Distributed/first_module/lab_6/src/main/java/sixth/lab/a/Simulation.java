package sixth.lab.a;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Simulation implements Runnable {
    private final Integer[][] field;
    private final Buffer buffer;
    private final CyclicBarrier barrier;

    public Integer[][] getField() {
        return field;
    }

    public Simulation(Buffer buffer, CyclicBarrier barrier, int numberOfRows, int numberOfColumns) {
        this.buffer = buffer;
        this.barrier = barrier;
        field = new Integer[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; ++i) {
            Random random = new Random();
            for (int j = 0; j < numberOfColumns; ++j) {
                field[i][j] = Math.abs(random.nextInt() % (Manager.CIVILIZATION_NUMBER + 1));
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            buffer.putInSecondary(field);
            Manager.updated = true;
        }
    }
}
