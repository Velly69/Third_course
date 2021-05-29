import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    public App(){
        super("Isomorphic Stellar Transformation");
        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        initComponents();
        setSize(600, 350);
        setLocation(450, 100);
        setResizable(false);
        setVisible(true);
    }

    public void initComponents(){
        setLayout(new GridLayout(9, 1));

        JLabel titleRow = new JLabel("<html><p style='padding-left: 150px; font-size: 16px'>Stellated Polygons</p></html>");

        JLabel nRow = new JLabel();
        nRow.setLayout(new GridLayout(1, 4));
        JLabel n = new JLabel("<html><p style='padding-left: 6px; font-size: 11px'>The number of angles n: </p></html>");
        JTextField nInput = new JTextField();
        nRow.add(Box.createVerticalBox());
        nRow.add(n);
        nRow.add(nInput);
        nRow.add(Box.createVerticalBox());

        JLabel ttlsRow = new JLabel();
        ttlsRow.setLayout(new GridLayout(1, 7));
        JLabel label1 = new JLabel("<html><p style='padding-left: 20px; font-size: 13px' color='blue'>Z1</p></html>");
       // JLabel label2 = new JLabel("<html><p style='padding-left: 20px; font-size: 13px' color='red'>Z2</p></html>");
        ttlsRow.add(Box.createVerticalBox());
        ttlsRow.add(label1);
//        ttlsRow.add(Box.createVerticalBox());
//        ttlsRow.add(Box.createVerticalBox());
//        ttlsRow.add(Box.createVerticalBox());
//        ttlsRow.add(label2);
        ttlsRow.add(Box.createVerticalBox());

        JLabel xRow = new JLabel();
        xRow.setLayout(new GridLayout(1, 5));
        JLabel z1x = new JLabel("<html><p style='padding-left: 8px'> x of circle around Z1: </p></html>");
        JTextField z1xInput = new JTextField();
        //JLabel z2x = new JLabel("x кола навколо Z2: ");
        //JTextField z2xInput = new JTextField();
        xRow.add(z1x);
        xRow.add(z1xInput);
//        xRow.add(Box.createVerticalBox());
//        xRow.add(z2x);
//        xRow.add(z2xInput);

        JLabel yRow = new JLabel();
        yRow.setLayout(new GridLayout(1, 5));
        JLabel z1y = new JLabel("<html><p style='padding-left: 8px'> y of circle around Z1: </p></html>");
        JTextField z1yInput = new JTextField();
        //JLabel z2y = new JLabel("y кола навколо Z2: ");
        //JTextField z2yInput = new JTextField();
        yRow.add(z1y);
        yRow.add(z1yInput);
//        yRow.add(Box.createVerticalBox());
//        yRow.add(z2y);
//        yRow.add(z2yInput);

        JLabel angleRow = new JLabel();
        angleRow.setLayout(new GridLayout(1, 5));
        JLabel z1Angle = new JLabel("<html><p style='padding-left: 8px'> Angle of rotation Z1: </p></html>");
        JTextField z1AngleInput = new JTextField();
        //JLabel z2Angle = new JLabel("<html><p style='padding-left: 8px'>Кут повороту Z2: </p></html>");
        //JTextField z2AngleInput = new JTextField();
        angleRow.add(z1Angle);
        angleRow.add(z1AngleInput);
//        angleRow.add(Box.createVerticalBox());
//        angleRow.add(z2Angle);
//        angleRow.add(z2AngleInput);

        JLabel btnRow = new JLabel();
        btnRow.setLayout(new GridLayout(1, 3));
        JButton btn = new JButton("Find the transformation");
        btn.addActionListener(e -> {
            if (!nInput.getText().equals("") && !z1xInput.getText().equals("") && !z1yInput.getText().equals("")
                    && !z1AngleInput.getText().equals("")){
                int nTrans = Integer.parseInt(nInput.getText());
                int xZ1 = Integer.parseInt(z1xInput.getText());
                int yZ1 = Integer.parseInt(z1yInput.getText());
                //int xZ2 = Integer.parseInt(z2xInput.getText());
                //int yZ2 = Integer.parseInt(z2yInput.getText());
                int angleZ1 = Integer.parseInt(z1AngleInput.getText());
                //int angleZ2 = Integer.parseInt(z2AngleInput.getText());
                if (nTrans < 5){
                    JOptionPane.showMessageDialog(null, "<html><p style='font-size: 17px'>n should be more than 4</p></html>");
                } else {
                    new IsomorphicStellarTransformation(nTrans, xZ1, yZ1, angleZ1);
                }
            } else {
                JOptionPane.showMessageDialog(null, "<html><p style='font-size: 17px'>Fill in all fields</p></html>");
            }

        });
        btnRow.add(Box.createVerticalBox());
        btnRow.add(btn);
        btnRow.add(Box.createVerticalBox());

        add(titleRow);
        add(nRow);
        add(ttlsRow);
        add(xRow);
        add(yRow);
        add(angleRow);
        add(Box.createHorizontalBox());
        add(btnRow);
        add(Box.createHorizontalBox());
    }

    public static void main(String[] args) {
        new App();
    }
}
