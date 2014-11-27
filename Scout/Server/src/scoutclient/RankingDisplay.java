package scoutclient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultRowSorter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
public class RankingDisplay extends javax.swing.JFrame implements Runnable
{
    String headers[] = {"Position","Team", "Rank Points","Ball Points","Balance Points","Defense Points"}; //"Team:   Top    Mid    Low    Bal    Def    Bob    Cro    Coo    Funn    Fou    Rob   Bri\n";
    Integer teams[] = TCPServer.teams; //=// {11,25,56,75,222,223,224,225,272,303,316,341,357,365,423,433,484,486,708,714,816,834,869,1089,1168,1218,1279,1302,1370,1391,1403,1626,1640,1647,1672,1676,1712,1811,1923,2016,2180,2234,2559,2590,2607,2729,3142,3314,3515,3637,3929,3974,4342};
        Integer data[][] = new Integer[60][13];
Thread me;
Integer eee[][] = new Integer[teams.length][5];
    public RankingDisplay(Integer[][] walrus)
    {
        data = walrus;
        initComponents();
       // jPanel1.setDoubleBuffered(true);
        displaying = new DefaultTableModel(eee,headers){
        public Class getColumnClass(int column) {
        Class returnValue;
        if ((column >= 0) && (column < getColumnCount())) {
          returnValue = getValueAt(0, column).getClass();
        } else {
          returnValue = Object.class;
        }
        return returnValue;
      }
    };
        jTable1.setModel(displaying);
        this.requestFocus();
        me = new Thread(this);
        me.start();
    }
    DefaultTableModel displaying;
        public RankingDisplay(int[][] walrus)
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
        displaying = new DefaultTableModel(eee,headers);
        jTable1.setModel(displaying);
       // jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.requestFocus();
        me = new Thread(this);
        me.start();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setAutoscrolls(false);
        jTable1.setEnabled(false);
        jTable1.setFocusable(false);
        jTable1.setRowSelectionAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setUpdateSelectionOnSort(false);
        jScrollPane1.setViewportView(jTable1);

