/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.archwood.frc2607;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 *Author: John
 */
public class dualCANJaguar {

   private CANJaguar j1, j2;
   dualCANJaguar(CANJaguar s1,CANJaguar s2)
    {
       j1 = s1;
       j2 = s2;
    }
      public double get()
    {
        try {
            return (j1.getX() + j2.getX()) / 2;
        }
        catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
        return 0.0d;
   }
   public void set(double d)
    {
        try {
            j1.setX(d);
            j2.setX(d);
        }
        catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
   }
     public CANJaguar getJ2()
    {
       return (CANJaguar) j2;
   }
  public CANJaguar getJ1()
    {
       return (CANJaguar) j1;
   }

}
