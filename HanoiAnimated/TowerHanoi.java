/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package HanoiAnimated;

/**
 *
 * @author User
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class TowerHanoi extends javax.swing.JFrame {

    Graphics g;
    Thread animationThread;

    private static final int X_START = 100;
    private static final int X_END = 655;
    private static final int Y_END = 350;
    private static final int ROD_LENGTH = 200;
    private static final int ROD_GAP = 200;
    private static final int SIDE_GAP = 75;
    private static final int THICKNESS = 3;
    private static final int DiskTHICKNESS = 20;

    static int rods[][];// the three towers' disks as stack
    static int diskOntop[];//top of the three stacks
    static int from, to;//moving 'from' tower number 'to' tower number
    static int diskInAir;//number of disk moved (1 to n)
    static int n, xaxis, yaxis, u;

    static Color colors[] = {Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW};

    public TowerHanoi() {
        initComponents();
        g = jPanel1.getGraphics();
        jPanel1.paintComponents(g);
        rods = new int[3][10];
        diskOntop = new int[3];
    }

    static void push(int to, int numOfDisk) //putting disk on tower
    {
        rods[to - 1][++diskOntop[to - 1]] = numOfDisk;
    }

    static int pop(int from) //take topmost disk from tower
    {
        return (rods[from - 1][diskOntop[from - 1]--]);
    }

    Color getColor(int disknum) {
        return colors[disknum % 8]; //8 possible disks
    }

    void drawMainComponent(Graphics g) { //initialize the rods and disks
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

            //draw all disks on tower
            for (i = 0; i <= diskOntop[j - 1]; i++) {
                Graphics2D g2D2 = (Graphics2D) g;
                g2D2.setStroke(new BasicStroke(THICKNESS - 1));

                disk = rods[j - 1][i];
                g.setColor(getColor(disk));
                g.fillOval(j * xaxis - 55 - disk * 5, Y_END - (i + 1) * 20, 55 + disk * 10, DiskTHICKNESS);
                g.drawOval(j * xaxis - 55 - disk * 5, Y_END - (i + 1) * 20, 55 + disk * 10, DiskTHICKNESS);

            }
        }
    }

    void drawFrame(Graphics g, int x, int y) {
        try {
            drawMainComponent(g);
            g.setColor(getColor(diskInAir));
            g.fillOval(x - 50 - diskInAir * 5, y - 200, 55 + diskInAir * 10, DiskTHICKNESS);
            g.drawOval(x - 50 - diskInAir * 5, y - 200, 55 + diskInAir * 10, DiskTHICKNESS);
            Thread.sleep(60);
        } catch (InterruptedException ex) {
        }

    }

    void moveDisk(Graphics g) //to show the movement of disk
    {
        int dif, sign;
        diskInAir = pop(from);
        int x_clip = from * xaxis;
        int y_clip = yaxis - (diskOntop[from - 1] + 1);
        int heightofmove = u - 20;
        //taking disk upward from the tower
        while (y_clip > heightofmove) {
            y_clip -= 20; // move the disk upward
            drawFrame(g, x_clip, y_clip);
        }

        y_clip = heightofmove;
        dif = to * xaxis - x_clip; //distance to travel 
        sign = dif / Math.abs(dif); //to return absolute value 
        //moving disk towards a target tower
        while (Math.abs(x_clip - to * xaxis) >= 20) {
            x_clip += sign * 20;
            drawFrame(g, x_clip, y_clip);
        }

        //placing disk on a target tower
        x_clip = to * xaxis;
        while (y_clip < yaxis - (diskOntop[to - 1] + 1) * 10) {
            y_clip += 20;
            drawFrame(g, x_clip, y_clip);
        }
        push(to, diskInAir);
        drawMainComponent(g);
    }

    void solveTowersOfHanoi(Graphics g, int n, int a, int b, int c) throws InterruptedException //Move top n disk from tower 'a' to tower 'c' tower 'b' used for swapping
    {
        if (n >= 1) {
            solveTowersOfHanoi(g, n - 1, a, c, b);
            drawMainComponent(g);
            from = a;
            to = c;
            //animating the move
            moveDisk(g);
            solveTowersOfHanoi(g, n - 1, b, a, c);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        strbtn = new javax.swing.JButton();
        stpbtn = new javax.swing.JButton();
        diskbox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        strbtn.setText("START");
        strbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                strbtnActionPerformed(evt);
            }
        });
        getContentPane().add(strbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(342, 424, 154, 65));

        stpbtn.setText("STOP");
        stpbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stpbtnActionPerformed(evt);
            }
        });
        getContentPane().add(stpbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(561, 424, 154, 65));

        diskbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", " " }));
        diskbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diskboxActionPerformed(evt);
            }
        });
        getContentPane().add(diskbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(194, 445, -1, -1));

        jLabel1.setText("SELECT NUMBER OF DISK:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 448, -1, -1));

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 379, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 23, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HanoiAnimated/background.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 530));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void strbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_strbtnActionPerformed
        // TODO add your handling code here:
        String value = String.valueOf(diskbox.getSelectedItem());
        n = Integer.parseInt(value);

        animationThread = new Thread(new Runnable() {

            public void run() {
                TowerHanoi th = new TowerHanoi();
                int i;
                for (i = 0; i < 3; i++) {
                    diskOntop[i] = -1;
                }

                //putting all disks on tower 'a'
                for (i = n; i > 0; i--) {
                    push(1, i);
                }
                xaxis = th.getWidth() / 4;
                yaxis = th.getHeight() - 50;
                u = yaxis - n * 12;
                try {
                    th.solveTowersOfHanoi(g, n, 1, 2, 3);

                } catch (InterruptedException ex) {
                    System.out.println("SOMETHING WRONG");
                }
                try {
                    Thread.sleep(10);

//throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                } catch (Exception e) {
//                    
                }
            }

        });
        animationThread.start();

    }//GEN-LAST:event_strbtnActionPerformed

    private void diskboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diskboxActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_diskboxActionPerformed

    private void stpbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stpbtnActionPerformed
        // TODO add your handling code here:
        animationThread.stop();
        jPanel1.repaint();
    }//GEN-LAST:event_stpbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TowerHanoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TowerHanoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TowerHanoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TowerHanoi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TowerHanoi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> diskbox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton stpbtn;
    private javax.swing.JButton strbtn;
    // End of variables declaration//GEN-END:variables
}
