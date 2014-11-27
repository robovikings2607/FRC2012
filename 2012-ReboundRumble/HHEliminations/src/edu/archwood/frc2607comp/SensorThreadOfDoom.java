/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.archwood.frc2607comp;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDSource;

/**
 *
 *Author: John
 */
public class SensorThreadOfDoom extends Counter implements  PIDSource{
    Counter ofDoom;
    Thread me;
    double whatiexpecttoberpm = 0;
    double sixvalues = 0;
    double intervaltime = 0;
    double samplingArray[] = {0,0,0,0,0};
    public SensorThreadOfDoom(int i)
    {
    super (new DigitalInput(i));
    //me = new Thread(this);
    setUpSourceEdge(false,true);
    setMaxPeriod(2);
   // me.start();
    }
    public void setMaxTime(double d)
    {

        intervaltime = d;
    }
    public double getRPM()
    {
        return (1/getPeriod())*60;
    }

    public double pidGet() {
        return (1/getPeriod())*60;
    }

}
