/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HanoiAnimated;

/**
 *
 * @author User
 */
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HanoiAnimated extends JPanel implements ItemListener {

    private static final int X_START = 50;
    private static final int X_END = 500;
    private static final int Y_END = 415;
    private static final int ROD_LENGTH = 200;
    private static final int ROD_GAP = 150;
    private static final int SIDE_GAP = 75;
    private static final int THICKNESS = 3;
    private static final int DiskTHICKNESS = 20;
    private static final String DISK_NUMBER_LABEL = "Select the number of disks:";

    static int tower[][];// the three towers' disks as stack
    static int top[];//top of the three stacks
    static int from, to;//moving 'from' tower number 'to' tower number
    static int diskInAir;//number of disk moved (1 to n)
    static int n, l, b, u; //n is number of disk, 

    static JComboBox c1;
    static JLabel JL1;
    private static JButton animateButton;
    private static JLabel diskNumberLabel;
    private static JComboBox<Integer> diskNumberSelection;

    static Color colors[] = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW};

    public HanoiAnimated() {
        tower = new int[3][10];
        top = new int[3];
    }

    static void push(int to, int diskno) //putting disk on tower
    {
        tower[to - 1][++top[to - 1]] = diskno;
    }

    static int pop(int from) //take topmost disk from tower
    {
        return (tower[from - 1][top[from - 1]--]);
    }

    Color getColor(int disknum) {
        return colors[disknum % 8]; //8 possible disks
    }

    void drawStill(Graphics g) {
        int j, i, disk;
        g.clearRect(0, 0, getWidth(), getHeight());
        for (j = 1; j <= 3; j++) {
            //draw tower
            Graphics2D g2D = (Graphics2D) g;
            g2D.setStroke(new BasicStroke(THICKNESS));
            g.setColor(Color.DARK_GRAY);
            //Draw the platform 
            g.drawLine(X_START, Y_END, X_END, Y_END);
            //Draw the rods
            g.drawLine(X_START + SIDE_GAP, Y_END - ROD_LENGTH, X_START + SIDE_GAP, Y_END);
            g.drawLine(X_START + SIDE_GAP + ROD_GAP, Y_END - ROD_LENGTH, X_START + SIDE_GAP + ROD_GAP, Y_END);
            g.drawLine(X_END - SIDE_GAP, Y_END - ROD_LENGTH, X_END - SIDE_GAP, Y_END);
//            g.setColor(Color.GRAY);
//            g.fillRoundRect(j * l, u, 5, b - u, 1, 1);

            //draw all disks on tower
            for (i = 0; i <= top[j - 1]; i++) {
                Graphics2D g2D2 = (Graphics2D) g;
                g2D2.setStroke(new BasicStroke(THICKNESS - 1));
                g.setColor(Color.BLACK);
                disk = tower[j - 1][i];
//                g.drawOval(X_START + SIDE_GAP + ROD_GAP * i - (diskSize * DiskTHICKNESS / 2),
//                        Y_END - DiskTHICKNESS * 1 + top[j - 1], diskSize * DiskTHICKNESS, DiskTHICKNESS);

                g.setColor(getColor(disk));
//                g.fillOval(X_START + SIDE_GAP + ROD_GAP * i - (diskSize * DiskTHICKNESS / 2),
//                        Y_END - DiskTHICKNESS * 1 + top[j - 1], diskSize * DiskTHICKNESS, DiskTHICKNESS);
                g.fillOval(j * l - 50 - disk * 5, b - (i + 1) * 20, 55 + disk * 10, DiskTHICKNESS);

            }
        }
    }

    void drawFrame(Graphics g, int x, int y) {
        try {
            drawStill(g);
            g.setColor(getColor(diskInAir));
//            g.fillOval(X_START + SIDE_GAP + ROD_GAP * x - (diskSize * DiskTHICKNESS / 2),
//                    Y_END - DiskTHICKNESS * 1 + top[y - 1], diskSize * DiskTHICKNESS, DiskTHICKNESS);
            g.fillOval(x - 50 - diskInAir * 5, y - 50, 55 + diskInAir * 10, DiskTHICKNESS);
            Thread.sleep(60);
        } catch (InterruptedException ex) {
        }

    }

    void animator(Graphics g) //to show the movement of disk
    {
        int x, y, dif, sign;
        diskInAir = pop(from);
        x = from * l;
        y = b - (top[from - 1] + 1) * 10;
        //taking disk upward from the tower
        for (; y > u - 20; y -= 8) {
            drawFrame(g, x, y);
        }

        y = u - 20;
        dif = to * l - x;
        sign = dif / Math.abs(dif);
        //moving disk towards a target tower
        for (; Math.abs(x - to * l) >= 24; x += sign * 12) {
            drawFrame(g, x, y);
        }

        x = to * l;
        //placing disk on a target tower
        for (; y < b - (top[to - 1] + 1) * 10; y += 8) {
            drawFrame(g, x, y);
        }
        push(to, diskInAir);
        drawStill(g);
    }

    void solveTowersOfHanoi(Graphics g, int n, int a, int b, int c) throws InterruptedException //Move top n disk from tower 'a' to tower 'c'
    //tower 'b' used for swapping
    {
        if (n >= 1) {
            solveTowersOfHanoi(g, n - 1, a, c, b);
            drawStill(g);
            Thread.sleep(700);
            from = a;
            to = c;
            //animating the move
            animator(g);
            solveTowersOfHanoi(g, n - 1, b, a, c);
        }
    }

    public static void main(String[] args) throws IOException {

        int i = 0;
        String s = JOptionPane.showInputDialog("Enter number of disks");
        HanoiAnimated ha = new HanoiAnimated();

        n = Integer.parseInt(s);

        //setting all tower empty
        for (i = 0; i < 3; i++) {
            top[i] = -1;
        }

        //putting all disks on tower 'a'
        for (i = n; i > 0; i--) {
            push(1, i);
        }

        JFrame fr = new JFrame();
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLayout(new BorderLayout());
        fr.setSize(600, 500);
        fr.add(ha);

        ha.setSize(fr.getSize());
        fr.setVisible(true);
        l = ha.getWidth() / 4;
        b = ha.getHeight() - 50;
        u = b - n * 12;
        //start solving
        try {
            ha.solveTowersOfHanoi(ha.getGraphics(), n, 1, 2, 3);
        } catch (Exception ex) {
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
