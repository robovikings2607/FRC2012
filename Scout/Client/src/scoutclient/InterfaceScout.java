/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scoutclient;
//
import java.awt.print.PrinterException;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;//
import java.io.Serializable;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;//
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *
 * @author Griffin
 */
public class InterfaceScout extends javax.swing.JFrame implements Runnable
{
    private static final String theme = "Nimbus";
Thread me = new Thread(this);
    /**
     * Creates new form InterfaceScout
     */
    int ints[][] = new int[6000][14];
    int ints_filtered[][] = new int[54][14];
    Integer points[][] = new Integer[54][5];
         String notes[] = new String[6000];
         String headers[] = {"Auton","Teleop","# of Matches","Defense","Bots Bal With","Has an Auton","Tip in Auton","Functioning","Balanced Bridge","Coop Score","Tipped - Bridge","Teams"}; //"Team:   Top    Mid    Low    Bal    Def    Bob    Cro    Coo    Funn    Fou    Rob   Bri\n";
    Integer teams[] = {11,25,56,75,222,223,224,225,272,303,316,341,357,365,423,433,484,486,708,714,816,834,869,1089,1168,1218,1279,1302,1370,1391,1403,1626,1640,1647,1672,1676,1712,1811,1923,2016,2180,2234,2559,2590,2607,2729,3142,3314,3515,3637,3929,3974,4342};
         public void sendToDatabase(Serializable data) throws Exception
    {
        String modifiedSentence;
        Socket clientSocket = new Socket(jTextField6.getText(), 4765);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        ObjectOutputStream oos = new ObjectOutputStream(outToServer);
      //  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        oos.writeObject(data);
        oos.flush();
        Thread.sleep(1000);
        //oos.close();
   //     modifiedSentence = inFromServer.readLine();
        //System.out.println("FROM SERVER: " + modifiedSentence);
       // inFromServer.close();
        oos.close();
        clientSocket.close();
    }
    public void sendToDatabase(String data) throws Exception
    {
        String modifiedSentence;
        Socket clientSocket = new Socket(jTextField6.getText(), 4765);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      //  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outToServer.writeBytes(data);
        outToServer.flush();
        Thread.sleep(1000);
        outToServer.close();
   //     modifiedSentence = inFromServer.readLine();
        //System.out.println("FROM SERVER: " + modifiedSentence);
       // inFromServer.close();
        clientSocket.close();
    }
    
    public boolean containsOnlyNumbers(String str) {
        
    //It can't contain only numbers if it's null or empty...
    if (str == null || str.length() == 0){
        System.out.println("yo");
    return false;
    }
        
    for (int i = 0; i < str.length(); i++) {

        //If we find a non-digit character we return false.
        if (!Character.isDigit(str.charAt(i))){
            System.out.println(str.charAt(i));
            return false;
        }
    }
       
    return true;
    }
        //
    public InterfaceScout() {
        initComponents();
        me.start();
    }
    
