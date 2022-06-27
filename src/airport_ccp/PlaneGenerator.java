package airport_ccp;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PlaneGenerator extends Thread
{
   ATC atc;
   
   public PlaneGenerator(ATC atc)
   {
       this.atc = atc;
   }
   
   public void run()
   {
       for(int i = 1; i <=ATC.maxP; i++)
       {
           Plane plane = new Plane(atc);
           plane.setArrTime(new Date());
           
           Thread threadPlane = new Thread(plane);
           
           plane.setPlaneN(" Plane " + i);
           
           threadPlane.start();
           ATC.pLeft++; //INCREASE THE NUMBER PLANE LEFT
           try
           {
               //INDICATES SUMMONING PLANES IN RANDOM EVERY 0-3 SECONDS
               Thread.sleep(new Random().nextInt(3000)); 
           }
           catch (InterruptedException e)
           {
               e.printStackTrace();
           }          
       }         
       return;
   }
   
}
