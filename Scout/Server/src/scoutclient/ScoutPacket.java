/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package scoutclient;

import java.io.Serializable;

/**
 *
 *Author: John
 */
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
public class ScoutPacket implements Serializable{
private int teamNum, autonPoints, teleopPoints, balanceQual, defenseQual, botsBalanced;
private boolean crossBar, cooperBal, function, fouls,  tipBridge, tipInAuton, absent;
private String notes;
public ScoutPacket()
    {

    }
public ScoutPacket(int int1, int int3, int int4, boolean b1,
boolean b2, boolean b3, boolean b4,  boolean b6, boolean b7, boolean b8, String st1, int int5, int int6, int int7)
    {
        teamNum = int1;
        autonPoints = int3;
        teleopPoints = int4;
        balanceQual = int5;
        defenseQual = int6;
        botsBalanced = int7;
        crossBar = b1;
        tipInAuton = b2;
        function = b3;
        fouls = b4;
        tipBridge = b6;
        cooperBal=b7;
        absent = b8;
        notes = st1;
    }

public boolean getAbsent()
    {
        return absent;
    }

public boolean getCooperated()
    {
        return cooperBal;
    }

public boolean getHasAuton()
    {
        return crossBar;
    }
public boolean getTipInAuton()
    {
        return tipInAuton;
    }
public boolean getFunctioning()
    {
        return function;
    }
public boolean getBalanced()
    {
        return fouls;
    }

public boolean getTippedFromBridge()
    {
        return tipBridge;
    }
public int getTeam()
    {
        return teamNum;
    }
public int getTeleopPoints()
    {
        return teleopPoints;
    }
public int getAutonPoints()
    {
        return autonPoints;
    }

public int getDefense()
    {
        return defenseQual;
    }
public int getBalance()
    {
        return balanceQual;
    }
public int getBotsBalanced()
    {
        return botsBalanced;
    }
public String getNotes()
    {
        return notes;
    }
}