package sixth.lab.b;

import java.util.Arrays;
import java.util.Random;

public class Simulation implements Runnable {
    private Integer[][] field;
    private final int numberOfRows;
    private final int numberOfColumns;
    private Buffer buffer;

    public Simulation(Buffer buffer, int numberOfRows, int numberOfColumns) {
        this.buffer = buffer;
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        field = new Integer[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; ++i) {
            Random random = new Random();
            for (int j = 0; j < numberOfColumns; ++j) {
                field[i][j] = Math.abs(random.nextInt() % Manager.CIVILIZATION_NUMBER + 1);
            }
        }
    }

    private boolean insideField(int i, int j) {
        return i >= 0 && i < field.length && j >= 0 && j < field[0].length;
    }

    private int countNeighbors(int i, int j) {
        int answer = 0;
        int[] shifts = { -1, 0, 1 };
        for (int xShift : shifts) {
            for (int yShift : shifts) {
                if (xShift == 0 && yShift == 0)
                    continue;
                int x = i + xShift;
                int y = j + yShift;
                if (insideField(x, y)) {
                    if (field[x][y].equals(field[i][j])) {
                        ++answer;
                    }
                }
            }
        }
        return answer;
    }

    private Integer[] countAllNeighbors(int i, int j) {
        Integer[] neighbors = new Integer[Manager.CIVILIZATION_NUMBER];
        Arrays.fill(neighbors, 0);
        int[] shifts = { -1, 0, 1 };
        for (int xShift : shifts) {
            for (int yShift : shifts) {
                if (xShift == 0 && yShift == 0)
                    continue;
                int x = i + xShift;
                int y = j + yShift;
                if (insideField(x, y)) {
                    if (field[x][y] != 0) {
                        ++neighbors[field[x][y] - 1];
                    }
                }
            }
        }
        return neighbors;
    }

    public void update() {
        Integer[][] newField = new Integer[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; ++i) {
            for (int j = 0; j < numberOfColumns; ++j) {
                newField[i][j] = field[i][j];

                if (field[i][j] == 0) {
                    Integer[] neighbors = countAllNeighbors(i, j);
                    int civil = -1;
                    int numberOfNeighbours = 0;
                    for (int k = 0; k < neighbors.length; ++k) {
                        if (neighbors[k] > numberOfNeighbours) {
                            numberOfNeighbours = neighbors[k];
                            civil = k;
                        }
                    }
                    if (civil != -1 && numberOfNeighbours == 3) {
                        newField[i][j] = civil + 1;
                    }
                } else {
                    int numberOfNeighbours = countNeighbors(i, j);
                    if (numberOfNeighbours < 2 || numberOfNeighbours > 3) {
                        newField[i][j] = 0;
                    }
                }
            }
        }
        field = newField;
    }

    @Override
    public void run() {
        while (true) {
            buffer.putInSecondary(field);
            update();
        }
    }
}

