/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/


/**
 *
 *Author: Sean
 */
package edu.archwood.frc2607comp;



import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import java.util.Date;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class ReboundRumbleCompetitionBot extends IterativeRobot implements Runnable
{
       double previousTiltSpeed = 0, FULLPOWER = 0,  MaxTime;
    RobotDrive driveTrain;
    robovikingStick driveStick, gameControlStick, gameAutoStick;
    SpeedController  Rotator, LeftRearMot, RightRearMot, LeftFrontMot, RightFrontMot, Collector, Feeder;
    dualCANJaguar Shooter;
    CANJaguar TheBRIDGINATOR;
    DriverStationLCD lcd = DriverStationLCD.getInstance();
    Servo cameraTilt, theGATINATOR;
    DriverStation ds;
    Relay LEDs, charginLEDs;
    Encoder theEncoder = null;
    int shooterCharge = 0;
    Solenoid important;
    double rectHeightCasted = 0;
    int autonTime = 0;
    int autonStep = 0;
    int savetheimages = 0;
    boolean isShooting = false;
    AnalogChannel rangefinder;
    FakeGyro falseGyro;
    temperatureSensor baseTemp,turretTemp,falseTemp;
    Gyro baseGyro, turretGyro;
    VisionThread VT;
    boolean driveOK, shooterOK, collectorOK, bridgeOK;
    int burstFire = 0;
    Thread me;
    int autonDelay = 3;
    double currentDistance[] = {0,0};
    
    boolean toggleVT, track;
    PIDController pidLoopConfig;
    SensorThreadOfDoom shooterSpeed;
    int rpmAdjust = 0;
    private double getDesiredRPM (double distFeet) {
    return (0.42361111111119*MathUtils.pow(distFeet, 3))+(-17.00297619048*MathUtils.pow(distFeet,2))+(238.19246031751*distFeet)+716.8809523808;
    }
    public void disabledInit()
    {
        showMessage("CV: 3.22.12:1A!",2);
        important.set(true);
    }
    public void robotInit()
    {
        
        ds = DriverStation.getInstance();
        System.out.println("Drive Stick");
        driveStick = new robovikingStick(1);
        System.out.println("Control Stick");
        gameControlStick = new robovikingStick(2);
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
                theEncoder = new Encoder(5,8);
                theEncoder.setDistancePerPulse(31.4 / 360);
                theEncoder.reset();
                driveOK = true;
                driveTrain.setSafetyEnabled(false);
                System.out.println("Bridges are burning now");
                TheBRIDGINATOR = new CANJaguar(constants.bridgelower);
                bridgeOK = true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        cameraTilt = new Servo(constants.servosarenotgyros);
        MaxTime = 0;
        System.out.println("Auto Stick");
        gameAutoStick = new robovikingStick(3);
        System.out.println("Collector");
        for (int i = 0; (i <= 10)&&(!collectorOK); i++)
        {
            try
            {
                Collector = new Jaguar(constants.collectorsp);
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
                Feeder = new Victor(constants.feedersp);
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
       // falseTemp = new temperatureSensor(constants.temp3);
        System.out.println("Gyros");
        baseGyro = new Gyro(constants.gyro1);
        turretGyro = new Gyro(constants.gyro2);
        //falseGyro = new FakeGyro(constants.gyro3,falseTemp);
        //falseGyro.reset();
        baseGyro.reset();
        turretGyro.reset();
        System.out.println("RangeFinder");
        rangefinder = new AnalogChannel(constants.range);
        System.out.println("LEDs");
        LEDs = new Relay(constants.lightssp,Relay.Direction.kForward);
        charginLEDs = new Relay(constants.mrCullensFavoriteNumber,Relay.Direction.kForward);
        System.out.println("Vision");
        VT = new VisionThread();
        if (!gameAutoStick.getRawButton(1))
        {
           VT.init();
        }
        shooterSpeed = new SensorThreadOfDoom(constants.ballSensor);
        shooterSpeed.setMaxTime(1500);
        shooterSpeed.start();
        pidLoopConfig = new PIDController(.0045, .0005,.0014,shooterSpeed,Shooter);
        pidLoopConfig.disable();
        pidLoopConfig.setInputRange(0, constants.maxRPM);
        pidLoopConfig.setOutputRange(0, 1); 
        pidLoopConfig.setTolerance(1);              
        important = new Solenoid(1);
        theGATINATOR = new Servo(constants.theGate);
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
    int autonMode = 5;
    boolean yes = false, no = false;
    public void disabledPeriodic()
    {
        if (driveStick.getRawButton(1))
        {
            autonMode = 0;
        }
        if (driveStick.getRawButton(4))
        {
            autonMode = 1;
        }
        if (driveStick.getRawButton(3))
        {
            autonMode = 2;
        }
        if (driveStick.getRawButton(2))
        {
            autonMode = 3;
        }
        if (driveStick.getRawButton(5))
        {
            autonMode = 4;
        }
                if (driveStick.getRawButton(6))
        {
            autonMode = 5;
        }
        if (driveStick.getRawButton(9)&&!yes)
        {
            autonDelay--;
        }
        if (driveStick.getRawButton(10)&&!no)
        {
            autonDelay++;
        }
        yes = driveStick.getRawButton(9);
        no = driveStick.getRawButton(10);
        showMessage(autonMode==0?"LeftHoop":autonMode==1?"TopHoop":autonMode==2?"RightHoop":autonMode==4?"Sabotage":autonMode==5?"Fender":"BottomHoop",1);
        showMessage("Delay shooting:"+autonDelay+"seconds",3);
    }

    int evenTime = 0;
    public void shoot(double RPM)
    {
        pidLoopConfig.setSetpoint(RPM*(1+(ds.getAnalogIn(4)/10)));
        pidLoopConfig.enable();
    }
    public void autonomousInit()
    {
        theEncoder.reset();
        autonTime = 0;
        autonStep = 0;
        evenTime = 0;
        driveTrain.arcadeDrive(0,0);
        Collector.set(0);

        Feeder.set(0);
    }
    public void autonomousPeriodic()
    {
        autonTime++;
    if (!shooterOK)
    {
        return;
    }
           if (autonMode == 5 && autonTime < 60){
        driveTrain.arcadeDrive(0.6,0);
    }
    if (autonMode == 4)
    {
        switch (autonStep)
        {
            case 0:
                driveTrain.arcadeDrive(.2,0.0);
                if (theEncoder.getDistance()>=48)
                {
                    autonStep++;
                    autonTime = 0;
                    driveTrain.arcadeDrive(0,0);
                }
            break;
            case 1:
                TheBRIDGINATOR.set(-.5);
                if (autonTime>=30)
                {
                    autonStep++;
                    TheBRIDGINATOR.set(0);
                    autonTime = 0;
                }
            break;
            case 2:
                
                
            break;
        }
    }
    else
    {
    if (autonMode!=5)
    {
    switch (autonStep)
    {
        case 0: //LockOn step
            cameraTilt.set(.6); 
            if (false)
            {
            currentDistance[0] = 0;
            LEDs.set(Relay.Value.kOn);
            victory = gotoTarget(autonMode);
            System.out.println("FINDING TARGET!");
            if (victory!=null) //Aim at the target
            {
                autonStep++; //Continue when done
                autonTime = 0;
                LEDs.set(Relay.Value.kOff);
            }
            if (autonTime>75) //If it takes too long...
            {
                autonStep++; //Continue as if we are locked on
                Rotator.set(-0);
                autonTime=0;
                LEDs.set(Relay.Value.kOff);
            }
            }
            theGATINATOR.set(1);
            autonStep++;
        break;
        case 1: //Charge the motor...
            double y, x;
        //    currentDistance[0] = 0;
            //62.5% at 34 works!
            //showMessage(autonMode==0?"LeftHoop":autonMode==1?"TopHoop":autonMode==2?"RightHoop":"BottomHoop",1);
            if (autonMode==0||autonMode==2)
            shoot(1640+rpmAdjust);
            if (autonMode==1)
            shoot(1780+rpmAdjust);//1870
            if (autonMode==3)
            shoot(1200+rpmAdjust);
            if (autonMode==5)
            shoot(1390+rpmAdjust);
            if (autonTime>70*autonDelay) //100
            {
                autonStep++;
                autonTime = 0;
            }
        break;
        case 2: //FIRE!
           
   
        driveTrain.arcadeDrive(0,0);
    
            
            VT.saveImage("images\\"+new Date().getTime()+"TcWidthT"+currentDistance[0]+"TcHeightT"+currentDistance[1]+"TcRangeT"+rangefinder.getValue()+"TRPMT"+shooterSpeed.getRPM()+"TDistanceT"+VT.getDistance()+".jpg");
            Feeder.set(-1); //Stop the feeder
            if (autonMode!=5)
            {
                 if ((autonTime>80))//50
            {
                theGATINATOR.set(0); 
                shoot(1870+rpmAdjust);//1870
            }
            }
            if (autonTime>500)
            {
                autonStep++; //KEEP SHOOTING!
                autonTime = 0;
            }
        break;
        case 3: //STOP! PLEASE! NOOOOO!
            pidLoopConfig.disable(); //KILL THE P.I.D. LOOP OF DOOM
            Feeder.set(0); //Stop the feeder
            Collector.set(0); //Stop the feeder
            Shooter.set(0); //Stop the shooter
        break;
    }
    }
    else
        {
            switch (autonStep)
    {
        case 0: //LockOn step
         //   theGATINATOR.set(.7);
            autonStep++; //Continue as if we are locked on
  
                autonTime=0;
        break;
        case 1: //Charge the motor...

                pidLoopConfig.setSetpoint(constants.FenderShot+50);
                pidLoopConfig.enable();
if (autonTime>77)
            {
               autonTime = 0;
               autonStep++;
            }
            break;
        case 2: //FIRE!
            try {
                driveTrain.arcadeDrive(0,0);
                       if (autonTime>110)
            {
                theGATINATOR.set(0);
            }
                       //                if ((autonTime>50&&autonTime<105)||((autonTime>155&&autonTime<205))||(autonMode>255))//each 

                if ((autonTime>50&&autonTime<85)||((autonTime>115&&autonTime<145))||(autonTime>180))//each 
                {
                    Feeder.set(-1); //Give the balls to the shooter
                }
                else
                {
                    Feeder.set(0);
                }
                // if (autonMode>205&&autonMode<255)
                if (autonTime>145&&autonTime<220)
                {
                    Collector.set(-1);
                }
                else
                {
                    Collector.set(0);
                }
            } catch (Exception e) {}
            //I AM THE BEST AT SPACE
     
            if (autonTime>500)
            {
                autonStep++; //KEEP SHOOTING!
                autonTime = 0;
            }
        break;
        case 3: //STOP! PLEASE! NOOOOO!
            try {
                Feeder.set(0); //Stop the feeder
                Shooter.set(0); //Stop the shooter
                 pidLoopConfig.disable();
            } catch (Exception e) {}
        break;
    }
        }
       }
    }
double timerIhave;
boolean toggleLights = false;
boolean lightsOn = true;
public void run()
    {
        while (true)
    {
        
             if (gameControlStick.getButtonToggle(11))
             {
                 rpmAdjust += 10;
             }
        if (gameControlStick.getButtonToggle(10))
        {
            rpmAdjust -= 10;
        }
        if (isOperatorControl())
        {
            /*if (savetheimages>0)
        {
            savetheimages--;
            VT.saveImage("images\\"+new Date().getTime()+"T"+currentDistance[0]+"T"+currentDistance[1]+"T"+rangefinder.getValue()+"T"+shooterSpeed.getRPM()+"T"+VT.getDistance()+".jpg");
        }*/
        if ((driveStick.getRawButton(8))||(gameControlStick.getRawButton(7))||(Collector.get()!=0))
        {
            theGATINATOR.set(0);
        }
        else
        {

                theGATINATOR.set(1);
            
        }


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
           if (lockedOnTarget)
           {
               target = -1;
           }
           if (target!=-1)
           {
         lockedOnTarget = (gotoTarget(target)!=null);
            }
        }
        else
        {
            lockedOnTarget = false;
            LEDs.set(Relay.Value.kOff);
            VT.gotoBed();
        }
        }
            try {
                Thread.sleep(20);
            }
            catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            toggleLights = gameAutoStick.getRawButton(1);
    }
    }
boolean lockedOnTarget = false;
    public void teleopPeriodic() {
         timerIhave = System.currentTimeMillis();
   
            FULLPOWER = (1-gameControlStick.getZ())/2;
            showMessage("EstRPM:"+ getDesiredRPM(VT.getDistance()),4);
            showMessage("DES: "+((FULLPOWER*constants.maxRPM)+rpmAdjust),6);
            showMessage("RPM: "+shooterSpeed.getRPM(),5);
        if (driveStick.getRawButton(10))
        {
         //   falseGyro.reset();
        }
       /* showMessage("bG"+baseGyro.getAngle(),3);
        showMessage("tG"+turretGyro.getAngle(),4);
        showMessage("fG"+falseGyro.getRelativeAngle(),5);
        showMessage("rF"+rangefinder.getValue(),5);*/
       
        if (driveOK)
        {
        /*if (driveStick.getRawButton(9))
        {
            if (falseGyro.getRelativeAngle()<-1)
            {
                if (previousTiltSpeed==0)
                {
                previousTiltSpeed=-.55*Math.abs(falseGyro.getRelativeAngle());
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
            if (falseGyro.getRelativeAngle()>1)
            {
                if (previousTiltSpeed==0)
                {
                previousTiltSpeed=.55*Math.abs(falseGyro.getRelativeAngle());
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
        {*/
         //    driveTrain.arcadeDrive(-driveStick.getY()*0.8, 
           //  Math.abs(driveStick.getY())>.4?1.0*(-driveStick.getRawAxis(3)):0);
      if (driveStick.getRawButton(1))
            {
                    if (Math.abs(driveStick.getRawAxis(3))>.03)
                {
                driveTrain.arcadeDrive(-driveStick.getY()*0.45, 
                -Math.max(Math.min(driveStick.getRawAxis(3),Math.abs(driveStick.getY())),-Math.abs(driveStick.getY())));
                }
                else
                {
                    driveTrain.arcadeDrive(-driveStick.getY()*.55,0);
                }
            }
            else
            {
                /* driveTrain.arcadeDrive(-driveStick.getY()*0.8, 
                Math.abs(driveStick.getY())>.4?1.0*(-driveStick.getRawAxis(3)):0);*/
                if (Math.abs(driveStick.getRawAxis(3))>.03)
                {
                driveTrain.arcadeDrive(-driveStick.getY()*0.75, 
                -Math.max(Math.min(driveStick.getRawAxis(3),Math.abs(driveStick.getY())),-Math.abs(driveStick.getY())));
                }
                else
                {
                    driveTrain.arcadeDrive(-driveStick.getY()*.85,0);
                }
            }
        }
/*
 * Conor Code:
 * driveTrain.arcadeDrive(-driveStick.getY()*1.0, 
             Math.abs(driveStick.getY())>.4?1.0*(-driveStick.getRawAxis(3))*
             (-driveStick.getY()/Math.abs(driveStick.getY())):0);
 */
 //       }

        if (driveStick.getRawButton(6))
        {
            cameraTilt.set(1);
        }
        else
        {
            cameraTilt.set(.6); 
        }
        showMessage("Width: "+VT.getWidth(),2);
        //showMessage("Dist R: "+rangefinder.getValue(),4);
         showMessage("Dist: "+VT.getDistance(),3);

        showMessage("TurretAngle: "+(turretGyro.getAngle()-baseGyro.getAngle()),1);
        // showMessage("TiltAngle: "+(falseGyro.getRelativeAngle()),3);
        //driveTrain.arcadeDrive(-driveStick.getY(), -driveStick.getX());
       try {
                  double a  = .3;
                if (TheBRIDGINATOR.getOutputCurrent()>15)
                {
                    a = .7;
                }
               
               // showMessage("Bridge: "+TheBRIDGINATOR.getOutputCurrent()+","+a,3);
             
            
        //driveTrain.arcadeDrive(-driveStick.getY(), -driveStick.getX());
              
                
        if (driveStick.getRawButton(4))
        {
       TheBRIDGINATOR.setX(.5);
        }
        else
        {
            if (driveStick.getRawButton(2))
        {
          TheBRIDGINATOR.setX(-a);
        }
            else
            {
          TheBRIDGINATOR.setX(0);
            }
        }
        }
         catch (Exception e)
         {
             
         }
                     
        
if (shooterOK)
{
    if (gameAutoStick.getRawButton(10))
    {
        if (gameAutoStick.getRawButton(11))
        {
            rpmAdjust = 0;
        }
        else
        {
           rpmAdjust = (int)(constants.FenderShot-(FULLPOWER*constants.maxRPM));
        }
    }
    else
    {
        if (gameAutoStick.getRawButton(11))
        {
             rpmAdjust = (int)(constants.FoulShot-(FULLPOWER*constants.maxRPM));
        }
        if (gameAutoStick.getRawButton(6))
        {
             rpmAdjust = (int)(constants.TopShot-(FULLPOWER*constants.maxRPM));
        }
    }
        if (gameControlStick.getRawButton(4))
        {
            Rotator.set(-.5);//ES SUPOSED 2 BE 1
        }
        else
        {
            if (gameControlStick.getRawButton(5))
            {
                Rotator.set(.5);
            }
            else
            {
                if (!(gameAutoStick.getRawButton(1)))
                Rotator.set(0);
            }
        }
        if (gameControlStick.getRawButton(1))
        {
            charginLEDs.set(Relay.Value.kOn);
            shoot((FULLPOWER*constants.maxRPM)+rpmAdjust);
          //  shooterCharge++;
            if (gameControlStick.getRawButton(3))
            {
               savetheimages = constants.imageSafe;
               
              // if (burstFire++<ds.getAnalogIn(1)*2)
              // {
                Feeder.set(-1);
              /* }
               else
               {
                   Feeder.set(Relay.Value.kOff);
                   burstFire = 0;
                   shooterCharge -= 20;
               }*/
            }
            else
            {
                   if (gameControlStick.getRawButton(9))
                   {
                   Feeder.set(1);
                   }
                    else
                    {
                    Feeder.set(0);
                    }  
            }
        }
        else
        {
        pidLoopConfig.disable();
        charginLEDs.set(Relay.Value.kOff);
        if (!(gameAutoStick.getRawButton(8))||(gameAutoStick.getRawButton(9)))
            {
            shooterCharge=0;
              
               if (gameControlStick.getRawButton(9))
                   {
                   Feeder.set(1);
                   }
                    else
                    {
                    Feeder.set(0);
                    }
            }
        }
        }
        if (collectorOK)
        {
        if (driveStick.getRawButton(5)||gameControlStick.getRawButton(2))
        {
            Collector.set(-1);
        }
        else
        {
            if (driveStick.getRawButton(3))
            {
                Collector.set(1);
            }
            else
            {
                Collector.set(0);
            }
        }
        }
        if ((System.currentTimeMillis()-timerIhave)>MaxTime)
        {
            MaxTime = (System.currentTimeMillis()-timerIhave);
        }
       // System.out.println("Bench "+(System.currentTimeMillis()-timerIhave));
//        showMessage("Max "+MaxTime,6);
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
                    showMessage("Height:"+r.boundingRectHeight,6);
                    currentDistance[1] = r.boundingRectHeight;
                    currentDistance[0] = r.boundingRectWidth;
                    //showMessage("DisC:"+(327.478443*(MathUtils.pow(.984241,r.boundingRectHeight))),6);
double rotateSpeed, minRS = 0.05, maxRS = .9;
                    if (r.center_mass_x>152+constants.cameraMountedAngleCorrection)
                    {
                        if (r.center_mass_x<168+constants.cameraMountedAngleCorrection)
                        {
                            Rotator.set(-0);
                            return r;
                        }
                        else
                        {
                        rotateSpeed = minRS;
                        if (r.center_mass_x>170+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.06;
                        if (r.center_mass_x>180+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.12;
                        if (r.center_mass_x>190+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.16;
                        if (r.center_mass_x>200+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.18;
                        if (r.center_mass_x>210+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.19;
                        if (r.center_mass_x>220+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.20;
                        if (r.center_mass_x>230+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.21;
                        if (r.center_mass_x>240+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.23;
                        if (r.center_mass_x>250+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.24;
                        if (r.center_mass_x>260+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.30;
                        if (r.center_mass_x>270+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.46;
                        if (r.center_mass_x>280+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.52;
                        if (r.center_mass_x>290+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.68;
                        if (r.center_mass_x>300+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.74;
                        if (r.center_mass_x>310+constants.cameraMountedAngleCorrection) rotateSpeed = maxRS;
 
                        if (Math.abs(driveStick.getY())>.1&&isOperatorControl()) rotateSpeed = .5;
                       // showMessage("RS"+rotateSpeed,3);
                        Rotator.set(rotateSpeed);
                        }
                    }
                    else
                    {
                        if (r.center_mass_x<168+constants.cameraMountedAngleCorrection)
                        {
                        rotateSpeed = minRS;
                        if (r.center_mass_x<150+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.06;
                        if (r.center_mass_x<140+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.12;
                        if (r.center_mass_x<130+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.16;
                        if (r.center_mass_x<120+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.18;
                        if (r.center_mass_x<110+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.19;
                        if (r.center_mass_x<100+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.20;
                        if (r.center_mass_x<90+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.21;
                        if (r.center_mass_x<80+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.23;
                        if (r.center_mass_x<70+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.24;
                        if (r.center_mass_x<60+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.30;
                        if (r.center_mass_x<50+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.46;
                        if (r.center_mass_x<40+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.52;
                        if (r.center_mass_x<30+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.68;
                        if (r.center_mass_x<20+constants.cameraMountedAngleCorrection) rotateSpeed = minRS+.74;
                        if (r.center_mass_x<10+constants.cameraMountedAngleCorrection) rotateSpeed = maxRS;
                          if (Math.abs(driveStick.getY())>.1&&isOperatorControl()) rotateSpeed = .5;
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