        jScrollPane2.setViewportView(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 935, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
int etet = 0;
int rowrowrow = 0;
int pongpower = 1;
    public void run() {
     
       while (true)
       {

    
   etet = 0;
              jTable1.scrollRectToVisible(jTable1.getCellRect(rowrowrow+=pongpower, 0, false));
              
             if (rowrowrow>=teams.length||rowrowrow<=0)
              {
                  pongpower*=-1;
              }
          /*
              *                  woah[0] = clientSentence.getTeam();
                 woah[1] = clientSentence.getAutonPoints();
                 woah[2] = clientSentence.getTeleopPoints();
                 woah[3] = clientSentence.getAbsent();
                 woah[4] = clientSentence.getHasAuton();
                 woah[5] = clientSentence.getTipInAuton();
                 woah[6] = clientSentence.getFunctioning();
                 woah[7] = clientSentence.getBalanced();
                 woah[8] = clientSentence.getCooperated();
                 woah[9] = clientSentence.getTippedFromBridge();
                 woah[10] = clientSentence.getBalance();
                 woah[11] = clientSentence.getDefense();
                 woah[12] = clientSentence.getBotsBalanced();
                 woah[13] = clientSentence.getNotes();
              */
           data = new Integer[teams.length][6];
               for (int e = 0; e < teams.length; e ++)
{
    int fe;
              //  eee[i][1] = tempFiltered[i][0] + tempFiltered[i][1] + tempFiltered[i][3] + tempFiltered[i][4] + (tempFiltered[i][6]*2) + (tempFiltered[i][7]*6)+ (tempFiltered[i][9]*2);
   data[e][0] = e;
    data[e][1] = teams[e];
   data[e][2] = (TCPServer.ints[teams[e]][0]+TCPServer.ints[teams[e]][1]+TCPServer.ints[teams[e]][4]+TCPServer.ints[teams[e]][5]+TCPServer.ints[teams[e]][7]*2+TCPServer.ints[teams[e]][8]*6+TCPServer.ints[teams[e]][10]*2);
   data[e][3] = (TCPServer.ints[teams[e]][0]+TCPServer.ints[teams[e]][1]);
   data[e][4] = TCPServer.ints[teams[e]][5];
   data[e][5] = (TCPServer.ints[teams[e]][4]);
} //{"Team", "Rank eee","Ball eee","Balance Score","Defense Score"};
           try
           {
                  jTable1.setEditingColumn(-1);

        int n = data.length;
        boolean doMore = true;
        while (doMore) {
            n--;
            doMore = false;  // assume this is our last pass over the array
            for (int i = 0; i < n; i++) {
                int x1 = data[i][2];
                int x2 = data[i+1][2];
                if (x1 < x2) {
                    // exchange elements
                    Integer[] temp = data[i];
                    data[i] = data[i+1];
                    data[i+1] = temp;
                    doMore = true;  // after an exchange, must look again
                }
            }
        }

    for (int e = 0; e < teams.length; e ++)
{
    int fe;
     data[e][0] = e+1;
              //  eee[i][1] = tempFiltered[i][0] + tempFiltered[i][1] + tempFiltered[i][3] + tempFiltered[i][4] + (tempFiltered[i][6]*2) + (tempFiltered[i][7]*6)+ (tempFiltered[i][9]*2);
    displaying.setValueAt(data[e][0],e,0);
displaying.setValueAt(data[e][1],e,1);
displaying.setValueAt(data[e][2],e,2);
displaying.setValueAt(data[e][3],e,3);
displaying.setValueAt(data[e][4],e,4);
displaying.setValueAt(data[e][5],e,5);
} jTable1.setEditingColumn(-1);
   jTable1.setEditingRow(-1);/*
 Integer places[][] = new Integer[teams.length][1];
    Integer[][] temp = new Integer[TCPServer.ints.length][14];
        Integer[][] tempFiltered = new Integer[teams.length][14];

        for (int foo=0; foo < TCPServer.ints.length; foo++){
            int placeholder = 0;
            for (int fooey = 0; fooey < TCPServer.ints[0].length; fooey++){
                if (fooey != 2){
                temp[foo][placeholder] = Integer.valueOf(TCPServer.ints[foo][fooey]);
                placeholder++;
                }
            }
        }
        for (int foo=0; foo<TCPServer.ints.length;foo++){
            temp[foo][11] = Integer.valueOf(foo);
         //   System.out.println(temp[foo][11]);
        }
        
        int placeholder = 0;
        for (int i = 0;i< temp.length;i++){
            if (Arrays.asList(teams).contains(temp[i][11]))
            {
                tempFiltered[placeholder] = temp[i];
              //  System.out.println(tempFiltered[placeholder][11]);
                placeholder++;
            }
        }
            
         jTable1.setEditingColumn(-1);
   jTable1.setEditingRow(-1);
        placeholder = 0;
        for (int i = 0; i<tempFiltered.length; i++)
        {
            places[i][0] = i+1;
            eee[i][0] = tempFiltered[i][11];
            eee[i][1] = tempFiltered[i][0] + tempFiltered[i][1] + tempFiltered[i][3] + tempFiltered[i][4] + (tempFiltered[i][6]*2) + (tempFiltered[i][7]*6)+ (tempFiltered[i][9]*2);
            eee[i][2] = tempFiltered[i][0] + tempFiltered[i][1];
            eee[i][3] = tempFiltered[i][4];
            eee[i][4] = tempFiltered[i][3];
        }
                      
                               jTable1.setModel(new javax.swing.table.DefaultTableModel(eee,headers)
                {
                    @Override
                            public Class getColumnClass(int column) {
        Class returnValue;
        if ((column >= 0) && (column < getColumnCount())) {
          returnValue = getValueAt(0, column).getClass();
        } else {
          returnValue = Object.class;
        }
        return returnValue;
      }
                });
 jTable1.setEditingColumn(-1);
   jTable1.setEditingRow(-1);
                                 // displaying = ety;

                    //  jTable1.setUpdateSelectionOnSort(false);
                      
                 //  jTable1.setModel(null);          
                      

   jTable1.setEditingColumn(-1);
   jTable1.setEditingRow(-1);
    //displaying.setDataVector(eee, headers);
   //  jTable1.setModel(displaying);
     jTable1.setAutoCreateRowSorter(true);
          DefaultRowSorter sorter = ((DefaultRowSorter)jTable1.getRowSorter());
        ArrayList list = new ArrayList();
        list.add( new RowSorter.SortKey(1, SortOrder.DESCENDING) );
        sorter.setSortKeys(list);
        sorter.sort();
               jTable1.setEditingColumn(-1);
   jTable1.setEditingRow(-1);
    //{"Team", "Rank eee","Ball eee","Balance Score","Defense Score"};
//{"Team#","Auton","Teleop","Absents","Matches","Defense","Bots Balanced","Working Auton","Tip in Auton","Functioning","Balanced Bridge","Coop Score","Tipped - Bridge"};       
        data = waffle;
       // Arrays.sort(teams);*/
            try {
                Thread.sleep(250);
            }
            catch (Exception ex) {
               Logger.getLogger(RankingDisplay.class.getName()).log(Level.SEVERE, null, ex);
            }
       }
       catch (Exception waffles)
       {
          waffles.printStackTrace();
       }
       }}
   
    
}