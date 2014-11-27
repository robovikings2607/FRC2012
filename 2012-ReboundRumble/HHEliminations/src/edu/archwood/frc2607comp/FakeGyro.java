/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.archwood.frc2607comp;

import edu.wpi.first.wpilibj.AnalogChannel;

/**
 *
 *Author: John
 */
public class FakeGyro {
AnalogChannel sensor;
temperatureSensor temp;
double new_rate, cumulated, currentTemp;
FakeGyro(int i, temperatureSensor t)
    {
        sensor = new AnalogChannel(i);
        temp = t;
    }
public void reset()
    {
    cumulated = 0;
}
public double getRelativeAngle()
    {
    currentTemp = temp.getTemp();
     new_rate = (sensor.getVoltage()-2.567675991)+((currentTemp-25)*.001);
            
        cumulated+=new_rate;
        return cumulated;
    }
}
