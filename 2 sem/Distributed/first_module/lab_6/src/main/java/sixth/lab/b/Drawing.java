package sixth.lab.b;

import javax.swing.*;
import java.awt.*;

public class Drawing extends JPanel implements Runnable {
    private final int sizeOfCell;
    private Integer[][] field;
    private final Buffer buffer;
    private final Color[] colors = { Color.BLACK, Color.WHITE, Color.cyan, Color.MAGENTA };

    public Drawing(Buffer buffer, int sizeOfCell) {
        this.sizeOfCell = sizeOfCell;
        this.buffer = buffer;
    }

    @Override
    public void paint(Graphics g) {
        if (field == null)
            return;
        for (int i = 0; i < field.length; ++i) {
            for (int j = 0; j < field[0].length; ++j) {
                Color color = colors[field[i][j]];
                g.setColor(color);
                g.fillRect(i * sizeOfCell, j * sizeOfCell, (i + 1) * sizeOfCell, (j + 1) * sizeOfCell);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            field = buffer.getFromPrimary();
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}