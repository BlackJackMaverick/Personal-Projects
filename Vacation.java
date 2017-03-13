import java.awt.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Vacation {
	
	/*
	 * queue of vacationers using a FIFO model, initialized to 10 in constructor.
	 */
	public static Vacationer[] vacationers;
	/*
	 * array of caught fish
	 */
	public static int fishCaught;
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
		
		//Scanner for collecting rod and bait numbers
		Scanner sc = new Scanner(System.in);
		
		//Number of rods and bait for experiment:
		System.out.print("\nEnter number of rods: ");
		int r = sc.nextInt();
		System.out.print("\nEnter number of bait: ");
		int b = sc.nextInt();
		System.out.print("\nInclude a slacker for this experiment? (y/n): ");
		String s = sc.next();
		
		//To do five trials, variable to store trial number
		int trial;
		//Array to store fish caught per vacationer for calculating averages + for totals
		int[][] avg = new int[10][5];
		int[] totals = new int[5];
			
		//Run 5 trials
		for(trial = 1; trial <= 5; trial++){
			
		//Array of rods
		FishingRod[] rods = new FishingRod[r];
		for (int i=0; i < rods.length; i++){
			rods[i] = new FishingRod();
		}
		
		//Array of bait
		Bait[] bait = new Bait[b];
		for (int i=0; i < bait.length; i++){
			bait[i] = new Bait();
		}
		
		System.out.println("============= Starting vacation: Trial "+trial+" =============");
		
		fishCaught = 0;
		
		//Sleep for legibility of print statements
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Make 10 vacationer threads and start. Make 1 slacker if the user has asked for one.
		vacationers = new Vacationer[10];
		if(s.equals("y")){
			vacationers[0] = new Vacationer(1, rods, bait, true);
			vacationers[0].start();
		}
		else {
			vacationers[0] = new Vacationer(1, rods, bait);
			vacationers[0].start();
		}
		for (int i=1; i <= 9; i++){
			vacationers[i] = new Vacationer(i+1, rods, bait);
			vacationers[i].start();
		}
		
		
		//Day of 8 hours = 24 seconds
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//After trials finish, count fish per vacationer and terminate.
		for (int i=0; i < vacationers.length; i++){
			avg[i][trial-1] = vacationers[i].getCaughtFish();
			vacationers[i].interrupt();
		}
		//store total caught fish for this trial.
		totals[trial-1] = fishCaught;
		
		System.out.println("\nVacation over. Caught " + fishCaught + " fish in total.");
		
		//Small wait until next trial
		if(trial != 5){
			System.out.println("Waiting 5 seconds to start next trial.\n");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		}//end of trials
		
		//Print experiment results
		System.out.println("\nExperiment results for "+r+" rods and "+b+" baits: ");
		System.out.println("Average fish caught by: ");
		for(int i=0; i <10; i++){
			System.out.println("Vacationer_"+(i+1)+": "+((avg[i][0]+avg[i][1]+avg[i][2]+avg[i][3]+avg[i][4])/5));
		}
		System.out.println("Average total caught: " + ((totals[0]+totals[1]+totals[2]+totals[3]+totals[4])/5) + "\n");
	}
}