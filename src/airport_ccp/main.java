package airport_ccp;


public class main 
{
    public static void main(String[] args) {
         
        System.out.println("============ AIRPORT SIMULATION ============");
        System.out.println("===== START =====");
        ATC atc = new ATC();
        PlaneGenerator pGen = new PlaneGenerator(atc);
        RefuelTruck refTruck = new RefuelTruck();
        
        Thread threadPlaneGen = new Thread(pGen);
        Thread threadRefTruck = new Thread(refTruck);
        
        threadPlaneGen.start();
        threadRefTruck.start();    
    }
    
}
