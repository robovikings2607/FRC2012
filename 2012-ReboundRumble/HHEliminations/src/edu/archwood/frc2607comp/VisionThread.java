
/**
 *
 *Author: Sean
 */
package edu.archwood.frc2607comp;

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
    String filename = "";
    private double timeStamp = 0;
    private double clockTime = 0;
    private double rectangleScore = 0;
    private double lastRectangleScore = 0;
    private int bestRectangleElementNumber = 0;
    private double currentDistance = 0;
    private int bestRectangleWidth = 0;
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
                    Timer.delay(4);
            System.out.println("Camera");
            camera = AxisCamera.getInstance();
            camera.writeResolution(AxisCamera.ResolutionT.k320x240);
            cc = new CriteriaCollection();
            System.out.println("RES:"+camera.getResolution().height);
    
            imageCollector = new Thread(this);
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
                               /* if (!filename.equals(""))
                {
            System.out.println("SAVEd");
            image.write(filename);  
            filename = "";
                }*/
                BinaryImage thresholdImage = image.thresholdRGB(171, 255, 45, 255, 217, 255);   // keep only objects that John wants
                BinaryImage convexHullImage = thresholdImage.convexHull(false);          // fill in occluded rectangles
                BinaryImage goodimage = convexHullImage.removeSmallObjects(false, 3);  // remove small artifacts
                BinaryImage filteredImage = goodimage.particleFilter(cc);           // find filled in rectangles
                reports = filteredImage.getOrderedParticleAnalysisReports();  // get list of result
                filteredImage.free();
                convexHullImage.free();
                thresholdImage.free();
                goodimage.free();
                image.free();
                 //Distance From Hoop Calculation
                for (int i = 0; i < reports.length; i++)
                {
                    rectangleScore = reports[i].particleArea/(reports[i].boundingRectHeight * reports[i].boundingRectWidth);
                    if (rectangleScore > lastRectangleScore)
                    {
                        bestRectangleElementNumber = i;
                        lastRectangleScore = rectangleScore; 
                    }
                }
                //the above for loop gives me the rectangle with the square-est dimentions (ie not distorted)
                if (bestRectangleElementNumber < reports.length){
                    bestRectangleWidth = reports[bestRectangleElementNumber].boundingRectWidth;
 //                   currentDistance = (((1.5*(240/bestRectangleHeight))/2)/Math.toDegrees(Math.tan(24)));
                    currentDistance = ((640/bestRectangleWidth)/2)/Math.tan(Math.toRadians(24));
                }
                //above calculation is a geomatric calculation to figure the distance from the backboard based on known dimensions of the target and its pixel width
                //End Distance From Hoop Calculation
                clockTime = System.currentTimeMillis()-timeStamp;
                Thread.sleep(10);
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
                /* try {
             if (!filename.equals(""))
                {
               
            ColorImage image = camera.getImage();
            image.write(filename);  
            System.out.println("Imageatized");
            image.free();
            //System.out.println("In another life, I saw myself, way back then, back when I was new. Somewhere down the line I started to slip years gone by, biting my lip. All this time, all the while I knew. Now you're on your own, one for the pages, over the hill and through the ages does my heaven burn like **** on you? Out beneath the cracks and coming in waves, rolling like an earthquake under the pages, Heavy now, tell me Mr. True");
            filename = "";
                }
                Thread.sleep(50);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }*/
             }
            
    }
    }
    
    public int getWidth()
    { 
        return bestRectangleWidth;
    }
    public double getDistance()
    {
        return currentDistance;
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
    public void saveImage(String fname)
    {
      // filename = fname;
    }
}