    public boolean checkTheFieldsAreValid (){
        boolean validTeam = false;
        for (int theta = 0; theta<teams.length; theta++)
        {
            if ((""+teams[theta]).equals(teamNumber.getText()))
            {
                validTeam = true;
            }
        }
        if (!validTeam)
        {
            return false;
        }
        if (!((teamNumber.getText() != null || !"".equals(teamNumber.getText())) && containsOnlyNumbers(teamNumber.getText())) ){
            System.out.println("1");
          return false;  
        }
        else if (!((topPoints.getText() != null || !"".equals(topPoints.getText())) && containsOnlyNumbers(topPoints.getText())) ){
            System.out.println("2");
          return false;  
        }
        else if (!((midPoints.getText() != null || !"".equals(midPoints.getText())) && containsOnlyNumbers(midPoints.getText())) ){
            System.out.println("3");
          return false;  
        }
        else if (!((lowPoints.getText() != null || !"".equals(lowPoints.getText())) && containsOnlyNumbers(lowPoints.getText())) ){
            System.out.println("4");
          return false;  
        }
                else if (!((teleopTopPoints.getText() != null || !"".equals(topPoints.getText())) && containsOnlyNumbers(topPoints.getText())) ){
            System.out.println("2");
          return false;
        }
        else if (!((teleopMidPoints.getText() != null || !"".equals(midPoints.getText())) && containsOnlyNumbers(midPoints.getText())) ){
            System.out.println("3");
          return false;
        }
        else if (!((teleopLowPoints.getText() != null || !"".equals(lowPoints.getText())) && containsOnlyNumbers(lowPoints.getText())) ){
            System.out.println("4");
          return false;
        }
        else{
            return true;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jDialog2 = new javax.swing.JDialog();
        passwordForIPConfig = new javax.swing.JPasswordField();
        jDialog3 = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jDialog4 = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jTextField1 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel24 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        teamNumber = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        teamNotes = new javax.swing.JTextField();
        defence = new javax.swing.JSlider();
        robotsBalanced = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        midPoints = new javax.swing.JTextField();
        topPoints = new javax.swing.JTextField();
        lowPoints = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        operational = new javax.swing.JCheckBox();
        coopertition = new javax.swing.JCheckBox();
        britip = new javax.swing.JCheckBox();
        jTextField6 = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        workingAuton = new javax.swing.JCheckBox();
        tipInAuton = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        teleopLowPoints = new javax.swing.JTextField();
        teleopTopPoints = new javax.swing.JTextField();
        teleopMidPoints = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        balanced = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        matchNumber = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        absent = new javax.swing.JCheckBox();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("jLabel5");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 805, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(215, Short.MAX_VALUE))
        );

