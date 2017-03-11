import java.util.concurrent.Semaphore;

public class Vacation {
	/*
	 * queue of vacationers using a FIFO model, initialized to 10 in constructor.
	 */
	private Vacationer[] Vacationers;
	/*
	 * array of caught fish
	 */
	private int[] FishCaught;
	/*
	 * semaphores to handle the number of available rods and bait
	 */
	private Semaphore AvailableRods;
	private Semaphore AvailableBait;
	/*
	 * constructor to initialize variables, and vacationers
	 */
	public Vacation(int R, int B){
		Vacationers = new Vacationer[10];
		FishCaught = new int[10];
		AvailableBait=new Semaphore(B,true);
		AvailableRods=new Semaphore(R,true);
		for(int i =0;i<Vacationers.length;i++){
			Vacationers[i]=new Vacationer(this,i);
			FishCaught[i]=0;
		}
	}
	/*
	 * getters and setters
	 */
	public int[] getFishCaught(){
		return FishCaught;
	}
	public void setFishCaught(int[] i){
		FishCaught=i;
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
		/*
		 * Assuming all threads execute at same time, the start of a thread is the same for all others, 
		 * as well when one thread terminates as do all others
		 */
		for(int i=0;i<Vacationers.length;i++){
			Vacationers[i].exec();
		}
		/*
		 * if a thread is still alive do not proceed. However assuming all threads begin and end at the same time,
		 * if one thread terminates so do the others
		 */
		while(!Vacationers[9].isAlive());
		/*
		 * print out of fish caught by vacationers
		 */
		for(int i=0;i<Vacationers.length;i++){
			System.out.println("Vacationer " + Vacationers[i].getVacationersName()+ " caught: " + Vacationers[i].getCaughtFish()+ " fish.");
		}
		/*
		 * Calculation and printout of total fish caught
		 */
		int total=0;
		for(int i=0;i<Vacationers.length;i++){
			total= total+Vacationers[i].getCaughtFish();
		}
		System.out.println("The total amount of fish caught is: " + total);
}
	/*
	 * main method
	 */
	public static void main(String args[]){
		/*
		 * parameters of vacation to change the size of bait and rod semaphores. 
		 */
		Vacation v = new Vacation(3,3);
		v.Execute();
	}
}