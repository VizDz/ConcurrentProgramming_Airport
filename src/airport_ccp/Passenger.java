package airport_ccp;


import java.util.logging.Level;
import java.util.logging.Logger;


public class Passenger extends Thread {
    
    Plane plane;
    public static int totE = 0;
    public static int totD = 0;
    
    public Passenger(Plane plane)
    {
        this.plane = plane;
    }
    
    public void run()
    {
        dePass(); //DISEMBARK PASSENGERS
        ePass(); //EMBARK PASSENGERS
        
    }
    
    
    
    private void dePass() //FUNCTION TO DISEMBARK PASSENGERS
    {
        int rdm = (int) ((Math.random()*49) + 1); //RANDOM NUMBER OF PASSENGERS DISEMBARK
        
        System.out.println("PASSENGER THREAD :" + plane.getPlaneN()+ " Disembarking " + rdm + " Passengers and Undocking all of Their Luggages...");
        
        for(int i = 1; i <= rdm; i++)
        {
            try
            {
                System.out.println("\tPassengers Thread : Passenger " + i + " Disembarking from" + plane.getPlaneN() +"...");
                Thread.sleep(100); //INDICATE THE TIME TO DISEMBARKING ALL PASSENGERS & UNDOCKING THEIR LUGGAGES
            }
            catch (InterruptedException e)
            {
                Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        System.out.println("PASSENGER THREAD :" + plane.getPlaneN() + " Finish Disembarking " + rdm + " Passengers & Undocking Their Luggages :)");
        totD = totD + rdm;
        
    }
    
    
    private void ePass() //FUNCTION TO EMBARK PASSENGERS
    {
        int rdm = (int) ((Math.random()*49) + 1); //RANDOM NUMBER OF PASSENGERS EMBARK
        
        System.out.println("PASSENGER THREAD :" + plane.getPlaneN() + " Embarking " + rdm + " Passengers  & Docking Their Luggages...");
        
        for(int i = 1; i <= rdm; i++)
        {
            try
            {
                System.out.println("\tPassengers Thread : Passenger " + i + " Embarking to" + plane.getPlaneN() +"...");
                Thread.sleep(100); //INDICATE THE TIME TO EMBARKING NEW PASSENGERS & PUT THEIR LUGGAGES
            }
            catch (InterruptedException e)
            {
                Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        System.out.println("PASSENGER THREAD :" + plane.getPlaneN() + " Finish Embarking " + rdm + " Passengers :)");
        totE = totE + rdm;
    }
    
} 
