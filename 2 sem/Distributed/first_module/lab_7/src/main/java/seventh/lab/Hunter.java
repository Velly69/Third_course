package seventh.lab;

import java.util.ArrayList;
import java.util.List;

public class Hunter extends GameObject {
    public List<Bullet> bullets;
    private static final int SHIFT = 5;

    public Hunter(int x, int y, int dx, int dy) {
        super(x, y, dx, dy);
        loadImage("src/main/resources/hunter.jpeg");
        bullets = new ArrayList<>();
        Thread thread = new Thread(this);
        thread.start();
    }

    public int getShift() {
        return SHIFT;
    }

    @Override
    protected void loadImage(String imageName) {
        super.loadImage(imageName);
        image = image.getScaledInstance(200,200, 0);
        getImageDimensions();
    }

    @Override
    public void run() {
        while(visible) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            move();
        }
    }

    public void fire() {
        Bullet newBullet = new Bullet(x + width + 80, y + 10);
        bullets.add(newBullet);
    }
}
