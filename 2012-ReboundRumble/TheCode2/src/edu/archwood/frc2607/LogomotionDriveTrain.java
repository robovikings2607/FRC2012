/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.archwood.frc2607;

//import edu.archwood.frc2607.logomotion.LogomotionConstants;
//import edu.archwood.frc2607.utility.ConsoleLogger;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Ron
 */
public class LogomotionDriveTrain{

    private CANJaguar   frontLeftMotor = null, frontRightMotor = null,
                        rearLeftMotor = null, rearRightMotor = null;
    private double      frontLeftMotorSpeed, frontRightMotorSpeed,
                        rearLeftMotorSpeed, rearRightMotorSpeed;
      public static final int frontLeftCANID = 12, frontRightCANID = 10,
                            rearLeftCANID = 11, rearRightCANID = 2;
    private short       driveNow = 0;

    public boolean hasReachedFork = false;
    private void initMotors() {
        while (frontLeftMotor == null) {
            try {
                frontLeftMotor = new CANJaguar(frontLeftCANID);
               // cl.log("frontLeft Jag created successfully");
            } catch (Exception e) {
             //   cl.log("frontLeft Jag exception, retrying...");
                Timer.delay(.25);
            }
        }
      
        while (rearLeftMotor == null) {
            try {
                rearLeftMotor = new CANJaguar(rearLeftCANID);
           //     cl.log("rearLeft Jag created successfully");
            } catch (Exception e) {
             //   cl.log("rearLeft Jag exception, retrying...");
                Timer.delay(.25);
            }
        }
 
        while (frontRightMotor == null) {
            try {
                frontRightMotor = new CANJaguar(frontRightCANID);
            //    cl.log("frontRight Jag created successfully");
            } catch (Exception e) {
            //    cl.log("frontRight Jag exception, retrying...");
                Timer.delay(.25);
            }
        }

        while (rearRightMotor == null) {
            try {
                rearRightMotor = new CANJaguar(rearRightCANID);
               
            } catch (Exception e) {
               // cl.log("rearRight Jag exception, retrying...");
                Timer.delay(.25);
            }
        }
    }

    private void configMotor(CANJaguar m, String desc, short i) {
        boolean keepTrying = true;
        short entryPoint = 0;
        String msg = " ";
        while (keepTrying) {
            try {
               switch (entryPoint) {
                   case 0:
                       msg = "setting Fault Time";
                       m.configFaultTime(0.5);
                       ++entryPoint;
                   case 1:
                       msg = "setting Speed Control Mode";
                       m.changeControlMode(CANJaguar.ControlMode.kSpeed);
                       ++entryPoint;
                   case 2:
                       msg = "setting speed & pos ref";
                       m.setSpeedReference(CANJaguar.SpeedReference.kEncoder);
                       m.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
                       ++entryPoint;
                   case 3:
                       msg = "setting Encoder Lines";
                       m.configEncoderCodesPerRev(encoderLines[i]);
                       ++entryPoint;
                   case 4:
                       msg = "setting PID gains";
                       m.setPID(pidGains[i][0], pidGains[i][1], pidGains[i][2]);
                       ++entryPoint;
                   case 5:
                       msg = "enabling control";
                       m.enableControl();
                       ++entryPoint;
                       keepTrying = false;
                   default:
                       break;
               }
            } catch (Exception e){
           //     cl.log("Error configuring " + desc + " in step " + msg + ", retrying...");
                Timer.delay(.1);
            }
        }
     //   cl.log(desc + " configured successfully");

    }

  //  public static final int frontLeftCANID = 12, frontRightCANID = 10,
    //                        rearLeftCANID = 11, rearRightCANID = 2;

   // public static final int elbowLeftCANID = 14, elbowRightCANID = 6;
   // public static final int shoulderLeftCANID = 8, shoulderRightCANID = 20;

    public static final short frontLeftIndex = 0,
                            frontRightIndex = 1,
                            rearLeftIndex = 2,
                            rearRightIndex = 3;
    public static final int encoderLines[] = {250, 250, 360, 350}; // FL, FR, RL, RR
    public static final short armTopMiddle = 0,
                              armMidMiddle = 1,
                              armFeeder = 2;
    public static final int armPositions[][] = { {590, 550},    // top middle
                                                 {160, 182},    // middle middle
                                                 {130, 230}     // feeder
                                               };
    // 452, 312 for top side,  move elbow to 481 to hang, then back to 322,216 and reverse

