package scoutclient;
import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
public class ServerEditor extends javax.swing.JFrame
{
    String headers[] = {"Team#","Auton","Teleop","Absents","Matches","Defense","Bots Balanced","Working Auton","Tip in Auton","Functioning","Balanced Bridge","Coop Score","Tipped - Bridge"}; //"Team:   Top    Mid    Low    Bal    Def    Bob    Cro    Coo    Funn    Fou    Rob   Bri\n";
    Integer teams[] = TCPServer.teams;///{11,25,56,75,222,223,224,225,272,303,316,341,357,365,423,433,484,486,708,714,816,834,869,1089,1168,1218,1279,1302,1370,1391,1403,1626,1640,1647,1672,1676,1712,1811,1923,2016,2180,2234,2559,2590,2607,2729,3142,3314,3515,3637,3929,3974,4342};
        Integer data[][] = new Integer[60][13];

    public ServerEditor(Integer[][] walrus)
    {
        data = walrus;
        initComponents();
        jTable1.setModel(new DefaultTableModel(data,headers));
        this.requestFocus();
    }
        public ServerEditor(int[][] walrus)
    {
        Integer waffle[][] = new Integer[teams.length][walrus[0].length+1];
for (int i = 0; i < walrus[0].length; i ++)
{
    for (int e = 0; e < teams.length; e ++)
{
    waffle[e][i+1] = walrus[teams[e]][i];
}
}       
        data = waffle;
       // Arrays.sort(teams);
        for (int ti = 0; ti < teams.length; ti++)
        {
            data[ti][0] = teams[ti];
        }
        initComponents();
        jTable1.setModel(new DefaultTableModel(data,headers));
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "null"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("SAVE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1044, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(393, 393, 393)
                .addComponent(jButton1)
                .addContainerGap(594, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        System.out.println(jTable1.getValueAt(0,1));
        for (int x = 0; x < jTable1.getRowCount(); x++)
        {
           for (int y = 1; y < jTable1.getColumnCount(); y++)
            {
                
                TCPServer.ints[teams[x]][y-1]=Integer.valueOf(""+(jTable1.getValueAt(x, y)));
            } 
        }
        TCPServer.writeToFile(TCPServer.GW);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}