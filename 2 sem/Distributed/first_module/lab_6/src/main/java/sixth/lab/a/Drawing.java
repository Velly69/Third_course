package sixth.lab.a;

import javax.swing.*;
import java.awt.*;

public class Drawing extends JPanel implements Runnable {
    private final int sizeOfCell;
    private Integer[][] field;
    private final Buffer buffer;
    private final Color[] colors = { Color.BLACK, Color.WHITE, Color.MAGENTA, Color.RED, Color.CYAN };

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
                int index = i / Manager.TASK_SIZE + 1;
                if (index > Manager.THREAD_NUM)
                    index = Manager.THREAD_NUM;
                Color color = field[i][j] != 0 ? colors[index] : colors[0];
                g.setColor(color);
                g.fillRect(j * sizeOfCell, i * sizeOfCell, (j + 1) * sizeOfCell, (i + 1) * sizeOfCell);
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
