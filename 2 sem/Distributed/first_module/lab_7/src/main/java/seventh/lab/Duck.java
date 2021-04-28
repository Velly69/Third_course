package seventh.lab;

public class Duck extends GameObject {
    private boolean isMovingRight;
    private boolean isDead;

    public Duck(int x, int y, int dx, int dy) {
        super(x, y, dx, dy);
        if (dx > 0) {
            isMovingRight = true;
            loadImage("src/main/resources/duckRight.gif");
        } else {
            isMovingRight = false;
            loadImage("src/main/resources/duckLeft.gif");
        }
        width = 200;
        height = 100;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void setIsDead(boolean b) {
        isDead = b;
        loadImage("src/main/resources/duckDead.png");
    }

    public boolean isDead() {
        return isDead;
    }

    @Override
    public void run() {
        while (!isDead) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            move();
            if (x > GamePanel.gameWidth - 200 || x < 0) {
                dx = -dx;
                if (isMovingRight) {
                    isMovingRight = false;
                    loadImage("src/main/resources/duckLeft.gif");
                } else {
                    isMovingRight = true;
                    loadImage("src/main/resources/duckRight.gif");
                }
            }
        }
        try {
            Thread.sleep(sleepTime * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        visible = false;
    }

    @Override
    protected void loadImage(String imageName) {
        super.loadImage(imageName);
        image = image.getScaledInstance(100,100, 0);
        getImageDimensions();
    }

    @Override
    protected void getImageDimensions() {
        width = 100;
        height = 100;
    }
}
