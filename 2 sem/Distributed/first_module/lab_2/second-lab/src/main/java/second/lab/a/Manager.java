package second.lab.a;

import java.util.Random;

public class Manager {
    private boolean[][] forest;
    private int currTask = -1;
    private boolean foundWinni = false;

    public Manager(int x, int y) {
        if (x <= 0 ) x = 100;
        if (y <= 0 ) y = 100;
        forest = new boolean[x][y];
        winniHasLocated();
    }

    public boolean isFoundWinni() {
        return foundWinni;
    }

    public void setFoundWinni(boolean foundWinni) {
        this.foundWinni = foundWinni;
    }

    public synchronized Task getTask() {
        if(currTask + 1 < forest.length) {
            return new Task(forest[++currTask], currTask);
        }
        return null;
    }

    private void winniHasLocated() {
        Random r = new Random();
        int winnisX = r.nextInt(forest.length - 1);
        int winnisY = r.nextInt(forest[0].length - 1);
        forest[winnisX][winnisY] = true;
    }

    public class Task{
        private boolean [] area;
        private int y;

        public Task(boolean[] area, int y) {
            this.area = area;
            this.y = y;
        }

        public boolean[] getArea() {
            return area;
        }

        public int getY() {
            return y;
        }
    }
}