    public static final double minRPM = 0.0, maxRPM = 800.0;
    public static final double rangeRPM = (maxRPM - minRPM);
    public static final double wheelInchesPerRev = 18.849555921538759430775860299677;
    public static final double elbowMinPosition = 6, elbowMaxPosition = 955,
                               shoulderMinPosition = 130, shoulderMaxPosition = 900;

    public static final int compressorRelay = 1;
    public static final int gyroAI = 1, temperatureAI = 2,
                            elbowPotAI = 4, shoulderPotAI = 6;
    public static final int leftPhotoSwitchDI = 2, midPhotoSwitchDI = 1,
                            rightPhotoSwitchDI = 3, compressorSwitchDI = 5;
    public static final int clawSolenoid = 1;

    public static final int DeploymentServoPWM = 6;
    public static final int ReleaseServoPWM = 5;

    public static final byte Justdrive_Top = 99;
    public static final byte Justdrive_Mid = 1;
    public static final byte Justdrive_Low = 2;

    public static final byte Straightline_Top = 3;
    public static final byte Straightline_Mid = 4;
    public static final byte Straightline_Low = 5;

    public static final byte Forkleft_Top = 6;
    public static final byte Forkleft_Mid = 7;
    public static final byte Forkleft_Low = 8;

    public static final byte Forkright_Top = 9;
    public static final byte Forkright_Mid = 10;
    public static final byte Forkright_Low = 11;

    public static final double pidGains[][] = { {.350, .004, .001}, // FL
                                                {.350, .004, .001}, // FR
                                                {.350, .004, .001}, // RL
                                                {.350, .004, .001} }; // RR

    public void mecanumDrive(double forward, double turn, double rotate) {
    //Watchdog.getInstance().feed();
        if (++driveNow > 2)  {
            driveNow = 0;

            //if (forward > -0.01 && forward < 0.01) forward = 0;
            if (turn > -0.01 && turn < 0.01) turn = 0;
            //if (rotate > -0.01 && rotate < 0.01) rotate = 0;

            frontLeftMotorSpeed = forward + rotate + turn;
            frontRightMotorSpeed = forward - rotate - turn;
            rearLeftMotorSpeed = forward + rotate - turn;
            rearRightMotorSpeed = forward - rotate + turn;

            double max = Math.abs(frontLeftMotorSpeed);
            double temp = Math.abs(frontRightMotorSpeed);
            if (temp > max) max = temp;
            temp = Math.abs(rearLeftMotorSpeed);
            if (temp > max) max = temp;
            temp = Math.abs(rearRightMotorSpeed);
            if (temp > max) max = temp;

            if (max > 1.0) {
		frontLeftMotorSpeed /= max;
		frontRightMotorSpeed /= max;
		rearLeftMotorSpeed /= max;
		rearRightMotorSpeed /= max;
            }

//            if (frontLeftMotorSpeed < -.01 || frontLeftMotorSpeed > .01)
                frontLeftMotorSpeed = (minRPM + (frontLeftMotorSpeed * rangeRPM));
//            else frontLeftMotorSpeed = 0;
            if (frontRightMotorSpeed < -.01 || frontRightMotorSpeed > .01)
                frontRightMotorSpeed = (minRPM + (frontRightMotorSpeed * rangeRPM));
            else frontRightMotorSpeed = 0;
//            if (rearLeftMotorSpeed < -.01 || rearLeftMotorSpeed > .01)
                rearLeftMotorSpeed = (minRPM + (rearLeftMotorSpeed * rangeRPM));
//            else rearLeftMotorSpeed = 0;
//            if (rearRightMotorSpeed < -.01 || rearRightMotorSpeed > .01)
                rearRightMotorSpeed = (minRPM + (rearRightMotorSpeed * rangeRPM));
//            else rearRightMotorSpeed = 0;

            String msg = "";
            byte syncGroup = 0x20;
            try {
               
                msg = "frontLeftJag";
                frontLeftMotor.setX(-frontLeftMotorSpeed, syncGroup);
                msg = "frontRightJag";
                frontRightMotor.setX(frontRightMotorSpeed, syncGroup);
                msg = "rearLeftJag";
                rearLeftMotor.setX(-rearLeftMotorSpeed, syncGroup);
                msg = "rearRightJag";
                rearRightMotor.setX(rearRightMotorSpeed, syncGroup);
                msg = "syncGroup";
                CANJaguar.updateSyncGroup(syncGroup);
            } catch (Exception e) {
            //    cl.log("mecanumDrive error at step " + msg);
            }
            try
            {
                CANJaguar.updateSyncGroup(syncGroup);
            }
            catch (Exception e) {
           //     cl.log("mecanumDrive error at step " + msg);
        }
    }
    }

