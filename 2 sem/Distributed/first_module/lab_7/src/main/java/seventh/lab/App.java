package seventh.lab;

import javax.swing.*;

public class App extends JFrame {
    public App() {
        add(new GamePanel());
        setResizable(false);
        pack();
        setTitle("Duck Hunt");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        App duckHunt = new App();
        duckHunt.setVisible(true);
    }
}
