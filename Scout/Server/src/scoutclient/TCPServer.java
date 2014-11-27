/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scoutclient;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.net.*;
import java.nio.channels.FileChannel;
import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFrame;
/**
 *
 * @author Griffin
 */
public class TCPServer implements Runnable{
static Thread me;
public static File GW;
static String dir = new File("C:\\Documents and Settings\\All Users\\Documents\\").exists()?"C:\\Documents and Settings\\All Users\\Documents\\":"C:\\";
private static class autoBackup implements Runnable
{
        Thread global;
        public autoBackup()
        {
            global = new Thread(this);
            global.start();
        }
        public void run() {
            while (true)
            {
                try
                {
                    Thread.sleep(10*60*1000);
                }
                catch (InterruptedException ex) 
                {
                }
                System.out.println("Backup saved as "+backUp());
            }
        }

}
private static class Part2 implements Runnable
{
    static Thread part2;
    public Part2()
    {
        part2 = new Thread(this);
        part2.start();
        GW = new File("C:\\Users\\Public\\Documents\\_ScoutFormNumber3.esBroken");
    }

        public void run() {
            try {
                System.out.println("Server initializing...");
                System.out.print('\u0007');
                me = new Thread(new TCPServer());
                me.start();
                TCPServer.SQLITEload();
              ScoutPacket clientSentence;
              ScoutPacket capitalizedSentence;

              ServerSocket welcomeSocket = new ServerSocket(4765);

              System.out.println("Socket opened!");
                          
                             
               TCPServer.SQLITEload();  
               System.out.println("Backup saved as "+backUp());
               System.out.println("Welcome to the RoboVikings scouting server!");
              /*   if (GW.exists())
                 {
                     System.out.println("Data file found! Reading...");
                     try {
                         ObjectInputStream input = new ObjectInputStream(new FileInputStream(GW));
                         ints = (int[][])input.readObject();
                         notes = (String[])input.readObject();
                         System.out.println("Data file loaded!");
                         
                     }
                     catch (IOException ex) {
                         Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                     }
                     catch (ClassNotFoundException ex) {
                         Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                     }
                  }*/
              
              while(true)
              {
              try
                      {
                 Socket connectionSocket = welcomeSocket.accept();
                 System.out.println("Socket opened! Starting input stream...");
                 Thread.sleep(10);
                ObjectInputStream inFromClient =
                    new ObjectInputStream(connectionSocket.getInputStream());
                System.out.println("ObjectInputStream opened! Starting output stream...");
                Thread.sleep(10);
                ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());
                System.out.println("ObjectOutputStream opened! Reading packet...");
                                Thread.sleep(10);
                 Serializable waffle = (Serializable)inFromClient.readObject();
                 System.out.println("Packet read! Identifying...");
                                 Thread.sleep(10);
                // clientSentence = new ScoutPacket();
                 if (waffle instanceof ScoutPacket)
                 {
                     clientSentence = (ScoutPacket) waffle;
                     System.out.println("ScoutPacket recieved! Unloading packet...");
                     
                 Object [] woah = new Object[14];
                 /* Array element Values in order (0 down to the length of woah)
                  * - Team Number
                  * - Top Hoop points
                  * - Middle Hoop points
                  * - Low Hoop points
                  * - Did they cross the barrier
                  * - Did they coopertition balance
                  * - Did the robot operate seamlessly (True by default)
                  * - Did they commit a foul
                  * - Did they tip from a robot impact
                  * - Did they tip while tryting to balance
                  * - Quality of Balance
                  * - Quality of Defense
                  * - # of bot balanced (with your robot)
                  */
                 woah[0] = clientSentence.getTeam();
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
                 System.out.println("Packet unloaded!");
                 
                 System.out.println("Received: Team " + woah[0] +
                         "\nAuton points: " + woah[1] +
                         "\nTeleop points: " + woah[2] +
                         "\nDo They have a working Auton: " + woah[4] +
                         "\nDo they tip the briidge in Auton: " + woah [5] +
                         "\nDid the robot fail at all during the match: " + woah[6] +
                         "\nHow many times did they robot balance: " + woah[7] +
                         "\nThey cooperated: " + woah[8] +
                         "\nThey were absent" + woah[3] +
                         "\nDid they tip while trying to balance a bridge: " + woah[9] +
                         "\nHow well did they balance: " + woah[10] +
                         "\nHow many did they balance with: " + woah[11] +
                         "\nHow good was their defense: " + woah[12]+
                         "\nNOTES: "+woah[13]);
                // capitalizedSentence = clientSentence.toUpperCase();
     //            outToClient.writeBytes(capitalizedSentence);
      //           outToClient.flush();
       //          outToClient.close();        
              //   System.out.println(ints[clientSentence.getTeam()][0]);
               //  System.out.println(clientSentence.getAutonPoints());
                 System.out.println("Saving data...");
                     TCPServer.SQLITEadd(clientSentence.getTeam(), clientSentence.getAutonPoints(), clientSentence.getTeleopPoints(), (clientSentence.getAbsent()?1:0), clientSentence.getBalance(), clientSentence.getDefense(), clientSentence.getBotsBalanced(), (clientSentence.getHasAuton()?1:0), (clientSentence.getTipInAuton()?1:0), (clientSentence.getFunctioning()?1:-1), (clientSentence.getBalanced()?1:0), (clientSentence.getCooperated()?1:0), (clientSentence.getTippedFromBridge()?1:0),"~"+ clientSentence.getNotes());    
       
                 ints[clientSentence.getTeam()][0] += clientSentence.getAutonPoints();
                 ints[clientSentence.getTeam()][1] += clientSentence.getTeleopPoints();
                 
                 if (clientSentence.getAbsent() == true){
                 ints[clientSentence.getTeam()][2] += (clientSentence.getAbsent()?1:0);
                 }
                 
                 ints[clientSentence.getTeam()][3] += clientSentence.getBalance();
                 
                 ints[clientSentence.getTeam()][4] += clientSentence.getDefense();

                 ints[clientSentence.getTeam()][5] += clientSentence.getBotsBalanced();
                 
                 if (clientSentence.getHasAuton() == true){
                 ints[clientSentence.getTeam()][6] += (clientSentence.getHasAuton()?1:0);
                 }
                 
                 ints[clientSentence.getTeam()][7] += (clientSentence.getTipInAuton()?1:0);
                 
                 if (clientSentence.getFunctioning() == false){
                 ints[clientSentence.getTeam()][8] += (clientSentence.getFunctioning()?1:-1);
                 }
                 
            //     System.out.println((clientSentence.getBalanced()?1:0));
                 if (clientSentence.getBalanced() == true){
                 ints[clientSentence.getTeam()][9] += (clientSentence.getBalanced()?1:0);   
                 }
                 
                 if (clientSentence.getCooperated() == true){
                 ints[clientSentence.getTeam()][10] += (clientSentence.getCooperated()?1:0);   
                 }
                 
                 if (clientSentence.getTippedFromBridge() == true){
                 ints[clientSentence.getTeam()][11] += (clientSentence.getTippedFromBridge()?1:0);
                 }
                 
                 
                 notes[clientSentence.getTeam()] += "~"+clientSentence.getNotes();
                 System.out.println("Data saved! Updating data file...");
//writeToFile(GW);
                  System.out.println("Data file updated!");
              }

              if (waffle instanceof Request)
              {
                  System.out.println("RequestPacket recieved! Sending integer information...");
               outToClient.writeObject(ints);
               
               System.out.println("Integers sent! Sending strings...");
                               Thread.sleep(10);
               outToClient.writeObject(notes);
               System.out.println("Strings sent! Waiting...");
               
               Thread.sleep(1000);
              }
              System.out.println("Closing output streams...");
                 outToClient.close();
                 System.out.println("Closing input streams...");
                 inFromClient.close();
                 System.out.println("Closing socket...");
                 connectionSocket.close();
                 System.out.println("Packet handled successfully.");
               }

                             catch (Exception e)
                             {
                                 System.err.println("Aww, something es broken!");
                                 System.err.println(e);
                             }
           }}
            catch (IOException ex) {
                System.err.println("Aww, something es broken!");
                                 System.err.println(ex);
            }
        }
    }
    /**
     * @param args the command line arguments
     */
        static int ints[][] = new int[6000][12];
        static String notes[] = new String[6000];
       public static void main(String argv[]) throws Exception
      {
      Part2 lol = new Part2();
        autoBackup becauseGriffinWorriesTooMuch = new autoBackup();
       }
       public static void writeToFile(File GWe)
       {
        ObjectOutputStream THETA = null;
        try {
            THETA = new ObjectOutputStream(new FileOutputStream(GWe));
            String previous = "";
            try {
                THETA.writeObject(ints);
            }
            catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                THETA.writeObject(notes);
            }
            catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            THETA.flush();
            THETA.close();
        }
        catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                THETA.close();
            }
            catch (IOException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       }