        jDialog2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                checkForPasswordForIPConfig(evt);
            }
        });

        passwordForIPConfig.setText("jPasswordField1");
        passwordForIPConfig.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                checkForPasswordForIPConfig(evt);
            }
        });

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(passwordForIPConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(passwordForIPConfig, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jDialog3.setBounds(new java.awt.Rectangle(0, 0, 1000, 500));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        jButton10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton10.setText("Print Standings");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(363, 363, 363))
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(333, Short.MAX_VALUE))
        );

        jDialog4.setBounds(new java.awt.Rectangle(70, 70, 630, 420));
        jDialog4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                teamNotesSelection(evt);
            }
        });
        jScrollPane2.setViewportView(jList1);

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                teamEnteredSearch(evt);
            }
        });

        jLabel22.setText("Type team to search for");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("1");

        jScrollPane4.setViewportView(jList2);

        jLabel24.setFont(new java.awt.Font("Tahoma", 2, 18)); // NOI18N
        jLabel24.setText("Notes On Teams");

        javax.swing.GroupLayout jDialog4Layout = new javax.swing.GroupLayout(jDialog4.getContentPane());
        jDialog4.getContentPane().setLayout(jDialog4Layout);
        jDialog4Layout.setHorizontalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog4Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jDialog4Layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel22)
                                .addGap(101, 101, 101)
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jDialog4Layout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jDialog4Layout.setVerticalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jDialog4Layout.createSequentialGroup()
                        .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDialog4Layout.createSequentialGroup()
                                .addGroup(jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel22))
                                .addGap(14, 14, 14)
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 162, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lookForPasswordBringUp(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setText("Auton Top");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autonTopPressed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 90, -1));

        jButton2.setText("Teleop Top");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teleopTopPressed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, -1, -1));

        jButton3.setText("Auton Mid");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autonMidPressed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 90, -1));

        jButton4.setText("Teleop Mid");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teleopMidPressed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, -1, -1));

        jButton5.setText("Auton Low");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autonLowPressed(evt);
            }
        });
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 90, -1));

        jButton6.setText("Teleop Low");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teleopLowPressed(evt);
            }
        });
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, -1, -1));

        teamNumber.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        getContentPane().add(teamNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 97, 32));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("TEAM #");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        teamNotes.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        getContentPane().add(teamNotes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 219, 94));

        defence.setMajorTickSpacing(1);
        defence.setMaximum(2);
        defence.setMinorTickSpacing(1);
        defence.setPaintLabels(true);
        defence.setPaintTicks(true);
        defence.setSnapToTicks(true);
        defence.setValue(0);
        getContentPane().add(defence, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 77, -1));

        robotsBalanced.setMajorTickSpacing(1);
        robotsBalanced.setMaximum(3);
        robotsBalanced.setMinorTickSpacing(1);
        robotsBalanced.setPaintLabels(true);
        robotsBalanced.setPaintTicks(true);
        robotsBalanced.setSnapToTicks(true);
        robotsBalanced.setValue(0);
        getContentPane().add(robotsBalanced, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 180, 77, -1));

        jLabel2.setText("Defence Quality");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, -1, -1));

        jLabel4.setText("# of Bots balanced (including them)");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 160, -1, -1));

        midPoints.setText("0");
        getContentPane().add(midPoints, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 170, 59, -1));

        topPoints.setText("0");
        getContentPane().add(topPoints, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 60, -1));

        lowPoints.setText("0");
        lowPoints.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lookForPasswordBringUp(evt);
            }
        });
        getContentPane().add(lowPoints, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 59, -1));

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setText("SUBMIT");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitPressed(evt);
            }
        });
        getContentPane().add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 120, 40));

        jButton8.setText("View Info On Teams");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 510, 160, 30));

        operational.setSelected(true);
        operational.setText("Robot Operated throughout match");
        getContentPane().add(operational, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, -1, -1));

        coopertition.setText("Balanced on Coopertition Bridge");
        getContentPane().add(coopertition, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 250, -1, 20));

        britip.setText("Robot tipped from bridge");
        getContentPane().add(britip, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 280, -1, 20));

        jTextField6.setText("169.254.161.239");
        jTextField6.setEnabled(false);
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 480, 70, -1));

        jButton9.setText("View Current Standings");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, 160, 31));

        workingAuton.setText("Has a Working Auton");
        getContentPane().add(workingAuton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 170, -1));

        tipInAuton.setText("Tip Bridge in Auton");
        tipInAuton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tipInAutonActionPerformed(evt);
            }
        });
        getContentPane().add(tipInAuton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 130, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, 10, 350));

        teleopLowPoints.setText("0");
        getContentPane().add(teleopLowPoints, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, 60, -1));

        teleopTopPoints.setText("0");
        getContentPane().add(teleopTopPoints, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, 60, -1));

        teleopMidPoints.setText("0");
        getContentPane().add(teleopMidPoints, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 60, -1));
        getContentPane().add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 730, 10));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 70, 10, 350));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("AUTON");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("TELEOP");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 70, -1, -1));

        balanced.setText("Balanced");
        getContentPane().add(balanced, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 110, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("END GAME");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 70, -1, -1));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 70, 10, 350));

        matchNumber.setText("1");
        getContentPane().add(matchNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 40, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Match #");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, -1, -1));

        absent.setText("Were they Absent?");
        getContentPane().add(absent, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 20, -1, -1));
        getContentPane().add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 730, 10));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Notes on Team");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 540, -1, -1));

        jLabel3.setText("2 - Defense effective on");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 380, -1, -1));

        jLabel6.setText("0 - Didn't Play /");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 310, -1, -1));

        jLabel12.setText("1 - Defense only");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, -1, -1));

        jLabel13.setText("  Ineffective Defense");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 320, -1, -1));

        jLabel14.setText("  effective on dumper");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 350, -1, -1));

        jLabel15.setText("  robot");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 360, -1, -1));

        jLabel16.setText("  mobile shooting robot");
        getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 390, -1, -1));

        jLabel17.setText("Programming and Creation");
        getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 450, -1, -1));

        jLabel18.setText("By Griffin Della Grotte");
        getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 470, -1, -1));

        jLabel19.setText("Networking Consulting");
        getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 490, 140, -1));

        jLabel20.setText("  By John Westhoff");
        getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 510, -1, -1));

        jLabel21.setText("All of FRC Team 2607");
        getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 530, -1, -1));

        jButton11.setText("View Team Notes");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 470, 160, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void autonTopPressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autonTopPressed
        int d = Integer.valueOf(topPoints.getText());
        d += 6;
        topPoints.setText(""+d);
    }//GEN-LAST:event_autonTopPressed

    private void teleopTopPressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teleopTopPressed
        int d = Integer.valueOf(teleopTopPoints.getText());
        d += 3;
        teleopTopPoints.setText(""+d);
    }//GEN-LAST:event_teleopTopPressed

    private void autonMidPressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autonMidPressed
       int d = Integer.valueOf(midPoints.getText());
        d += 5;
        midPoints.setText(""+d);
    }//GEN-LAST:event_autonMidPressed

    private void teleopMidPressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teleopMidPressed
       int d = Integer.valueOf(teleopMidPoints.getText());
        d += 2;
        teleopMidPoints.setText(""+d);
    }//GEN-LAST:event_teleopMidPressed

    private void autonLowPressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autonLowPressed
       int d = Integer.valueOf(lowPoints.getText());
        d += 4;
        lowPoints.setText(""+d);
    }//GEN-LAST:event_autonLowPressed

    private void teleopLowPressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teleopLowPressed
       int d = Integer.valueOf(teleopLowPoints.getText());
        d += 1;
        teleopLowPoints.setText(""+d);
    }//GEN-LAST:event_teleopLowPressed

    private void submitPressed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitPressed
        if (checkTheFieldsAreValid()){
           // ScoutPacket ofDoom = new ScoutPacket(1,1,1,1,false,true,true,true,true,true,"FALSE",1,2,3);
        /*    ByteBuffer bb = ByteBuffer.allocate(2048);
                    bb.putInt(Integer.valueOf(teamNumber.getText()));
                    bb.putInt(Integer.valueOf(topPoints.getText()));
                    bb.putInt(Integer.valueOf(midPoints.getText()));
                    bb.putInt(Integer.valueOf(lowPoints.getText()));
                    bb.put(b)crossBar.isSelected(),
                    coopertition.isSelected(),
                    operational.isSelected(),
                    fouled.isSelected(),
                    ramtip.isSelected(),
                    britip.isSelected(),teamNotes.getText(),
                    balanceQuality.getValue(),
                    defence.getValue(),
                    robotsBalanced.getValue())*/
                    System.out.println(balanced.isSelected());
            ScoutPacket ofDoom = new ScoutPacket(//1,1,1,true,true,true,true,true,"",1,2,3);
                    Integer.valueOf(teamNumber.getText()),
                    (Integer.valueOf(topPoints.getText())+Integer.valueOf(midPoints.getText())+Integer.valueOf(lowPoints.getText())),
                    (Integer.valueOf(teleopMidPoints.getText())+Integer.valueOf(teleopLowPoints.getText())+Integer.valueOf(teleopTopPoints.getText())),
                    workingAuton.isSelected(),
                    tipInAuton.isSelected(),
                    operational.isSelected(),
                    balanced.isSelected(),
                    //false,
                    britip.isSelected(),
                    coopertition.isSelected(),
                    absent.isSelected(),
                    teamNotes.getText(),
                    1,
                    defence.getValue(),
                    robotsBalanced.getValue());
            /*String dataString = teamNumber.getText()+";"+topPoints.getText()+";"+midPoints.getText()+";"+lowPoints.getText()+";"+String.valueOf(crossBar.isSelected())+";"+
                    String.valueOf(coopertition.isSelected())+";"+String.valueOf(operational.isSelected())+";"+String.valueOf(fouled.isSelected())+";"+
                    String.valueOf(ramtip.isSelected())+";"+String.valueOf(britip.isSelected())+";"+teamNotes.getText()+";"+
                    balanceQuality.getValue() +";"+ defence.getValue() +";"+ robotsBalanced.getValue();*/
           // JOptionPane.showInputDialog(dataString);*/
   
            try {
              //  sendToDatabase(dataString);
                sendToDatabase(ofDoom);
                Thread.sleep(500);
                          teamNumber.setText("");
                    topPoints.setText("0");
                    midPoints.setText("0");
                    lowPoints.setText("0");
                    teleopLowPoints.setText("0");
                     teleopTopPoints.setText("0");
                      teleopMidPoints.setText("0");
                    workingAuton.setSelected(false);
                    tipInAuton.setSelected(false);
                    balanced.setSelected(false);
                    absent.setSelected(false);
                    coopertition.setSelected(false);
                    operational.setSelected(true);
                    britip.setSelected(false);
                    teamNotes.setText("");
                    defence.setValue(0);
                    robotsBalanced.setValue(0);
                    matchNumber.setText(""+(Integer.valueOf(matchNumber.getText())+1));
            } catch (Exception ex) {
            }
            
        }
        else{
            jDialog1.setBounds(0, 0, 600, 150);
            jLabel5.setText("Please enter valid data");
            jDialog1.setVisible(true);
        }
        
        
    }//GEN-LAST:event_submitPressed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        try {
            // TODO add your handling code here:
            Socket clientSocket = new Socket(jTextField6.getText(), 4765);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(outToServer);
             ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
            oos.writeObject(new Request());
         oos.flush();
         Thread.sleep(1000);
                ints = (int[][]) inFromServer.readObject();
                notes = (String[]) inFromServer.readObject();
         //       System.out.println(ints[1][0]);

                
          /*
           *                             ints[clientSentence.getTeam()][0] += clientSentence.getTop();
            ints[clientSentence.getTeam()][1] += clientSentence.getMid();
            ints[clientSentence.getTeam()][2] += clientSentence.getLow();
            ints[clientSentence.getTeam()][3] += clientSentence.getBalance();
            ints[clientSentence.getTeam()][4] += clientSentence.getDefense();

            ints[clientSentence.getTeam()][5] += clientSentence.getBotsBalanced();

            ints[clientSentence.getTeam()][6] += (clientSentence.getCross()?1:0);
            ints[clientSentence.getTeam()][7] += (clientSentence.getCoopertition()?1:0);
            ints[clientSentence.getTeam()][8] += (clientSentence.getFunctioning()?1:0);
            ints[clientSentence.getTeam()][9] += (clientSentence.getFouled()?-1:0);
            ints[clientSentence.getTeam()][10] += (clientSentence.getTippedFromRobot()?-1:0);
            ints[clientSentence.getTeam()][11] += (clientSentence.getTippedFromBridge()?1:0);
             notes[clientSentence.getTeam()] += clientSentence.getNotes();
            
           */
               /* 
                String message;
                message = "Team:   Top    Mid    Low    Bal    Def    Bob    Cro    Coo    Funn    Fou    Rob   Bri\n";
                for (int i = 1; i <= 60; i ++)
                {
                    message += i+"     ";
                    for (int e = 0; e <= 11; e++)
                    {
                    message += ints[i][e]+"   ";
                    }
                    message += "\n";
                }
                */
            new NewJFrame(ints,headers).setVisible(true);
            oos.close();

            inFromServer.close();
            //     modifiedSentence = inFromServer.readLine();
            //System.out.println("FROM SERVER: " + modifiedSentence);
            // inFromServer.close();
            clientSocket.close();
        }
        catch (Exception ex) {
            Logger.getLogger(InterfaceScout.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void checkForPasswordForIPConfig(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_checkForPasswordForIPConfig
        if (String.valueOf(passwordForIPConfig.getPassword()).equals("UnlockIP")) {
            jTextField6.setEnabled(true);
        }
        if (String.valueOf(passwordForIPConfig.getPassword()).equals("LockIP")) {
            jTextField6.setEnabled(false);
        }
    }//GEN-LAST:event_checkForPasswordForIPConfig

    private void lookForPasswordBringUp(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lookForPasswordBringUp
        if (evt.isShiftDown() && evt.isAltDown() && evt.isControlDown()){
            jTextField6.setEnabled(true);

        }
        if (lowPoints.getText().equals("lock")){
            jTextField6.setEnabled(false);
        }
    }//GEN-LAST:event_lookForPasswordBringUp

    private void tipInAutonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tipInAutonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tipInAutonActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        Integer places[][] = new Integer[54][1];
        try{
        Socket clientSocket = new Socket(jTextField6.getText(), 4765);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        ObjectOutputStream oos = new ObjectOutputStream(outToServer);
        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
        oos.writeObject(new Request());
        oos.flush();
        Thread.sleep(300);
        
        ints = (int[][]) inFromServer.readObject();
        notes = (String[]) inFromServer.readObject();
        }
         catch (Exception ex) {
            Logger.getLogger(InterfaceScout.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        Integer[][] temp = new Integer[ints.length][14];
        Integer[][] tempFiltered = new Integer[teams.length][14];

        for (int foo=0; foo < ints.length; foo++){
            int placeholder = 0;
            for (int fooey = 0; fooey < ints[0].length; fooey++){
                if (fooey != 2){
                temp[foo][placeholder] = Integer.valueOf(ints[foo][fooey]);
                placeholder++;
                }
            }
        }
        for (int foo=0; foo<ints.length;foo++){
            temp[foo][11] = Integer.valueOf(foo);
            System.out.println(temp[foo][11]);
        }
        
        int placeholder = 0;
        for (int i = 0;i< temp.length;i++){
            if (Arrays.asList(teams).contains(temp[i][11]))
            {
                tempFiltered[placeholder] = temp[i];
                System.out.println(tempFiltered[placeholder][11]);
                placeholder++;
            }
        }
        
        placeholder = 0;
        for (int i = 0; i<tempFiltered.length; i++)
        {
            places[i][0] = i+1;
            points[i][0] = tempFiltered[i][11];
            points[i][1] = tempFiltered[i][0] + tempFiltered[i][1] + tempFiltered[i][3] + tempFiltered[i][4] + (tempFiltered[i][6]*2) + (tempFiltered[i][7]*6)+ (tempFiltered[i][9]*2);
            points[i][2] = tempFiltered[i][0] + tempFiltered[i][1];
            points[i][3] = tempFiltered[i][4];
            points[i][4] = tempFiltered[i][3];
        }
                      
        jTable1.setModel(new javax.swing.table.DefaultTableModel(points,new Object[] {"Team", "Rank Points","Ball Points","Balance Score","Defense Score"}){
        public Class getColumnClass(int column) {
        Class returnValue;
        if ((column >= 0) && (column < getColumnCount())) {
          returnValue = getValueAt(0, column).getClass();
        } else {
          returnValue = Object.class;
        }
        return returnValue;
      }
    }
);      
        jTable2.setModel(new javax.swing.table.DefaultTableModel(places,new Object[] {"Place"}){
        public Class getColumnClass(int column) {
        Class returnValue;
        if ((column >= 0) && (column < getColumnCount())) {
          returnValue = getValueAt(0, column).getClass();
        } else {
          returnValue = Object.class;
        }
        return returnValue;
      }
    }
);
        jTable1.setAutoCreateRowSorter(true);
        jScrollPane1.getVerticalScrollBar().setModel(jScrollPane3.getVerticalScrollBar().getModel());
        jDialog3.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
                ArrayList teamsAsString;
        teamsAsString = new ArrayList (Arrays.asList(teams));
        MessageFormat top = new MessageFormat("ROBOVIKINGS SCOUTING LEADERBOARD PAGE");
        MessageFormat bottom = new MessageFormat(teamsAsString.toString());
        jTable1.setAutoCreateRowSorter(true);
        DefaultRowSorter sorter = ((DefaultRowSorter)jTable1.getRowSorter());
        ArrayList list = new ArrayList();
        list.add( new RowSorter.SortKey(1, SortOrder.DESCENDING) );
        sorter.setSortKeys(list);
        sorter.sort();
        try {
            jTable1.print(JTable.PrintMode.FIT_WIDTH, top, bottom );
        } catch (PrinterException ex) {
            Logger.getLogger(InterfaceScout.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        try{
        Socket clientSocket = new Socket(jTextField6.getText(), 4765);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        ObjectOutputStream oos = new ObjectOutputStream(outToServer);
        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
        oos.writeObject(new Request());
        oos.flush();
        Thread.sleep(300);
        
        ints = (int[][]) inFromServer.readObject();
        notes = (String[]) inFromServer.readObject();
        }
         catch (Exception ex) {
            Logger.getLogger(InterfaceScout.class.getName()).log(Level.SEVERE, null, ex);
        }
        List teamsSorted = Arrays.asList(teams);
        Collections.sort(teamsSorted);
        jList1.setListData(teamsSorted.toArray());
        jDialog4.setVisible(true);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void teamNotesSelection(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_teamNotesSelection
        if (jList1.getSelectedValue() != null){
            jLabel23.setText(String.valueOf(jList1.getSelectedValue()));
        }
        else{
            jLabel23.setText("0000000");
        }
        if (notes[Integer.valueOf(String.valueOf(jLabel23.getText()))] != null){
            String notesRevised[] = new String[80];
            ArrayList notesRevised2 = new ArrayList();
            for (int i = 0; i<notes[Integer.valueOf(String.valueOf(jList1.getSelectedValue()))].split("~").length-1; i++){
                notesRevised[i] = notes[Integer.valueOf(String.valueOf(jList1.getSelectedValue()))].split("~")[i+1];
            }
            for (int i = 0; i<notesRevised.length; i++){
                if (notesRevised[i] != null && !notesRevised[i].equals("")){
                    notesRevised2.add(notesRevised[i]);
                }  
            }
            jList2.setListData(notesRevised2.toArray());
        }
        else{
            jList2.setModel(new DefaultListModel());
        }
    }//GEN-LAST:event_teamNotesSelection

    private void teamEnteredSearch(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_teamEnteredSearch
        if (!jTextField1.getText().equals("") && Arrays.asList(teams).contains(Integer.valueOf(String.valueOf(jTextField1.getText()))))
        {
            jList1.setSelectedValue(Integer.valueOf(String.valueOf(jTextField1.getText())),true);
        }
    }//GEN-LAST:event_teamEnteredSearch

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if (theme.equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfaceScout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfaceScout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfaceScout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfaceScout.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new InterfaceScout().setVisible(true);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox absent;
    private javax.swing.JCheckBox balanced;
    private javax.swing.JCheckBox britip;
    private javax.swing.JCheckBox coopertition;
    private javax.swing.JSlider defence;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JDialog jDialog4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField lowPoints;
    private javax.swing.JTextField matchNumber;
    private javax.swing.JTextField midPoints;
    private javax.swing.JCheckBox operational;
    private javax.swing.JPasswordField passwordForIPConfig;
    private javax.swing.JSlider robotsBalanced;
    private javax.swing.JTextField teamNotes;
    private javax.swing.JTextField teamNumber;
    private javax.swing.JTextField teleopLowPoints;
    private javax.swing.JTextField teleopMidPoints;
    private javax.swing.JTextField teleopTopPoints;
    private javax.swing.JCheckBox tipInAuton;
    private javax.swing.JTextField topPoints;
    private javax.swing.JCheckBox workingAuton;
    // End of variables declaration//GEN-END:variables

    public void run() {
                        while (true)
                {try {
                    if (teamNotes.getText().contains("~"))
                    {
                       teamNotes.setText(teamNotes.getText().replaceAll("~","")); 
                    }
                     if (robotsBalanced.getValue()>0)
                     {
                         balanced.setSelected(true);
                     }
                     if (!topPoints.hasFocus())
                     {
                         if (topPoints.getText().isEmpty())
                         {
                             topPoints.setText("0");
                         }
                     }
                     if (!lowPoints.hasFocus())
                     {
                         if (lowPoints.getText().isEmpty())
                         {
                             lowPoints.setText("0");
                         }
                     }
                     if (!midPoints.hasFocus())
                     {
                         if (midPoints.getText().isEmpty())
                         {
                             midPoints.setText("0");
                         }
                     }
                     if (!teleopTopPoints.hasFocus())
                     {
                         if (teleopTopPoints.getText().isEmpty())
                         {
                             teleopTopPoints.setText("0");
                         }
                     }
                     if (!teleopMidPoints.hasFocus())
                     {
                         if (teleopMidPoints.getText().isEmpty())
                         {
                             teleopMidPoints.setText("0");
                         }
                     }
                     if (!teleopLowPoints.hasFocus())
                     {
                         if (teleopLowPoints.getText().isEmpty())
                         {
                             teleopLowPoints.setText("0");
                         }
                     }
                   if (!(topPoints.hasFocus()||lowPoints.hasFocus()||midPoints.hasFocus()))
                   {


                   if ((Integer.valueOf(topPoints.getText())+Integer.valueOf(midPoints.getText())+Integer.valueOf(lowPoints.getText()))>0)
                   {
                    workingAuton.setSelected(true);
                   }
                   }
                Thread.sleep(20);
            } catch (Exception ex) {
                Logger.getLogger(InterfaceScout.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
    }
}
