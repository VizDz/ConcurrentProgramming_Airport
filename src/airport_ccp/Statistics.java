package airport_ccp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class Statistics {
  
    public static ArrayList<Long> report1 = new ArrayList<Long>();
    public static ArrayList<Long> report2 = new ArrayList<Long>();
   
    private static final DecimalFormat df = new DecimalFormat("0.00");
    
    long tot1 = 0;
    long tot2 = 0;
    
    public static void report()
    {
         System.out.println("\n===== STATISTICS =====");
         
         System.out.println("Number of Plane\t\t: " + ATC.maxP);
         System.out.println("Number of Plane Served\t: " + (ATC.maxP - ATC.pLeft));
         
         System.out.println("Number of Gate\t\t: 2");
         System.out.println("Number of Empty Gate\t: 2");
         
         System.out.println("\nTotal of Passengers (Disembarking)\t: " +  Passenger.totD);
         System.out.println("Total of Passengers (Embarking)\t\t: " +  Passenger.totE);
          
         
         //THIS TIME START FROM THE PLANE ENTER THE RUNWAY TILL FINISH DEPARTING
         System.out.println("\n=== TIME TO LANDING - DEPARTING ==="); 
         for(int i = 0; i < report1.size(); i++)
         {
             System.out.print(" | " + report1.get(i));
         }
         System.out.println("\nMax Time : " + Collections.max(report1) + " miliseconds");
         System.out.println("Min Time : " + Collections.min(report1) + " miliseconds");
         System.out.println("Total Time : " + Plane.tot1+ " miliseconds");
         System.out.println("Average Time : " + df.format(Plane.tot1 / ATC.maxP) + " miliseconds");
         
         
         //THIS TIME START FROM THE PLANE ENTER THE WAITING LIST TO USE THE RUNWANY TILL THE PLANE GET THE RUNWAY     
         System.out.println("\n=== TIME WAITING IN THE SKY (WAITING LIST) ==="); 
         for(int i = 0; i < report2.size(); i++)
         {
             System.out.print(" | " + report2.get(i));
         }
         System.out.println("\nMax Time : " + Collections.max(report2) + " miliseconds");
         System.out.println("Min Time : " + Collections.min(report2) + " miliseconds");
         System.out.println("Total Time : " + Plane.tot2+ " miliseconds");
         System.out.println("Average Time : " + df.format(Plane.tot2 / ATC.maxP) + " miliseconds");
         
    }
    
    
}
 

