/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.archwood.frc2607;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 *
 *Author: John
 */
public class VisionThread implements Runnable{
    private ParticleAnalysisReport[] reports;
    private AxisCamera camera;
    private CriteriaCollection cc;
    private Thread imageCollector;
    private boolean processing = false;
    private double timeStamp = 0;
    private double clockTime = 0;
    public VisionThread()
    {
            Timer.delay(8);
            System.out.println("Camera");
            camera = AxisCamera.getInstance();
            camera.writeResolution(AxisCamera.ResolutionT.k320x240);
            cc = new CriteriaCollection();
            System.out.println("RES:"+camera.getResolution().height);
            imageCollector = new Thread(this);
    }
    public void wakeUp()
    {
        processing = true;
    }
    public void gotoBed()
    {
        processing = false;
    }
    public void init()
    {
        imageCollector.start();
    }
    public void run()
    {
    while (true)
    {
          
             if (processing)
             {
             try
             {
                timeStamp = System.currentTimeMillis();
                ColorImage image;
                image = camera.getImage();
                BinaryImage thresholdImage = image.thresholdRGB(150, 255, 00, 180, 0, 180);   // keep only green objects
                BinaryImage bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);  // remove small artifacts
                BinaryImage convexHullImage = bigObjectsImage.convexHull(false);          // fill in occluded rectangles
                BinaryImage goodimage = convexHullImage.removeSmallObjects(false, 2);  // remove small artifacts
                BinaryImage filteredImage = goodimage.particleFilter(cc);           // find filled in rectangles
                reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of result
                filteredImage.free();
                convexHullImage.free();
                bigObjectsImage.free();
                thresholdImage.free();
                goodimage.free();
                image.free();
                clockTime = System.currentTimeMillis()-timeStamp;
                Thread.sleep(20);
            }
            catch (AxisCameraException ex) {
                ex.printStackTrace();
            }
            catch (NIVisionException ex) {
                ex.printStackTrace();
            }catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            }
             else
             {
                 try {
                Thread.sleep(40);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
             }
            
    }
    }
    public ParticleAnalysisReport[] getReport()
    {
        if (reports==null)
        {
            return new ParticleAnalysisReport[0];
        }
        return reports;
    }
    public double getTimer()
    {
        return clockTime;
    }
}
