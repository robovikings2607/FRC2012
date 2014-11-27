/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.archwood.frc2607comp;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 *
 *Author: Sean
 */
public class dualCANJaguar implements PIDOutput{

   private CANJaguar j1, j2;
   private double maxSpeed = 3550;
   dualCANJaguar(CANJaguar s1,CANJaguar s2)
    {
            j1 = s1;
            j2 = s2;
        /*try {
            j1.changeControlMode(CANJaguar.ControlMode.kSpeed);
            j2.changeControlMode(CANJaguar.ControlMode.kSpeed);
            j1.enableControl();
            j2.enableControl();
            j1.setSpeedReference(CANJaguar.SpeedReference.kInvEncoder);
            j2.setSpeedReference(CANJaguar.SpeedReference.kInvEncoder);
            j1.configEncoderCodesPerRev(3650);
            j2.configEncoderCodesPerRev(3650);
        }
        catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }*/

    }
      public void setMaxSpeed(double d)
    {
        maxSpeed = d;
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
            j1.setX(d,constants.shootsync);
            j2.setX(d,constants.shootsync);
            CANJaguar.updateSyncGroup(constants.shootsync);
        }
        catch (CANTimeoutException ex) {
            ex.printStackTrace();
        }
   }
   public void setRPM(double d)
    {
        try {
            j1.setX(constants.maxRPM/d,constants.shootsync);
            j2.setX(constants.maxRPM,constants.shootsync);
            CANJaguar.updateSyncGroup(constants.shootsync);
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
    private double desiredSpeed;
    public void theta(double d)
    {
        desiredSpeed = d;
    }

    public void pidWrite(double output) {
//        output/=constants.maxRPM;
//        set(-Math.abs(desiredSpeed+output));
           set(-output);
    }

}