    public void mecanumDrive2(double forward, double turn, double rotate) {
    //Watchdog.getInstance().feed();
        if (++driveNow > 2)  {
            driveNow = 0;

            //if (forward > -0.01 && forward < 0.01) forward = 0;
            if (turn > -0.01 && turn < 0.01) turn = 0;
             if (forward > -0.01 && forward < 0.01) forward = 0;
             if (rotate > -0.01 && rotate < 0.01) rotate = 0;
            //if (rotate > -0.01 && rotate < 0.01) rotate = 0;

            frontLeftMotorSpeed = forward + rotate + turn;
            frontRightMotorSpeed = forward - rotate - turn;
            rearLeftMotorSpeed = forward + rotate - turn;
            rearRightMotorSpeed = forward - rotate + turn;

            double max = Math.abs(frontLeftMotorSpeed);
            double temp = Math.abs(frontRightMotorSpeed);
            if (temp > max) max = temp;
            temp = Math.abs(rearLeftMotorSpeed);
            if (temp > max) max = temp;
            temp = Math.abs(rearRightMotorSpeed);
            if (temp > max) max = temp;

            if (max > 1.0) {
		frontLeftMotorSpeed /= max;
		frontRightMotorSpeed /= max;
		rearLeftMotorSpeed /= max;
		rearRightMotorSpeed /= max;
            }

//            if (frontLeftMotorSpeed < -.01 || frontLeftMotorSpeed > .01)
                frontLeftMotorSpeed = (minRPM + (frontLeftMotorSpeed * rangeRPM));
//            else frontLeftMotorSpeed = 0;
            if (frontRightMotorSpeed < -.01 || frontRightMotorSpeed > .01)
                frontRightMotorSpeed = (minRPM + (frontRightMotorSpeed * rangeRPM));
            else frontRightMotorSpeed = 0;
//            if (rearLeftMotorSpeed < -.01 || rearLeftMotorSpeed > .01)
                rearLeftMotorSpeed = (minRPM + (rearLeftMotorSpeed * rangeRPM));
//            else rearLeftMotorSpeed = 0;
//            if (rearRightMotorSpeed < -.01 || rearRightMotorSpeed > .01)
                rearRightMotorSpeed = (minRPM + (rearRightMotorSpeed * rangeRPM));
//            else rearRightMotorSpeed = 0;

            String msg = "";
            byte syncGroup = 0x20;
            try {

                msg = "frontLeftJag";
                frontLeftMotor.setX(-frontLeftMotorSpeed, syncGroup);
                msg = "frontRightJag";
                frontRightMotor.setX(frontRightMotorSpeed, syncGroup);
                msg = "rearLeftJag";
                rearLeftMotor.setX(-rearLeftMotorSpeed, syncGroup);
                msg = "rearRightJag";
                rearRightMotor.setX(rearRightMotorSpeed, syncGroup);
                msg = "syncGroup";
                CANJaguar.updateSyncGroup(syncGroup);
            } catch (Exception e) {
             //   cl.log("mecanumDrive error at step " + msg);
            }
            try
            {
                CANJaguar.updateSyncGroup(syncGroup);
            }
            catch (Exception e) {
           //     cl.log("mecanumDrive error at step " + msg);
        }
    }
    }


    // try changing so that DIRECTION is applied to x axis (turn) as opposed
    // to z axis (rotate).
    // or, if still applying DIRECTION to the rotation, consider straightening
    // out once only the middle sensor is on (current we keep driving at diagonal)
    public LogomotionDriveTrain() {

        driveNow = 0;
        initMotors();
        configMotor(frontLeftMotor, "frontLeftJag", frontLeftIndex);
        configMotor(frontRightMotor, "frontRightJag", frontRightIndex);
        configMotor(rearLeftMotor, "rearLeftJag", rearLeftIndex);
        configMotor(rearRightMotor, "rearRightJag", rearRightIndex);
        //saveStartingPosition();
      //  lineTrackerLeft = new DigitalInput(leftPhotoSwitchDI);
       // lineTrackerMid = new DigitalInput(midPhotoSwitchDI);
       // lineTrackerRight = new DigitalInput(rightPhotoSwitchDI);
       //Gyroscope = new Gyro(gyroAI);
       // Temperature = new AnalogChannel(temperatureAI);
       // startTemp = (((Temperature.getVoltage()-2.5)*200)+77);
        //startTemp = ((startTemp-32)*5)/9;
        //();
        //updateGyroAndTemp();
       // StickWithIt = 0;
    }

}
