package sixth.lab.a;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class SimulationThread extends Thread {
    private final CyclicBarrier barrier;
    private final Simulation simulation;
    private final int start;
    private final int sizeOfTask;

    public SimulationThread(CyclicBarrier barrier, Simulation simulation, int start, int sizeOfTask) {
        this.barrier = barrier;
        this.simulation = simulation;
        this.start = start;
        this.sizeOfTask = sizeOfTask;
    }

    private boolean insideField(int i, int j, Integer[][] field) {
        return i >= start && i < start + sizeOfTask && j >= 0 && j < field[0].length;
    }

    private int countNeighbours(int i, int j, Integer[][] field) {
        int answer = 0;
        int[] shifts = { -1, 0, 1 };
        for (int xShift : shifts) {
            for (int yShift : shifts) {
                if (xShift == 0 && yShift == 0)
                    continue;
                int x = i + xShift;
                int y = j + yShift;
                if (insideField(x, y, field)) {
                    if (field[x][y] == 1) {
                        ++answer;
                    }
                }
            }
        }
        return answer;
    }

    @Override
    public void run() {
        while (true) {
            while (!Manager.updated){
                Thread.yield();
            }
            Integer[][] field = simulation.getField();
            Integer[][] newField = new Integer[sizeOfTask][Manager.COLUMNS];
            for (int i = start; i < start + sizeOfTask; ++i) {
                for (int j = 0; j < Manager.COLUMNS; ++j) {
                    newField[i - start][j] = field[i][j];
                    int numberOfNeighbours = countNeighbours(i, j, field);

                    if (field[i][j] == 0) {
                        if (numberOfNeighbours == 3) {
                            newField[i - start][j] = 1;
                        }
                    } else {
                        if (numberOfNeighbours < 2 || numberOfNeighbours > 3) {
                            newField[i - start][j] = 0;
                        }
                    }
                }
            }
            for (int i = start; i < start + sizeOfTask; ++i) {
                for (int j = 0; j < Manager.COLUMNS; ++j) {
                    field[i][j] = newField[i - start][j];
                }
            }
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
