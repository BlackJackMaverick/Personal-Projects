import java.awt.List;
import java.util.concurrent.Semaphore;

public class Vacation {
	
	/*
	 * queue of vacationers using a FIFO model, initialized to 10 in constructor.
	 */
	public static Vacationer[] vacationers;
	/*
	 * array of caught fish
	 */
	public static int fishCaught = 0;
	/*
	 * semaphores to handle the number of available rods and bait
	 */
	private Semaphore AvailableRods;
	private Semaphore AvailableBait;
	/*
	 * constructor to initialize variables, and vacationers
	 */
//	public Vacation(int R, int B){
//		Vacationers = new Vacationer[10];
//		FishCaught = new int[10];
//		AvailableBait=new Semaphore(B,true);
//		AvailableRods=new Semaphore(R,true);
//		for(int i =0;i<Vacationers.length;i++){
//			Vacationers[i]=new Vacationer(this,i);
//			FishCaught[i]=0;
//		}
//	}
	/*
	 * getters and setters
	 */
	public static int getFishCaught(){
		return fishCaught;
	}
	public static void addFishCaught(int c){
		fishCaught = fishCaught + c;
	}
	public Semaphore getAvailableBait(){
		return AvailableBait;
	}
	public void setAvailableBait(Semaphore s){
		AvailableBait=s;
	}
	public Semaphore getAvailableRods(){
		return AvailableRods;
	}
	public void setAvailableRods(Semaphore s){
		AvailableRods=s;
	}
	public void Execute() {			
//		/*
//		 * Assuming all threads execute at same time, the start of a thread is the same for all others, 
//		 * as well when one thread terminates as do all others
//		 */
//		for(int i=0;i<Vacationers.length;i++){
//			Vacationers[i].exec();
//		}
//		/*
//		 * if a thread is still alive do not proceed. However assuming all threads begin and end at the same time,
//		 * if one thread terminates so do the others
//		 */
//		while(!Vacationers[9].isAlive());
//		/*
//		 * print out of fish caught by vacationers
//		 */
//		for(int i=0;i<Vacationers.length;i++){
//			System.out.println("Vacationer " + Vacationers[i].getVacationersName()+ " caught: " + Vacationers[i].getCaughtFish()+ " fish.");
//		}
//		/*
//		 * Calculation and printout of total fish caught
//		 */
//		int total=0;
//		for(int i=0;i<Vacationers.length;i++){
//			total= total+Vacationers[i].getCaughtFish();
//		}
//		System.out.println("The total amount of fish caught is: " + total);
}
	/*
	 * main method
	 */
	public static void main(String args[]){
		
		System.out.println("Starting vacation");
		
		//Array of rods
		FishingRod[] rods = new FishingRod[3];
		for (int i=0; i < rods.length; i++){
			rods[i] = new FishingRod();
		}
		
		//Array of bait
		Bait[] bait = new Bait[3];
		for (int i=0; i < bait.length; i++){
			bait[i] = new Bait();
		}
		
		//Make and start 10 vacationer threads
		vacationers = new Vacationer[10];
		for (int i=0; i < vacationers.length; i++){
			vacationers[i] = new Vacationer(i+1, rods, bait);
			vacationers[i].start();
		}
		
		
		//Day of 8 hours = 24 seconds. For quicker testing, put 10 seconds.
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//After the day finishes, terminate the vacationers
		for (int i=0; i < vacationers.length; i++){
			vacationers[i].interrupt();
		}
		System.out.println("Done. Caught " + fishCaught + " fish in total.");
	}
}