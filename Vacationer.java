import java.util.Random;

public class Vacationer implements Runnable {
	/*
	 * the instance variable 'fishingTools' represents if a vacationer has a fishing rod and/or bait
	 * fishingTools[0] represents the acquisition of a fishing rod. True value indicates the vacationer has a rod
	 * fishingTools[1] represents the acquisition of some bait. True value indicates the vacationer has some bait.
	 */
	private boolean[] fishingTools;
	/*
	 * The boolean variable 'ReadyToFish' suggests if a vacationer would like the opportunity to fish.
	 * If this instance variable is set to true then the vacationer would like to fish
	 * If this instance variable is set to false the vacationer has already fished
	 */
	private boolean ReadyToFish;
	/*
	 * A counter for the number of fish caught by a vacationer.
	 */
	private int CaughtFish;
	private Vacation vac;
	
	/*
	 * constructor sets fishingTools entries to false, and ReadyToFish to true by default
	 */
	public Vacationer(Vacation v){
		fishingTools = new boolean[2];
		fishingTools[0]=false;
		fishingTools[1]=false;
		ReadyToFish = true;
		CaughtFish = 0;
		vac=v;
	}
	/*
	 * These release and acquire methods toggle what the fishingTools the vacationer possesses.
	 */
	public void acquireFishingRod(){
		fishingTools[0]=true;
	}
	public void releaseFishingRod(){
		fishingTools[0]=false;
	}
	public void acquireBait(){
		fishingTools[1]=true;
	}
	public void releaseBait(){
		fishingTools[0]=false;
	}
	/*
	 * getter and setters for the number of caught fish.
	 */
	public int getCaughtFish(){
		return CaughtFish;
	}
	public void setCaughtFish(int c){
		CaughtFish=c;
	}
	
	public void run(){
		
		while(ReadyToFish){
			/*
			 * Attempt to acquire a fishing rod and/or bait, if successful change fishingTools[0], and/or fishingTools[1]
			 */
			try{vac.AvailableRods.acquire();}catch(Exception e){e.printStackTrace();}
			fishingTools[0]=true;
			try{vac.AvailableBait.acquire();}catch(Exception e){e.printStackTrace();}
			fishingTools[1]=true;
			/*
			 * if both tools are obtained try to fish
			 */
			if(fishingTools[0]&&fishingTools[1]){
				/*
				 * fish caught added to total
				 */
				Random r = new Random();
				CaughtFish = CaughtFish + r.nextInt(10);
				/*
				 * time spent fishing is 20 minutes = 1 second = 1000ms.
				 */
				try{sleep(1000);}catch(Exception e){e.printStackTrace();}
				/*
				 * release bait, wait 50ms, then release rod.
				 */
				try{vac.AvailableBait.release();}catch(Exception e){e.printStackTrace();}
				fishingTools[1]=false;
				try{sleep(50);}catch(Exception e){e.printStackTrace();}
				try{vac.AvailableRods.release();}catch(Exception e){e.printStackTrace();}
				fishingTools[0]=false;
			}
	}
}
}
