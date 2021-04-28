package first.lab.b;

import first.lab.MyRunnable;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class App {
    private static Thread firstThread;
    private static Thread secondThread;
    private static final JSlider slider = new JSlider();
    public static AtomicInteger semaphore = new AtomicInteger(0);
    private static JButton start1ThreadButton = new JButton("Start 1 Thread");
    private static JButton start2ThreadButton = new JButton("Start 2 Thread");
    private static JButton stop1ThreadButton = new JButton("Stop 1 Thread");
    private static JButton stop2ThreadButton = new JButton("Stop 2 Thread");
    private static JLabel infoLabel = new JLabel("Blocked by thread...");

    public static void main(String[] args) {
        setUI();
    }

    private static void setUI() {
        JFrame frame = new JFrame("B");

        slider.setBounds(50,30,300,50);
        slider.setMajorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        infoLabel.setBounds(150, 200, 200, 50);

        start1ThreadButton.setBounds(50,100,150,50);
        start1ThreadButton.addActionListener(e -> clickedStart1Thread());

        start2ThreadButton.setBounds(200,100,150,50);
        start2ThreadButton.addActionListener(e -> clickedStart2Thread());

        stop1ThreadButton.setBounds(50,150,150,50);
        stop1ThreadButton.addActionListener(e -> clickedStop1Thread());

        stop2ThreadButton.setBounds(200,150,150,50);
        stop2ThreadButton.addActionListener(e -> clickedStop2Thread());

        frame.add(slider);
        frame.add(start1ThreadButton);
        frame.add(start2ThreadButton);
        frame.add(stop1ThreadButton);
        frame.add(stop2ThreadButton);
        frame.add(infoLabel);

        infoLabel.setVisible(false);

        frame.setSize(400,500);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private static void clickedStart1Thread() {
        if(semaphore.get() == 1) {
            infoLabel.setVisible(true);
            return;
        }
        firstThread = new Thread(new MyRunnable(10, slider));
        firstThread.setDaemon(true);
        firstThread.start();
        firstThread.setPriority(1);

        stop2ThreadButton.setEnabled(false);
        infoLabel.setVisible(false);
    }

    private static void clickedStart2Thread() {
        if(semaphore.get() == 1) {
            infoLabel.setVisible(true);
            return;
        }
        secondThread = new Thread(new MyRunnable(90, slider));
        secondThread.setDaemon(true);
        secondThread.start();
        secondThread.setPriority(10);

        stop1ThreadButton.setEnabled(false);
        infoLabel.setVisible(false);
    }

    private static void clickedStop1Thread() {
        if (semaphore.get() == 1) {
            firstThread.interrupt();
            stop2ThreadButton.setEnabled(true);
            infoLabel.setVisible(false);
        }
    }

    private static void clickedStop2Thread() {
        if (semaphore.get() == 1) {
            secondThread.interrupt();
            stop1ThreadButton.setEnabled(true);
            infoLabel.setVisible(false);
        }
    }
}

