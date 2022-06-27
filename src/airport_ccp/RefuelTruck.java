package airport_ccp;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.Semaphore;
import java.util.LinkedList;
import java.util.Queue;


public class RefuelTruck extends Thread
{
    public static Queue<Plane> planeEmptyFuel = new LinkedList<Plane>();
    Semaphore reTruck = new Semaphore(1);
    int refueled = 0;
    
    public void run()
    {
        Plane plane;
        while(true)
        {
            synchronized (planeEmptyFuel)
            {
                while(refueled != ATC.maxP && planeEmptyFuel.isEmpty())
                {
                    System.out.println("REFUEL TRUCK\t : Waiting for a Plane...");
                    try
                    {
                        planeEmptyFuel.wait();
                    }
                    catch (InterruptedException e)
                    {
                        Logger.getLogger(RefuelTruck.class.getName()).log(Level.SEVERE, null, e);
                    }
                } 
                if (refueled == ATC.maxP)
                {
                    System.out.println("REFUEL TRUCK\t : COMPLETE !!! REFUELED ALL OF THE SIX PLANES");
                    break;
                }    
                plane = (Plane) planeEmptyFuel.poll();
                try
                {
                    reTruck.acquire();
                }
                catch (InterruptedException e)
                {
                    Logger.getLogger(RefuelTruck.class.getName()).log(Level.SEVERE, null, e);
                }
                System.out.println("REFUEL TRUCK\t : Refueling " + plane.getPlaneN() + " right now...");
                            
            }
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                Logger.getLogger(RefuelTruck.class.getName()).log(Level.SEVERE, null, e);
            }
            System.out.println("REFUEL TRUCK\t : Finish refuelling " + plane.getPlaneN());
            reTruck.release();
            plane.refueled = true;
            refueled++;
        }
            
    }
    
    public static void add(Plane plane)
    {
        System.out.println("REFUEL TRUCK\t :" + plane.getPlaneN() + " inserted into the Refuelling List");
        synchronized (planeEmptyFuel)
        {
            ((Queue<Plane>) planeEmptyFuel).add(plane);
            
            if(planeEmptyFuel.size() == 1)
            {
                planeEmptyFuel.notify();
            }
            
        }
    }
}
   
    
    

