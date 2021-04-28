package seventh.lab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final int NUMBER_OF_DUCKS = 15;
    private final Timer timer;
    private int score = 0;
    private int bulletsCount = NUMBER_OF_DUCKS * 3;
    private boolean inGame;
    private final Image backgroundImage;
    private final Random random = new Random();
    public static int gameWidth;
    public static int gameHeight;
    private final Hunter hunter;
    private List<Duck> ducks;

    public GamePanel() {
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
        setDoubleBuffered(true);
        inGame = true;
        backgroundImage = new ImageIcon("src/main/resources/back.png").getImage();
        gameWidth = backgroundImage.getWidth(this);
        gameHeight = backgroundImage.getHeight(this);
        setPreferredSize(new Dimension(gameWidth, gameHeight));
        hunter = new Hunter(0, gameHeight - 300, 0, 0);
        createDucks();
        timer = new Timer(8, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, null);
        if (inGame) {
            drawObjects(g);
        }
        else {
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void createDucks() {
        ducks = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DUCKS; i++) {
            int x = random.nextInt(gameWidth - 300);
            int y = random.nextInt(gameHeight - 600);
            int dx = 0;
            while (dx == 0) {
                dx = random.nextInt(10) - 5;
            }
            Duck newDuck = new Duck(x, y, dx, 0);
            ducks.add(newDuck);
        }
    }

    private void drawObjects(Graphics g) {
        if (hunter.isVisible()) {
            g.drawImage(hunter.getImage(), hunter.getX(), hunter.getY(), this);
        }
        ArrayList<Bullet> bullets = (ArrayList<Bullet>) hunter.bullets;
        for (Bullet s : bullets) {
            if (s.isVisible()) {
                g.drawImage(s.getImage(), s.getX(), s.getY(), this);
            }
        }
        for (Duck duck : ducks) {
            if (duck.isVisible()) {
                g.drawImage(duck.getImage(), duck.getX(), duck.getY(), this);
            }
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 30));
        g.drawString("Ducks left: " + ducks.size(), 15, 30);
        g.drawString("Bullets left: " + bulletsCount, gameWidth - 230, 30);
    }

    private void drawGameOver(Graphics g) {
        String msg = "Game Over";
        String scoreMsg = "Score: " + score;
        Font font = new Font("Helvetica", Font.BOLD, 48);
        FontMetrics fm = getFontMetrics(font);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg, (gameWidth - fm.stringWidth(msg)) / 2, gameHeight / 2);
        g.drawString(scoreMsg, (gameWidth - fm.stringWidth(scoreMsg)) / 2, gameHeight / 2 + 48);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();
        updateShots();
        updateDucks();
        checkCollisionOfBulletAndDuck();
        repaint();
    }

    private void inGame() {
        if (!inGame) {
            timer.stop();
        }
    }

    private void updateShots() {
        List<Bullet> bullets = hunter.bullets;
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (!bullet.isVisible()) {
                bullets.remove(i);
            }
        }
    }

    private void updateDucks() {
        if (ducks.isEmpty()) {
            inGame = false;
            return;
        }
        for (int i = 0; i < ducks.size(); i++) {
            Duck duck = ducks.get(i);
            if (!duck.isVisible()) {
                ducks.remove(i);
            }
        }
    }

    private void checkCollisionOfBulletAndDuck() {
        for (Bullet bullet : hunter.bullets) {
            Rectangle bulletBounds = bullet.getBounds();
            for (Duck duck : ducks) {
                if (!duck.isDead()) {
                    Rectangle duckBounds = duck.getBounds();
                    if (bulletBounds.intersects(duckBounds)) {
                        bullet.setVisible(false);
                        score++;
                        duck.setIsDead(true);
                        if (ducks.isEmpty()) {
                            inGame = false;
                        }
                    }
                }
            }
        }
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                hunter.dx = 0;
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_SPACE) {
                if (bulletsCount > 0) {
                    bulletsCount--;
                    hunter.fire();
                } else {
                    inGame = false;
                }
            }
            if (key == KeyEvent.VK_LEFT) {
                hunter.dx = -hunter.getShift();
            }
            if (key == KeyEvent.VK_RIGHT) {
                hunter.dx = hunter.getShift();
            }
        }
    }
}