Scanner in = new Scanner(System.in);
JFrame whatwesee;
public static Integer teams[] = {11,25,56,75,222,223,224,225,272,303,316,341,357,365,423,433,484,486,708,714,816,834,869,1089,1168,1218,1279,1302,1370,1391,1403,1626,1640,1647,1672,1676,1712,1811,1923,2016,2180,2234,2559,2590,2607,2729,3142,3314,3515,3637,3929,3974,4342};

public static String backUp()
{
              Date goOut = new Date();
              
              String name = dir+"_ScoutFormNumber3_Backup"+((goOut.getMonth()+1))+"_"+((goOut.getDate()+1))+"~"+(goOut.getHours())+"_"+(goOut.getMinutes())+"_"+(goOut.getSeconds())+".esBroken.db";
              File GW2 = new File(name);
              File GW1 = new File("test.db");
        try {
            copyFile(GW1,GW2);
        } catch (IOException ex) {
            return ex.getMessage();
        }
              //writeToFile(GW2);
              return GW2.getName();
}
    public void run() {
      while (true)
      {
          try
          {
          String le = in.nextLine();
          le = le.toLowerCase();
          if ("rankings".equals(le))
          {
             whatwesee = new RankingDisplay(ints);
             whatwesee.setVisible(true);
          }
          else
          if ("edit".equals(le))
          {
             whatwesee = new ServerEditor(ints);
             whatwesee.setVisible(true);
          }
          else
          if ("exit".equals(le))
          {
              if (whatwesee!=null)
              whatwesee.setVisible(false);
          }
          else
          if ("kill".equals(le))
          {
              System.out.println("Good bye!");
              System.exit(0);
          }
          else
          if ("nullpointer".equals(le))
          {
              throw new NullPointerException(); 
          }
                    else
          if ("does 8 equal 3?".equals(le))
          {
              throw new NullPointerException("YES IT DOES!"); 
          }
          else
          if ("forcesave".equals(le))
          {
              writeToFile(GW);
              String warlfef = backUp();
              System.out.println("Save Complete! Backuped to "+warlfef);
          }
          else
          if ("help".equals(le))
          {
              System.out.println(""
                      + "help - Prints this page\n"
                      + "about - Prints the about page\n"
                      + "edit - Opens the server data editor GUI\n"
                      + "rankings - Opens the server ranking display GUI\n"
                      + "exit - Closes the server GUI\n"
                      + "kill - Kills the server\n"
                      + "nullpointer - Throws a NULLPOINTEREXCEPTION!\n"
                      + "forcesave - Backs up the data file\n"
                      + "predict - Guesses the outcome of a match\n"
                      + "score - Gives a team's RankPoints/Match\n"
                      + "reload - Reloads the database\n"
                      + "blacklist - Gives a team an absence\n"
                      + "notes - Displays all available notes on a team\n"
                      + "allnotes - Displays all notes\n"
                      + "copyallnotes - Copies all notes to the clipboard");
          }
          else
          if (le.startsWith("reload"))
          {
               SQLITEload();
          }
          else
          if (le.startsWith("score"))
          {
              try
              {
              int team = Integer.valueOf(le.replaceAll("score", "").replaceAll(" ",""));
              double scored = (TCPServer.ints[team][0]+TCPServer.ints[team][1]+TCPServer.ints[team][4]+TCPServer.ints[team][5]+TCPServer.ints[team][7]*2+TCPServer.ints[team][8]*6+TCPServer.ints[team][10]*2);
              scored /= Math.max(1,ints[team][3]);
              System.out.println(scored);
              }
              catch (Exception e)
              {
                  System.err.println("The Syntax Of This Command Is:\n"
                          + "SCORE teamNumber");
              }
          }
         else
          if (le.startsWith("blacklist"))
          {
              try
              {
              int team = Integer.valueOf(le.replaceAll("blacklist", "").replaceAll(" ",""));
                ints[team][2]++;
              }
              catch (Exception e)
              {
                  System.err.println("The Syntax Of This Command Is:\n"
                          + "SCORE teamNumber");
              }
          }
          else
                        if (le.startsWith("notes"))
          {
              try
              {
              int team = Integer.valueOf(le.replaceAll("notes", "").replaceAll(" ",""));
                if (notes[team]==null||notes[team].isEmpty())
                {
                    System.err.println("This team doesn't seem to have any notes yet...");
                    continue;
                }
                StringTokenizer globalWaffles = new StringTokenizer(notes[team],"~");
                globalWaffles.nextToken();
                while (globalWaffles.hasMoreTokens())
                {
                    System.out.println(globalWaffles.nextToken());
                }
              }
              catch (Exception e)
              {
                  System.err.println("The Syntax Of This Command Is:\n"
                          + "NOTES teamNumber");
              }
          }
          else
           if (le.startsWith("allnotes"))
          {
                   
                  for (int team = 0; team<6000; team++)
                  {
                      if (notes[team]!=null)
                      {
                          System.out.println("~~~Team "+(team)+"~~~");
                StringTokenizer globalWaffles = new StringTokenizer(notes[team],"~");
                globalWaffles.nextToken();
                while (globalWaffles.hasMoreTokens())
                {
                    System.out.println(globalWaffles.nextToken());
                }
                      }
                  }
              }
          else
           if (le.startsWith("copyallnotes"))
          {
                   String allTheNotes = "";
                  for (int team = 0; team<6000; team++)
                  {
                      if (Arrays.binarySearch(teams, team)>=0)
                      {
                          allTheNotes+="~~~Team "+(team)+"~~~\n";
                StringTokenizer globalWaffles = new StringTokenizer(notes[team],"~");
                globalWaffles.nextToken();
                while (globalWaffles.hasMoreTokens())
                {
                    allTheNotes+=globalWaffles.nextToken()+"\n";
                }
                      }
                  }
                  StringSelection ss = new StringSelection(allTheNotes);
                  Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
              }

          else
          if (le.startsWith("predict"))
          {
                try {
                    int bteam1score, bteam2score, bteam3score, rteam1score, rteam2score, rteam3score;
                    int blueScore, redScore;
                    int blueauton, redauton, blueteleop, redteleop, bluebridge, redbridge;
                    int bteam1, bteam2, bteam3, rteam1, rteam2, rteam3;
                                StringTokenizer findTheTeams = new StringTokenizer(le.replaceAll("predict "," ").replaceAll("	", " "));
                                if (findTheTeams.countTokens()!=6)
                                {
                                    System.err.println("The Syntax Of This Command Is:"
                                            + "\n PREDICT redTeam1 redTeam2 redTeam3 blueTeam1 blueTeam2 blueTeam3");
                                    continue;
                                }
                                                               rteam1 = Integer.valueOf(findTheTeams.nextToken());
                                rteam2 = Integer.valueOf(findTheTeams.nextToken());
                                rteam3 = Integer.valueOf(findTheTeams.nextToken());
                                
                                
                                bteam1 = Integer.valueOf(findTheTeams.nextToken());
                                bteam2 = Integer.valueOf(findTheTeams.nextToken());
                                bteam3 = Integer.valueOf(findTheTeams.nextToken());
 
                                bteam1score = (((ints[bteam1][0])/Math.max(1,ints[bteam1][3])));
                                bteam2score = (((ints[bteam2][0])/Math.max(1,ints[bteam2][3])));
                                bteam3score = (((ints[bteam3][0])/Math.max(1,ints[bteam3][3])));
                                           
                                rteam1score = (((ints[rteam1][0])/Math.max(1,ints[rteam1][3])));
                                rteam2score = (((ints[rteam2][0])/Math.max(1,ints[rteam2][3])));
                                rteam3score = (((ints[rteam3][0])/Math.max(1,ints[rteam3][3])));
                                 
                                blueauton = bteam1score+bteam2score+bteam3score;
                                redauton = rteam1score+rteam2score+rteam3score;

                                bteam1score = Math.max((((ints[bteam1][1])/Math.max(1,ints[bteam1][3]))-((8*ints[rteam1][4]))/Math.max(1,ints[rteam1][3])),0);
                                bteam2score = Math.max((((ints[bteam2][1])/Math.max(1,ints[bteam2][3]))-((8*ints[rteam2][4]))/Math.max(1,ints[rteam2][3])),0);
                                bteam3score = Math.max((((ints[bteam3][1])/Math.max(1,ints[bteam3][3]))-((8*ints[rteam3][4]))/Math.max(1,ints[rteam3][3])),0);
                                           
                                rteam1score = Math.max((((ints[rteam1][1])/Math.max(1,ints[rteam1][3]))-((8*ints[bteam1][4]))/Math.max(1,ints[bteam1][3])),0);
                                rteam2score = Math.max((((ints[rteam2][1])/Math.max(1,ints[rteam2][3]))-((8*ints[bteam2][4]))/Math.max(1,ints[bteam2][3])),0);
                                rteam3score = Math.max((((ints[rteam3][1])/Math.max(1,ints[rteam3][3]))-((8*ints[bteam3][4]))/Math.max(1,ints[bteam3][3])),0);
                                 
                                blueteleop = bteam1score+bteam2score+bteam3score;
                                redteleop = rteam1score+rteam2score+rteam3score;

                                redbridge = (int)((10*(ints[rteam1][5]+ints[rteam2][5]+ints[rteam3][5])/Math.max(ints[rteam1][3]+ints[rteam2][3]+ints[rteam3][3],1))*1.3);
                                bluebridge = (int)((10*(ints[bteam1][5]+ints[bteam2][5]+ints[bteam3][5])/Math.max(ints[bteam1][3]+ints[bteam2][3]+ints[bteam3][3],1))*1.3);
                                int bs = 0, rs = 0;
                                if (redbridge>=10) rs = 10;
                                if (redbridge>=15) rs = (new Random().nextBoolean())?10:20;
                                if (redbridge>=20) rs = 20;
                                if (redbridge>=21) rs = (new Random().nextBoolean())?20:40;
                                if (redbridge>=30) rs = 40;
                                redbridge = rs;
                                
                                if (bluebridge>=10) bs = 10;
                                if (bluebridge>=15) bs = (new Random().nextBoolean())?10:20;
                                if (bluebridge>=20) bs = 20;
                                if (bluebridge>=21) bs = (new Random().nextBoolean())?20:40;
                                if (bluebridge>=30) bs = 40;
                                bluebridge = bs;
                                redScore = redauton+redteleop+redbridge;
                                blueScore = blueauton+blueteleop+bluebridge;
                                
                                System.out.println("We have the green light!");
                                Thread.sleep(500);
                                System.out.println((new Random().nextBoolean())?"Drivers ready!":"Drivers behind the line...");
                                Thread.sleep(500);
                                System.out.println("In 3... 2... 1... Go!");
                                Thread.sleep(500);
                                if (blueauton>redauton)
                                {
                                System.out.println("The blue alliance taking an early "+(blueauton-redauton)+"-point lead!");
                                Thread.sleep(500);
                                }
                                else
                                {
                                    if (blueauton<redauton)
                                {
                                System.out.println("The red alliance taking an early "+(redauton-blueauton)+"-point lead!");
                                Thread.sleep(500);
                                }
                                    else
                                    {
                                        System.out.println("The score is tied at "+redauton+" at the end of the Hybrid period!");
                                        Thread.sleep(500);
                                    }
                                }
                                System.out.println("The drivers take control of their robots as we enter our teleoperated period!");
                                Thread.sleep(500);
                                if (blueteleop+blueauton>redteleop+redauton)
                                {
                                    int difference = ((blueauton+blueteleop)-(redteleop+redauton));
                                System.out.println("The blue alliance has the lead at the end of our teleoperated mode with "+(blueteleop+blueauton)+" points!");
                                Thread.sleep(500);
                                if (difference<=20)
                                {
                                    System.out.println("With the red alliance behind by only "+difference+"-points, it will come down to the endgame!");
                                    Thread.sleep(500);
                                }
                                }
                                else
                                {
                                    if (blueauton+blueteleop<redauton+blueteleop)
                                {
                                                int difference = ((redteleop+redauton)-(blueauton+blueteleop));
                                System.out.println("The red alliance has the lead at the end of our teleoperated mode with "+(redteleop+redauton)+" points!");
                                Thread.sleep(500);
                                if (difference<=20)
                                {
                                    System.out.println("With the blue alliance behind by only "+difference+"-points, it will come down to the endgame!");
                                    Thread.sleep(500);
                                }
                                }
                                    else
                                    {
                                        System.out.println("The score is tied at "+(blueteleop+blueauton)+" with 30 seconds left on the clock...");
                                        Thread.sleep(500);
                                    }
                                }
                                System.out.println("And that's the buzzer!\n"
                                        + ("We have "+(bluebridge==10?"a single balance for blue and\n":bluebridge==20?"a double balance for blue and \n":bluebridge==40?"an incredible tripple balance for blue and \n":"no robots on the blue bridge and \n"
                                        )+(redbridge==10?"a single balance for red!":redbridge==20?"a double balance for red!":redbridge==40?"an incredible tripple balance for red!":"no robots on the red bridge!")));
                               Thread.sleep(500);
                               if (Math.abs(redScore-blueScore)<10)
                               {
                                   System.out.println("That was a close match, and we'll have the scores up in just a moment...");
                                   Thread.sleep(2000);
                               }
                                if (blueScore>redScore)
                               {
                               System.out.println("It looks like the blue alliance took the win "+blueScore+" to "+redScore+"!");
                               }
                               else
                               {
                                   if (blueScore<redScore)
                               {
                               System.out.println("It looks like the red alliance took the win "+blueScore+" to "+redScore+"!");
                               }
                               else
                               {
                               System.out.println("Amazing! The score is tied at "+blueScore+" to "+redScore+"!"); 
                               }
                               }
                } catch (InterruptedException ex) {
                    Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                }
          }
          else
          if ("about".equals(le))
          {
              System.out.println("GUI/Client and Scouting Scheme by Griffin Della Grotte"
                      + "\nNetworking/Server by John Westhoff"
                      + "\nSwing Consulting by Griffin Della Grotte"
                      + "\nCommand Integration (of Doom) by John Westhoff"
                      + "\nSQLite Databasing by John Westhoff");
          }
          else
          {
              java.awt.Toolkit.getDefaultToolkit().beep();
              System.err.println("The Command "+le+" Is Not Recognized."
                      + "\nType Help For Assistance.");
          }
      }
          catch (Exception etfonehome)
      {
          System.err.println("What do you think you are doing?");
      }
      }
      
    }
    /**SQL
     * Creates a new database, should only be invoked by SQLITEload()
     * @throws Exception, in the event the SQL driver cannot be found.
     */
    public static void SQL() throws Exception
    {Class.forName("org.sqlite.JDBC");
           // System.out.println("Loading driver");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
           // System.out.println("Creating query");
            Statement stat = conn.createStatement();
         stat.executeUpdate("create table scouting (Team, AutonPoints, TeleopPoints, Absent, Balance, Defence, BotsBalance, HasAuton, TipInAuton, Functioning, Balanced, Cooperated, TippedFromBridge, Notes, MatchTime);");
      
    }
    /**SQLITEload
     * Loads data from the database. If the database does not exist, SQL() is invoked.
     */
        public static void SQLITEload()
    {
        try {
            ints = new int[6000][12];
            notes = new String[6000];
            System.out.println("Driver found");
            //Make sure we have a SQL driver
            Class.forName("org.sqlite.JDBC");
            System.out.println("Loading driver");
            //Load it....
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Creating query");
            Statement stat = conn.createStatement();
            System.out.println("Sending load query");
            //Test sample it, to make sure it exists.
ResultSet rs = stat.executeQuery("select * from scouting;");
System.out.println("Loading...");
String lastDate = "never";
while (rs.next())
{
    System.out.println("Loading...");
    int team = rs.getInt(1);
            ints[team][0] += rs.getInt(2);
                 ints[team][1] += rs.getInt(3);
                 ints[team][2] += (rs.getInt(4));
                 
                 
                 ints[team][3] += rs.getInt(5);
                 
                 ints[team][4] += rs.getInt(6);

                 ints[team][5] += rs.getInt(7);
                 
                 ints[team][6] += (rs.getInt(8));
                 
                 
                 ints[team][7] += rs.getInt(9);
                 
                 ints[team][8] += rs.getInt(10);
                 
                 

                 ints[team][9] += rs.getInt(11); 
                 
              //   if (clientSentence.getCooperated() == true){
                 ints[team][10] += rs.getInt(12);
                // }
                 
                // if (clientSentence.getTippedFromBridge() == true){
                 ints[team][11] += rs.getInt(13);
                // }
                 
                 
                 notes[team] += "~"+rs.getString(14);
                 lastDate = rs.getString(15);
}
System.out.println("Done! Data was last sent on "+lastDate+".");
            /*ResultSet rs = stat.executeQuery("select * from people;");
            while (rs.next()) {
                System.out.println("name = " + rs.getString("name"));
                System.out.println("job = " + rs.getString("occupation"));
            }
            rs.close();*/
            conn.close();
        } catch (Exception ex) {
            try {
                SQL();
            } catch (Exception ex1) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }
        /** SQLITE ADD
         *  Adds scouting data to the SQLite database
         * @param team The team number of the team being scouted.
         * @param ap Auton Point count
         * @param tp Teleop Point count
         * @param wa 
         * @param ab 
         * @param fail
         * @param balance 
         * @param coop Coopertition
         * @param abs
         * @param tip Tip the bridge
         * @param bw
         * @param bc
         * @param d Defence abilility
         * @param notes Notes on the team
         * 
         */
    public static void SQLITEadd(int team, int ap, int tp, int wa, int ab, int fail, int balance, int coop, int abs, int tip, int bw, int bc, int d, String notes)
    {
        try {
            //Make sure we have a driver.
            Class.forName("org.sqlite.JDBC");
            //If that did not throw an exception, then we have one, and should load it
            Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
           // Statement stat = conn.createStatement();
            //?s are placeholders, set below.
            notes = notes.replaceAll("'", "\"");
               PreparedStatement prep = conn.prepareStatement("INSERT INTO 'scouting' VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            prep.setInt(1, team);
            prep.setInt(2, ap);
            prep.setInt(3, tp);
            prep.setInt(4, wa);
            prep.setInt(5, ab);
            prep.setInt(6, fail);
            prep.setInt(7, balance);
            //prep.setInt(8, coop);
            prep.setInt(9-1, abs);
            prep.setInt(10-1, tip);
            prep.setInt(11-1, bw);
            prep.setInt(12-1, bc);
            prep.setInt(14-1, d);
            prep.setString(15-1, notes);
            prep.setString(16-1, new Date().toString());
            //Run the query.
            prep.execute();
            System.out.println("Data added!");
            /*conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
*/
            /*ResultSet rs = stat.executeQuery("select * from people;");
            while (rs.next()) {
                System.out.println("name = " + rs.getString("name"));
                System.out.println("job = " + rs.getString("occupation"));
            }
            rs.close();*/
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void copyFile(File sourceFile, File destFile) throws IOException {
    if(!destFile.exists()) {
        destFile.createNewFile();
    }

    FileChannel source = null;
    FileChannel destination = null;

    try {
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        destination.transferFrom(source, 0, source.size());
    }
    finally {
        if(source != null) {
            source.close();
        }
        if(destination != null) {
            destination.close();
        }
    }
}
}
