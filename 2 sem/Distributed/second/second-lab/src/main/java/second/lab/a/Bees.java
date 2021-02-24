package second.lab.a;

public class Bees implements Runnable{
    private static Manager manager;
    private static int winniX;
    private static int winniY;

    public static int getWinniY() {
        return winniY;
    }

    public static int getWinniX() {
        return winniX;
    }

    public static void setManager(Manager manager) {
        Bees.manager = manager;
    }

    @Override
    public void run() {
        while (!manager.isFoundWinni()) {
            Manager.Task task = manager.getTask();
            System.out.println("Searching in area with number: " + task.getY());
            boolean [] area = task.getArea();

            if (area == null) return;

            for (int i = 0; i < area.length; i++) {
                if (area[i]){
                    winniX = i;
                    winniY = task.getY();
                    manager.setFoundWinni(true);
                }
            }
        }
    }
}
