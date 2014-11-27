/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


package edu.archwood.frc2607;


import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SmartDashboard;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class ReboundRumblePrototype extends IterativeRobot implements Runnable
{
       double previousTiltSpeed = 0, FULLPOWER = 0,  MaxTime;
    RobotDrive driveTrain;
    Joystick driveStick, gameControlStick, gameAutoStick;
    SpeedController  Rotator, LeftRearMot, RightRearMot, LeftFrontMot, RightFrontMot, BridgeBurning;
    dualCANJaguar Shooter;
    DriverStationLCD lcd = DriverStationLCD.getInstance();
    DriverStation ds;
    Relay LEDs, Collector, Feeder;
    int shooterCharge = 0;
    double rectHeightCasted = 0;
    int autonTime = 0;
    int autonStep = 0;
    boolean isShooting = false;
    AnalogChannel rangefinder;
    FakeGyro falseGyro;
    temperatureSensor baseTemp,turretTemp,falseTemp;
    Gyro baseGyro, turretGyro;
    VisionThread VT;
    boolean driveOK, shooterOK, collectorOK, bridgeOK;
    int burstFire = 0;
    Thread me;
    public void disabledInit()
    {
        showMessage("Robot Ready!",2);
    }
    public void robotInit()
    {
        ds = DriverStation.getInstance();
       SmartDashboard.init();
        System.out.println("Drive Stick");
        driveStick = new Joystick(1);
        System.out.println("Control Stick");
        gameControlStick = new Joystick(2);
        for (int i = 0; (i <= 10)&&(!bridgeOK); i++)
        {
            try
            {
                System.out.println("LRM");
                LeftRearMot = new CANJaguar(constants.leftcanmotor);
                System.out.println("RRM");
                RightRearMot = new CANJaguar(constants.rightcanmotor);
                System.out.println("LFM");
                LeftFrontMot = new CANJaguar(constants.leftcanmotor2);
                System.out.println("RFM");
                RightFrontMot = new CANJaguar(constants.rightcanmotor2);
                System.out.println("Drive Train");
                driveTrain = new RobotDrive(LeftRearMot,LeftFrontMot,RightRearMot,RightFrontMot);
                driveOK = true;
                System.out.println("Bridges are burning now");
                BridgeBurning = new CANJaguar(constants.bridgelower);
                bridgeOK = true;
                SmartDashboard.log("OK TO DRIVE!","Driving: ");
            } catch (Exception ex) {
                SmartDashboard.log("DO NOT DRIVE!","Driving: ");
                System.out.println(ex.getMessage());
            }
        }
        MaxTime = 0;
        System.out.println("Auto Stick");
        gameAutoStick = new Joystick(3);
        System.out.println("Collector");
        for (int i = 0; (i <= 10)&&(!collectorOK); i++)
        {
            try
            {
                Collector = new Relay(constants.collectorsp,Relay.Direction.kBoth);
                collectorOK = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        for (int i = 0; (i <= 10)&&(!shooterOK); i++)
        {
            try
            {
                System.out.println("Feed");
                Feeder = new Relay(constants.feedersp,Relay.Direction.kBoth);
                System.out.println("Shooter Jags");
                CANJaguar Shooter1 = new CANJaguar(constants.ooter1);
                CANJaguar Shooter2 = new CANJaguar(constants.ooter2);
                System.out.println("Shooter Object");
                Shooter = new dualCANJaguar(Shooter1, Shooter2);
                System.out.println("Rotate");
                Rotator = new CANJaguar(constants.rotater);
                shooterOK = true;
             } catch (Exception ex) {
                 System.out.println(ex.getMessage());
             }
        }
        System.out.println("Temperature Sensors");
        baseTemp = new temperatureSensor(constants.temp1);
        turretTemp = new temperatureSensor(constants.temp2);
        falseTemp = new temperatureSensor(constants.temp3);
        System.out.println("Gyros");
        baseGyro = new Gyro(constants.gyro1);
        turretGyro = new Gyro(constants.gyro2);
        falseGyro = new FakeGyro(constants.gyro3,falseTemp);
        falseGyro.reset();
        baseGyro.reset();
        turretGyro.reset();
        System.out.println("RangeFinder");
        rangefinder = new AnalogChannel(constants.range);
        System.out.println("LEDs");
        LEDs = new Relay(constants.lightssp,Relay.Direction.kForward);
        System.out.println("Vision");
        VT = new VisionThread();
        if (!gameAutoStick.getRawButton(1))
        {
           VT.init();
        }
        me = new Thread(this);
        me.start();
    }
    public void showMessage(String msg, int line)
    {
        Line l = DriverStationLCD.Line.kMain6;
        switch (line)
        {
            case 2:
                l=DriverStationLCD.Line.kUser2;
            break;
            case 3:
                l=DriverStationLCD.Line.kUser3;
            break;
            case 4:
                l=DriverStationLCD.Line.kUser4;
            break;
            case 5:
                l=DriverStationLCD.Line.kUser5;
            break;
            case 6:
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
        if (driveStick.getRawButton(1))
        {
            autonMode = 0;
        }
        if (driveStick.getRawButton(2))
        {
            autonMode = 1;
        }
        if (driveStick.getRawButton(3))
        {
            autonMode = 2;
        }
        if (driveStick.getRawButton(4))
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
    if (!shooterOK) return;
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
            Shooter.set(-y/1000); //Set the speed
            if (autonTime>250) //Continue when it's fast enough
            {
                autonStep++;
                autonTime = 0;
            }
        break;
        case 2: //FIRE!
            Feeder.set(Relay.Value.kReverse); //Give the balls to the shooter
            if (autonTime>500)
            {
                autonStep++; //KEEP SHOOTING!
                autonTime = 0;
            }
        break;
        case 3: //STOP! PLEASE! NOOOOO!
            Feeder.set(Relay.Value.kOff); //Stop the feeder
            Shooter.set(0); //Stop the shooter
        break;
    }
    }
double timerIhave;
public void run()
    {
        while (true)
    {
            if (gameAutoStick.getRawButton(1))
        {
           LEDs.set(Relay.Value.kOn);
           showMessage(autonMode==0?"LeftHoop":autonMode==1?"TopHoop":autonMode==2?"RightHoop":"BottomHoop",1);
           int target = -1;
           if (gameAutoStick.getRawButton(2))
           target = 3;
           if (gameAutoStick.getRawButton(4))
           target = 0;
           if (gameAutoStick.getRawButton(5))
           target = 2;
           if (gameAutoStick.getRawButton(3))
           target = 1;
           if (target!=-1)
           {
          gotoTarget(target);
            }
        }
        else
        {
            LEDs.set(Relay.Value.kOff);
            VT.gotoBed();
        }
            try {
                Thread.sleep(20);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
    }
    }
    public void teleopPeriodic() {
        if (driveStick.getRawButton(10))
        {
            falseGyro.reset();
        }
       /* showMessage("bG"+baseGyro.getAngle(),3);
        showMessage("tG"+turretGyro.getAngle(),4);
        showMessage("fG"+falseGyro.getRelativeAngle(),5);
        showMessage("rF"+rangefinder.getValue(),5);*/
        timerIhave = System.currentTimeMillis();
        if (driveOK)
        {
        if (driveStick.getRawButton(9))
        {
            if (falseGyro.getRelativeAngle()<-5)
            {
                if (previousTiltSpeed==0)
                {
                previousTiltSpeed=-.1;
                }
                else
                {
                    if (previousTiltSpeed>0)
                    previousTiltSpeed=0;
                }
            // driveTrain.mecanumDrive2(previousTiltSpeed, 0,0);
                 driveTrain.arcadeDrive(previousTiltSpeed, 0);
            }
            else
            {
            if (falseGyro.getRelativeAngle()>5)
            {
                if (previousTiltSpeed==0)
                {
                previousTiltSpeed=.1;
                }
                else
                {
                    if (previousTiltSpeed<0)
                    previousTiltSpeed=0;
                }
           //  driveTrain.mecanumDrive2(previousTiltSpeed, 0,0);
            driveTrain.arcadeDrive(previousTiltSpeed, 0);
            }
            else
            {
                previousTiltSpeed=0;
             driveTrain.arcadeDrive(0, 0);
            }
            }
        }
        else
        {
             driveTrain.arcadeDrive(-driveStick.getY()*.8, Math.abs(driveStick.getY())>.5?.8*(-driveStick.getRawAxis(3))*(-driveStick.getY()/Math.abs(driveStick.getY())):0);

        }


        showMessage("Power: "+((int)100*(1-((-gameControlStick.getZ()+1)/2)))+"%",2);
        showMessage("Dist: "+rangefinder.getValue(),4);
        showMessage("TurretAngle: "+(turretGyro.getAngle()-baseGyro.getAngle()),1);
        showMessage("TiltAngle: "+(falseGyro.getRelativeAngle()),3);
        //driveTrain.arcadeDrive(-driveStick.getY(), -driveStick.getX());
        if (driveStick.getRawButton(4))
        {
       BridgeBurning.set(.4);
        }
        else
        {
            if (driveStick.getRawButton(2))
        {
          BridgeBurning.set(-.4);
        }
            else
            {
          BridgeBurning.set(0);
            }
        }
        }

        
if (shooterOK)
{
    FULLPOWER = Math.min(FULLPOWER+.005,0);
        if (gameControlStick.getRawButton(4))
        {
            Rotator.set(-1);
        }
        else
        {
            if (gameControlStick.getRawButton(5))
            {
                Rotator.set(1);
            }
            else
            {
                if (!(gameAutoStick.getRawButton(1)))
                Rotator.set(0);
            }
        }
        if (gameControlStick.getRawButton(1))
        {
            if (gameControlStick.getRawButton(3))
            {
                FULLPOWER = -.3;
            }
            else
            {
                FULLPOWER = -(gameControlStick.getZ()+1)/2;
                 
            }
            shooterCharge++;
            if (shooterCharge>=150||gameControlStick.getRawButton(3))
            {
               shooterCharge=151;
               if (burstFire++<8)
               {
                Feeder.set(Relay.Value.kReverse);
               }
               else
               {
                   Feeder.set(Relay.Value.kOff);
                   burstFire = 0;
                   shooterCharge -= 20;
               }
            }

        }
        else
        {
        if (!(gameAutoStick.getRawButton(8))||(gameAutoStick.getRawButton(9)))
            {
            shooterCharge=0;
              
               if (gameControlStick.getRawButton(9))
                   {
                   Feeder.set(Relay.Value.kForward);
                   }
                    else
                    {
                    Feeder.set(Relay.Value.kOff);
                    }
            }
        }
        Shooter.set(FULLPOWER);
        }
        if (collectorOK)
        {
        if (driveStick.getRawButton(12))
        {
            Collector.set(Relay.Value.kReverse);
        }
        else
        {
            if (driveStick.getRawButton(3))
            {
                Collector.set(Relay.Value.kForward);
            }
            else
            {
                Collector.set(Relay.Value.kOff);
            }
        }
        }
        if ((System.currentTimeMillis()-timerIhave)>MaxTime)
        {
            MaxTime = (System.currentTimeMillis()-timerIhave);
        }
        showMessage("Bench "+(System.currentTimeMillis()-timerIhave),5);
        showMessage("Max "+MaxTime,6);
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
                    //showMessage("Height:"+r.boundingRectHeight,5);
                    //showMessage("Dis:"+(327.478443*(MathUtils.pow(.984241,r.boundingRectHeight))),4);
double rotateSpeed = 0, minRS = 0.2, maxRS = .9;
                    if (r.center_mass_x>155)
                    {
                        if (r.center_mass_x<165)
                        {
                            Rotator.set(-0);
                            return r;
                        }
                        else
                        {
                        rotateSpeed = minRS;
                        if (r.center_mass_x>200) rotateSpeed = minRS+.2;
                        if (r.center_mass_x>230) rotateSpeed = minRS+.3;
                        if (r.center_mass_x>250) rotateSpeed = minRS+.4;
                        if (r.center_mass_x>270) rotateSpeed = minRS+.5;
                        if (r.center_mass_x>290) rotateSpeed = minRS+.6;
                        if (r.center_mass_x>305) rotateSpeed = maxRS;
                       // showMessage("RS"+rotateSpeed,3);
                        Rotator.set(rotateSpeed);
                        }
                    }
                    else
                    {
                        if (r.center_mass_x<165)
                        {
                        rotateSpeed = minRS;
                        if (r.center_mass_x<120) rotateSpeed = minRS+.2;
                        if (r.center_mass_x<90) rotateSpeed = minRS+.3;
                        if (r.center_mass_x<70) rotateSpeed = minRS+.4;
                        if (r.center_mass_x<50) rotateSpeed = minRS+.5;
                        if (r.center_mass_x<30) rotateSpeed = minRS+.6;
                        if (r.center_mass_x<15) rotateSpeed = maxRS;
                        //showMessage("RS"+rotateSpeed,3);
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
