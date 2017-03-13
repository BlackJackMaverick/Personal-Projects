import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Vacationer extends Thread {
	
	private int name;
	/*
	 * A counter for the number of fish caught by a vacationer.
	 */
	public int caughtFish;
	
	public FishingRod[] rods;
	
	public Bait[] bait;
	
	//To indicate if this vacationer is the slacker that will delay releasing the rod and bait
	public boolean slacker;
	
	/*
	 * constructor
	 */
	public Vacationer(int name, FishingRod[] rods, Bait[] bait){
		caughtFish = 0;
		this.name = name;
		this.rods = rods;
		this.bait = bait;
		this.slacker = false;
	}
	
	public Vacationer(int name, FishingRod[] rods, Bait[] bait, boolean slacker){
		caughtFish = 0;
		this.name = name;
		this.rods = rods;
		this.bait = bait;
		this.slacker = slacker;
	}
	
	/*
	 * getter and setters 
	 */
	public int getCaughtFish(){
		return caughtFish;
	}
	
	//synchronized to act as a monitor
	public synchronized void addCaughtFish(int c){
		Vacation.addFishCaught(c);
		caughtFish++;
	}
	
	public void fish() throws InterruptedException{
		//used to indicated whether successfully acquired rod/bait
		boolean rodSuccess=false;
		boolean baitSuccess=false;
		
		//used to indicate which rod or bait was acquired
		int rodId =0;
		int baitId=0;
		
		//Try getting a rod (see if it's available)
		while(!rodSuccess){
			for(int i=0; i < rods.length; i++){
				rodSuccess = rods[i].useRod();
				rodId = i;
				if(rodSuccess){break;}//If acquired rod, break from loop
			}
		}
		System.out.println("Vacationer_" + name + " acquired rod_"+ (rodId+1) +".");
		
		//Try getting bait (see if it's available)
		while(!baitSuccess){
			for(int j=0; j < bait.length; j++){
				baitSuccess = bait[j].useBait();
				baitId = j;
				if(baitSuccess){break;}//If acquired bait, break from loop
			}
		}
		System.out.println("Vacationer_" + name + " acquired bait_" + (baitId+1) + ".");
		System.out.println("Vacationer_" + name + " is now fishing.");
		
		//Fish for 20 minutes (1 second)
		Thread.sleep(1000);
		
		//Catch a random number of fish between 1 and 10
		int caught = (int) (Math.random() * (10 - 1)) + 1;
		System.out.println("Vacationer_" + name + " caught " + caught + " fish. Adding to the bucket.");
		addCaughtFish(caught);
		
		//Wait 1 second before releasing resources (50 ms). If I am the slacker vacationer,
		//wait 100ms.
		if(slacker){
			System.out.println("**** Vacationer_"+name+" slacking. *****");
			Thread.sleep(100);
		}
		else{
			Thread.sleep(50);
		}
		
		//Release hold on rod and bait
		System.out.println("Vacationer_" + name + " stopped fishing. Releasing rod and bait.");
		rods[rodId].free();
		bait[baitId].free();
		
		//If at any point the thread terminates, release it's hold on the rods and bait
		if(isInterrupted()) {
			rods[rodId].free();
			bait[baitId].free();
		}
	}
	
	public void run(){
		
		//While alive, try fishing
		while (true){
			try{
				fish();
			}			
			catch (Exception e) {break;}
			if(isInterrupted()) {break;}
		}
		
		//System.out.println("Vacationer_"+name+" terminated.");
	}
}