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
public class temperatureSensor {
AnalogChannel temperature;
temperatureSensor(int a1)
    {
        temperature = new AnalogChannel(a1);
    }
public double getTemp()
    {
double currentTemp = 0.0d;
        currentTemp =(((temperature.getAverageVoltage()-2.5)*200)+77);
        currentTemp = ((currentTemp-32)*5)/9;
        return currentTemp;
    }
}
