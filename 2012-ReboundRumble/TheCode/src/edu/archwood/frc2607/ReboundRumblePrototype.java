/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.archwood.frc2607;


import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class ReboundRumblePrototype extends IterativeRobot
{
    RobotDrive headbobbingmovement;
    Joystick joy, joy2, joy3;
    SpeedController Roller, Feeder, Shooter, Rotator, V1, V2;
    DriverStationLCD lcd = DriverStationLCD.getInstance();
    Relay LEDs;
    int shooterCharge = 0;
    int imagerefreshRate = 0;
    double rectHeightCasted = 0;
    int autonTime = 0;
    int autonStep = 0;
    boolean isShooting = false;
    VisionThread VT;
    public void robotInit()
    {
        try
        {
            
            System.out.println("J1");
            joy = new Joystick(1);
            System.out.println("J2");
            joy2 = new Joystick(2);
            System.out.println("Drive");
            V1 = new Victor(1);
            V2 = new Victor(2);
            headbobbingmovement = new RobotDrive(V1,V2);
            System.out.println("J3");
            joy3 = new Joystick(3);
            System.out.println("Roll");
            Roller = new Victor(14);
            System.out.println("Feed");
            Feeder = new Victor(8);
            System.out.println("Shot");
            Shooter = new Victor(6);
            System.out.println("Rotate");
            Rotator = new Victor(5);
            System.out.println("LEDs");
            LEDs = new Relay(1,Relay.Direction.kForward);
            VT = new VisionThread();
            VT.init();
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    public void showMessage(String msg, int line)
    {
        Line l = DriverStationLCD.Line.kMain6;
        switch (line)
        {
            case 1:
                l=DriverStationLCD.Line.kUser2;
            break;
            case 2:
                l=DriverStationLCD.Line.kUser3;
            break;
            case 3:
                l=DriverStationLCD.Line.kUser4;
            break;
            case 4:
                l=DriverStationLCD.Line.kUser5;
            break;
            case 5:
                l=DriverStationLCD.Line.kUser6;
            break;
        }
        lcd.println(l,1,msg+"                           ");
        lcd.updateLCD();
    }
    ParticleAnalysisReport victory;
    int autonMode = 0;
    public void disabledPeriodic()
    {
        if (joy.getRawButton(1))
        {
            autonMode = 0;
        }
        if (joy.getRawButton(2))
        {
            autonMode = 1;
        }
        if (joy.getRawButton(3))
        {
            autonMode = 2;
        }
        if (joy.getRawButton(4))
        {
            autonMode = 3;
        }
        showMessage(autonMode==0?"LeftHoop":autonMode==1?"TopHoop":autonMode==2?"RightHoop":"BottomHoop",1);
    }
    public void autonomousInit()
    {
        autonTime = 0;
        autonStep = 0;
    }
    public void autonomousPeriodic()
    {
        autonTime++;
    switch (autonStep)
    {
        case 0: //LockOn step
            victory = gotoTarget(autonMode);
            if (victory!=null) //Aim at the target
            {
                autonStep++; //Continue when done
                autonTime = 0;
            }
            if (autonTime>150) //If it takes too long...
            {
                autonStep++; //Continue as if we are locked on
                Rotator.set(-0);
                autonTime=0;
            }
        break;
        case 1: //Charge the motor...
            double y, x;
            if (victory!=null) //If we have a target
            {
                x = (327.478443*(MathUtils.pow(.984241,victory.boundingRectHeight)));
                y=(332.4133467)*(MathUtils.pow(1.003756132,x)); //Find out how hard to shoot it
            }
            else
            {
                y=500; //Otherwise, go half speed
            }
            Shooter.set(y/1000); //Set the speed
            if (autonTime>250) //Continue when it's fast enough
            {
                autonStep++;
                autonTime = 0;
            }
        break;
        case 2: //FIRE!
            Feeder.set(-.5); //Give the balls to the shooter
            if (autonTime>500)
            {
                autonStep++; //KEEP SHOOTING!
                autonTime = 0;
            }
        break;
        case 3: //STOP! PLEASE! NOOOOO!
            Feeder.set(0); //Stop the feeder
            Shooter.set(0); //Stop the shooter
        break;
    }
    }
    public void teleopPeriodic() {
        showMessage("Power:"+((-joy2.getZ()+1)/2),1);
        headbobbingmovement.arcadeDrive(-joy.getY(), -joy.getX());
        if (joy2.getRawButton(4))
        {
            Rotator.set(-.3);
        }
        else
        {
            if (joy2.getRawButton(5))
            {
                Rotator.set(-.3);
            }
            else
            {
                Rotator.set(-0);
            }
        }
        if (joy2.getRawButton(1))
        {
            if (joy2.getRawButton(3))
            {
                Shooter.set(.8d);
            }
            else
            {
                 Shooter.set((-joy2.getZ()+1)/2);
            }
            shooterCharge++;
            if (shooterCharge>=250)
            {
                shooterCharge=251;
                Feeder.set(-.5);
            }
        }
        else
        {
        if (!(joy3.getRawButton(8))||(joy3.getRawButton(9)))
            {
            shooterCharge=0;
            Shooter.set(0);
            Feeder.set(0);
            }
        }
        if (joy2.getRawButton(2))
        {
            Roller.set(-1);
        }
        else
        {
            if (joy2.getRawButton(8))
            {
                Roller.set(1);
            }
            else
            {
                Roller.set(0);
            }
        }
        if (joy3.getRawButton(1))
        {
           LEDs.set(Relay.Value.kOn);
        }
        else
        {
            LEDs.set(Relay.Value.kOff);
        }
        if (joy3.getRawButton(1))
        {
            VT.wakeUp();
            ParticleAnalysisReport[] reports = VT.getReport();
            if (Shooter.get()>0)
            {
                reports = new ParticleAnalysisReport[0];
            }
                if (reports.length>0)
                {
                    int biggestSize=320;
                    ParticleAnalysisReport r = reports[0];
                    if (joy3.getRawButton(4))
                    {
                    for (int i = 0; i < reports.length; i++)
                    {
                        if (reports[i].center_mass_x<biggestSize)
                        {
                            biggestSize=reports[i].center_mass_x;
                            r = reports[i];
                        }
                    }
                    }
                    if (joy3.getRawButton(3))
                    for (int i = 0; i < reports.length; i++)
                    {
                        if (reports[i].center_mass_y<biggestSize)
                        {
                            biggestSize=reports[i].center_mass_y;
                            r = reports[i];
                        }
                    }
                    if (joy3.getRawButton(5))
                    {
                        biggestSize = 0;
                    for (int i = 0; i < reports.length; i++)
                    {
                        if (reports[i].center_mass_x>biggestSize)
                        {
                            biggestSize=reports[i].center_mass_x;
                            r = reports[i];
                        }
                    }
                    }
                    if (joy3.getRawButton(2))
                    for (int i = 0; i < reports.length; i++)
                    {
                        if (reports[i].center_mass_y>biggestSize)
                        {
                            biggestSize=reports[i].center_mass_y;
                            r = reports[i];
                        }
                    }
                    System.out.println("I found something!");
                    System.out.println("Center of Mass:"+(r.center_mass_x));
                    showMessage("Height:"+r.boundingRectHeight,5);
                    showMessage("Dis:"+(327.478443*(MathUtils.pow(.984241,r.boundingRectHeight))),4);
                    if (joy3.getRawButton(8))
                    {
                    double y, x;
x = (327.478443*(MathUtils.pow(.984241,r.boundingRectHeight)));
y=(332.4133467)*(MathUtils.pow(1.003756132,x));

                 Shooter.set(y/1000);

            shooterCharge++;
            if (joy3.getRawButton(10))
            {
                shooterCharge=251;
                Feeder.set(-.5);
            }




                    }
        else
        {
                        if (joy3.getRawButton(9))
                    {
                    double y, x;
x = (327.478443*(MathUtils.pow(.984241,r.boundingRectHeight)));
y=2.521605954*x+202.6112912;

                 Shooter.set(y/1000);

            shooterCharge++;
            if (joy3.getRawButton(10))
            {
                shooterCharge=251;
                Feeder.set(-.5);
            }

        }

                    }
           if (!(joy3.getRawButton(8))||(joy3.getRawButton(9)))
            {
            shooterCharge=0;
            Shooter.set(0);
            Feeder.set(0);
            }
double rotateSpeed = 0, minRS = 0.2, maxRS = 1.0;
                    if (r.center_mass_x>150)
                    {
                        if (r.center_mass_x<170)
                        {
                            Rotator.set(-0);
                            System.out.println("LOCKED ON!");
                        }
                        else
                        {
                        rotateSpeed = minRS;
                        if (r.center_mass_x>200) rotateSpeed = minRS+.1;
                        if (r.center_mass_x>230) rotateSpeed = minRS+.2;
                        if (r.center_mass_x>250) rotateSpeed = minRS+.3;
                        if (r.center_mass_x>270) rotateSpeed = minRS+.5;
                        if (r.center_mass_x>390) rotateSpeed = minRS+.65;
                        if (r.center_mass_x>300) rotateSpeed = maxRS;
                        showMessage("RS"+rotateSpeed,3);
                        Rotator.set(rotateSpeed);
                        }
                    }
                    else
                    {
                        if (r.center_mass_x<170)
                        {
                        rotateSpeed = minRS;
                        if (r.center_mass_x<120) rotateSpeed = minRS+.1;
                        if (r.center_mass_x<95) rotateSpeed = minRS+.2;
                        if (r.center_mass_x<70) rotateSpeed = minRS+.3;
                        if (r.center_mass_x<45) rotateSpeed = minRS+.5;
                        if (r.center_mass_x<20) rotateSpeed = minRS+.65;
                        if (r.center_mass_x<10) rotateSpeed = maxRS;
                        showMessage("RS"+rotateSpeed,3);
                        Rotator.set(-rotateSpeed);
                        }
                        else
                        {
                            Rotator.set(-0);
                            System.out.println("LOCKED ON!");
                        }
                    }
                }
                else
                {
                    System.out.println("I'M BLIND!");
                }
            showMessage("Bench:"+VT.getTimer(),2);
            }

        else
        {
            VT.gotoBed();
        }
    }
    /**
     * 
     * @param int direction: The net to shoot at 0-3
     * @return An object when locked on, null otherwise
     */
    private ParticleAnalysisReport gotoTarget(int direction)
    {
        VT.wakeUp();
            ParticleAnalysisReport[] reports = VT.getReport();
            if (Shooter.get()>0)
            {
                reports = new ParticleAnalysisReport[0];
            }
                if (reports.length>0)
                {
                    int biggestSize=320;
                    ParticleAnalysisReport r = reports[0];
                    if (direction == 0)
                    {
                    for (int i = 0; i < reports.length; i++)
                    {
                        if (reports[i].center_mass_x<biggestSize)
                        {
                            biggestSize=reports[i].center_mass_x;
                            r = reports[i];
                        }
                    }
                    }
                    if (direction==1)
                    {
                    for (int i = 0; i < reports.length; i++)
                    {
                        if (reports[i].center_mass_y<biggestSize)
                        {
                            biggestSize=reports[i].center_mass_y;
                            r = reports[i];
                        }
                    }
                    }
                    if (direction==2)
                    {
                        biggestSize = 0;
                    for (int i = 0; i < reports.length; i++)
                    {
                        if (reports[i].center_mass_x>biggestSize)
                        {
                            biggestSize=reports[i].center_mass_x;
                            r = reports[i];
                        }
                    }
                    }
                    if (direction==3)
                    {
                        biggestSize=0;
                    for (int i = 0; i < reports.length; i++)
                    {
                        if (reports[i].center_mass_y>biggestSize)
                        {
                            biggestSize=reports[i].center_mass_y;
                            r = reports[i];
                        }
                    }
                    }
                    System.out.println("I found something!");
                    System.out.println("Center of Mass:"+(r.center_mass_x));
                    showMessage("Height:"+r.boundingRectHeight,5);
                    showMessage("Dis:"+(327.478443*(MathUtils.pow(.984241,r.boundingRectHeight))),4);
double rotateSpeed = 0, minRS = 0.2, maxRS = 1.0;
                    if (r.center_mass_x>150)
                    {
                        if (r.center_mass_x<170)
                        {
                            Rotator.set(-0);
                            return r;
                        }
                        else
                        {
                        rotateSpeed = minRS;
                        if (r.center_mass_x>200) rotateSpeed = minRS+.1;
                        if (r.center_mass_x>230) rotateSpeed = minRS+.2;
                        if (r.center_mass_x>250) rotateSpeed = minRS+.3;
                        if (r.center_mass_x>270) rotateSpeed = minRS+.5;
                        if (r.center_mass_x>390) rotateSpeed = minRS+.65;
                        if (r.center_mass_x>300) rotateSpeed = maxRS;
                        showMessage("RS"+rotateSpeed,3);
                        Rotator.set(rotateSpeed);
                        }
                    }
                    else
                    {
                        if (r.center_mass_x<170)
                        {
                        rotateSpeed = minRS;
                        if (r.center_mass_x<120) rotateSpeed = minRS+.1;
                        if (r.center_mass_x<95) rotateSpeed = minRS+.2;
                        if (r.center_mass_x<70) rotateSpeed = minRS+.3;
                        if (r.center_mass_x<45) rotateSpeed = minRS+.5;
                        if (r.center_mass_x<20) rotateSpeed = minRS+.65;
                        if (r.center_mass_x<10) rotateSpeed = maxRS;
                        showMessage("RS"+rotateSpeed,3);
                        Rotator.set(-rotateSpeed);
                        }
                        else
                        {
                            Rotator.set(-0);
                            return r;
                        }
                    }
                }
                else
                {
                    System.out.println("I'M BLIND!");
                }
            return null;
            }

    }
