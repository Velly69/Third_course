package seventh.lab;

public class Bullet extends GameObject {
    private final Thread thread;

    public Bullet(int x, int y) {
        super(x, y, 0, -5);
        loadImage("src/main/resources/bullet.png");
        getImageDimensions();
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (visible) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            move();
            if (y < 0) {
                visible = false;
                thread.interrupt();
            }
        }
    }
}

