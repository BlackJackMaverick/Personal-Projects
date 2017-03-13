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
//	private Semaphore AvailableRods;
//	private Semaphore AvailableBait;

	/*
	 * getters and setters
	 */
	public static int getFishCaught(){
		return fishCaught;
	}
	public static void addFishCaught(int c){
		fishCaught = fishCaught + c;
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
		
		System.out.println("\n============= Starting vacation: Trial "+trial+" =============");
		
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
			Thread.sleep(24000);
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
			System.out.println("Waiting 5 seconds to start next trial.");
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
		System.out.println("Average total caught: " + ((totals[0]+totals[1]+totals[2]+totals[3]+totals[4])/5));
		
	}
}