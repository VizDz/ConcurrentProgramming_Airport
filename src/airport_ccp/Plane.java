package airport_ccp;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

public class Plane extends Thread
{
   private String pName;
   private volatile long landTime;
   private volatile long depTime;
   private volatile long flyTime;
   
   public static double tot1 = 0;
   public static double tot2 = 0;
   
   ATC atc;
   Date arrTime;
   
   public boolean refueled = false;
   
   public void setLandTime (long landTime)
   {
       this.landTime = landTime;
   }
   public long getLandTime()
   {
       return landTime = landTime;
   }
   
   
   public void setDepTime (long depTime)
   {
       this.depTime = depTime;
   }
   public long getDepTime ()
   {
       return depTime = depTime;
   }
   
   
   public void setFlyTime (long flyTime)
   {
       this.flyTime = flyTime;
   }
   public long getFlyTime()
   {
       return flyTime = flyTime;
   }
   
   
    public void setPlaneN (String pName)
   {
       this.pName = pName;
   }   
   public String getPlaneN()
   {
       return pName = pName;
   }
  
   
   public Plane(ATC atc)
   {
       this.atc = atc;
   }
   
   
   public void setArrTime(Date arrTime)
   {
       this.arrTime = arrTime;
   }
   public Date getArrTime()
   {
       return arrTime = arrTime;
   }
   
   
   
   private void reqForLanding()
   {
       System.out.println("PLANE THREAD\t :" + this.getPlaneN() + " Request for Landing...");
       atc.Landing(this);
       
       System.out.println("PLANE THREAD\t :" + this.getPlaneN() + " Trying to Acquire a Gate and a Runway...");
       atc.acquireGnR();
   }
   
   
   
   private void ldSeq() //LANDING SEQUENCE
   {
       setLandTime(System.currentTimeMillis());     
       Statistics.report2.add((getLandTime()-getFlyTime()));
       tot2 = tot2 + (getLandTime()-getFlyTime());
       System.out.println("PLANE THREAD\t : =====" + this.getPlaneN() + " Spends " + (getLandTime()-getFlyTime()) + " miliseconds Waiting in the Sky =====");
       System.out.println("PLANE THREAD\t :" + this.getPlaneN() + " Landing at the Runway...");
       
       try
       {
           Thread.sleep(1500); //INDICATE TIME TO USE RUNWAY FOR LANDING
           System.out.println("PLANE THREAD\t :" + this.getPlaneN() + " Docking to the Gate...");
           Thread.sleep(1500);  //INDICATE TIME TO DOCK AT THE GATE
       }
       catch (InterruptedException e)
       {
           Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, e);
       }
       System.out.println("PLANE THREAD\t :" + this.getPlaneN() + " Landing Successfull & Docked at the Gate :)");
       atc.runway.release();
   }
   
   
   private void apSeq() //AIRPORT SEQUENCE
   {
       Passenger passenger = new Passenger (this);
       CleanSupply cleanSupply = new CleanSupply(this);
       Thread threadPassenger = new Thread(passenger);
       Thread threadCleanSupply = new Thread(cleanSupply);
       threadPassenger.start();
       threadCleanSupply.start();
       RefuelTruck.add(this);
             
       try
       {
           threadPassenger.join();
           threadCleanSupply.join();
       }
       catch (InterruptedException e)
       {
           Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, e);
       }
       while(refueled == false) //THE PLANE WILL WAIT IF HAVEN'T REFUELED YET
       {
           System.out.println("PLANE THREAD\t :" + this.getPlaneN() + " Not Refeuled Yet, Waiting on REFUELLING...");
           try
           {
               Thread.sleep(2000); 
           }
           catch(InterruptedException e)
           {
               Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, e);
           }
       }
   }
   
   
   private void dpSeq() //DEPARTURE SEQUENCE
   {
       atc.acquireR(this);
       System.out.println("PLANE THREAD\t :" + this.getPlaneN() + " Undocking from the Gate...");
       try
       {
           Thread.sleep(1500);//INDICATE TIME TO UNDOCKING FROM THE GATE
           System.out.println("PLANE THREAD\t :" + this.getPlaneN() + " Using the Runway for Departing...");
           
           Thread.sleep(1500); //INDICATE TIME TO USING RUNWAY TO DEPARTING
           System.out.println("PLANE THREAD\t :" + this.getPlaneN() + " Success Departed from the Airport :)");
           atc.gate.release(); //RELEASE THE RUNWAY AFTER FINISH
           atc.runway.release(); //RELEASE THE GATE AFTER FINISH
       }
       catch (InterruptedException e)
       {
           Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, e);
       }
       setDepTime(System.currentTimeMillis()); //CAPTURE THE TIME NEEDED TILL DEPARTURE
       
       System.out.println("PLANE THREAD\t : ========" + this.getPlaneN() + " Spends " + ((getDepTime()- getLandTime())) + " miliseconds in the Airport to Landing & Departing ========");
       Statistics.report1.add((getDepTime()- getLandTime()));
       tot1 = tot1 + (getDepTime()-getLandTime());
       ATC.pLeft--; //DECREASE THE NUMBER OF PLANE LEFT AFTER FINISH DEPARTING
   }
   
   
    public void run()
   {
       reqForLanding(); //REQUEST FOR LANDING
       ldSeq();         //LANDING SEQUENCE (LANDING + DOCKING)
       apSeq();         //AIRPORT SEQUENCE
       dpSeq();         //DEPARTURE SEQUENCE
       
       //CHECK IF NO PLANE LEFT IN THE AIRPORT + BOTH RUNWAY & 2 GATES ARE EMPTY
       if(ATC.pLeft == 0 && atc.runway.availablePermits() == 1 && atc.gate.availablePermits() == 2) 
       {
           System.out.println("===== FINISH =====");
           Statistics.report(); //FUNCTION FOR STATISTICS
       }
   }
}
