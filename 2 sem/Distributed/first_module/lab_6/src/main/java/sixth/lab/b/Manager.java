package sixth.lab.b;

import javax.swing.*;

public class Manager {
    static final int CIVILIZATION_NUMBER = 3;
    private final int sizeOfCell;
    private final int rows;
    private final int columns;

    public Manager(int sizeOfCell, int rows, int columns) {
        this.sizeOfCell = sizeOfCell;
        this.rows = rows;
        this.columns = columns;
    }

    public void start() {
        Buffer buffer = new Buffer();
        Simulation simulation = new Simulation(buffer, rows, columns);
        Drawing drawing = new Drawing(buffer, sizeOfCell);

        JFrame frame = new JFrame();
        frame.setSize((rows) * sizeOfCell, (columns + 3) * sizeOfCell);
        frame.getContentPane().add(drawing);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Thread draw = new Thread(drawing);
        Thread simulate = new Thread(simulation);
        simulate.start();
        draw.start();
        while (true) { }
    }
}
