package airport_ccp;


import java.util.logging.Level;
import java.util.logging.Logger;


public class CleanSupply extends Thread {
    
    Plane plane;
    
    public CleanSupply(Plane plane)
    {
        this.plane = plane;
    }
    
    public void run()
    {
        clean(); //CLEANING PLANE
        reSupply(); //RESUPPLYING STOCKS
    }
    
    
    private void clean() //CLEANING THE PLANE
   {
       try
       {
           System.out.println("CLN & SUP TEAM\t : Cleaning " + plane.getPlaneN() + " now...");
           Thread.sleep(1000);  //INDICATE TIME FOR CLEANING
           
       }
       catch (InterruptedException e)
       {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, e);
       }
       System.out.println("CLN & SUP TEAM\t : Finish Cleaning" + plane.getPlaneN() + " :)");
   }

  
   private void reSupply() //RESUPPLYING THE PLANE
   {    
       try
       {
           System.out.println("CLN & SUP TEAM\t : Resupplying Stocks for " + plane.getPlaneN() + " now...");
           Thread.sleep(1000);  //INDICATE TIME FOR RESUPPLYING 
       }
       catch (InterruptedException e)
       {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, e);
       }
       System.out.println("CLN & SUP TEAM\t : Finish Resupplying" + plane.getPlaneN() + " :)");
   }
  
    
} 
