package airport_ccp;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Queue;
import java.util.LinkedList;

public class ATC extends Thread
{
   Queue<Plane> pAir;
   Semaphore runway = new Semaphore(1);
   Semaphore gate = new Semaphore(2);
   
   public static int pLeft = 0; //COUNTER FOR THE NUMBER OF PLANE LEFT
   public static int maxP = 6; //MAX NUMBER OF PLANE
   
   public ATC() 
   {
       pAir = new LinkedList<Plane>();
   }
   
  public void Landing(Plane plane)
  {
      synchronized (pAir)       //GO INSIDE THE WAITING LIST
      {
          ((Queue<Plane>) pAir).add(plane);
          System.out.println("ATC\t\t :" + plane.getPlaneN() + " Queuing at [" + plane.getArrTime() + "]");
          plane.setFlyTime(System.currentTimeMillis());
      }
  }
  
  public void acquireGnR()      //ACQUIRE RUNWAY AND GATE TO LANDING & DOCK
  {
      try
      {
         gate.acquire();
         Plane plane = (Plane) pAir.poll();
         
         System.out.println("ATC\t\t :" + plane.getPlaneN() + " Trying to acquire the Runway for Landing ");
          
         runway.acquire();
          
         System.out.println("ATC\t\t :" + plane.getPlaneN() + " Acquired a Runway ");
      }
      catch(InterruptedException e)
      {
          Logger.getLogger(ATC.class.getName()).log(Level.SEVERE, null, e);
      }
     
  }
  
  public void acquireR (Plane plane) //ACQUIRE RUNWAY FOR DEPARTING
  {
      try
      {
          System.out.println("ATC\t\t :" + plane.getPlaneN() + " Trying to acquire the Runway for Departing ");
          runway.acquire();
      }
      catch(InterruptedException e)
      {
          Logger.getLogger(ATC.class.getName()).log(Level.SEVERE, null, e);
      }
  }
}

