import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Vacationer extends Thread {
	
	private int name;
	/*
	 * the instance variable 'fishingTools' represents if a vacationer has a fishing rod and/or bait
	 * fishingTools[0] represents the acquisition of a fishing rod. True value indicates the vacationer has a rod
	 * fishingTools[1] represents the acquisition of some bait. True value indicates the vacationer has some bait.
	 */
	private boolean[] fishingTools;
	/*
	 * A counter for the number of fish caught by a vacationer.
	 */
	private int caughtFish;
	
	public FishingRod[] rods;
	
	public Bait[] bait;
	
	/*
	 * constructor sets fishingTools entries to false, initializes number of Caught fish
	 * and maps the vacationer thread to another main thread.
	 * ----removed vacation and n parameters
	 */
	public Vacationer(int name, FishingRod[] rods, Bait[] bait, int fishCaught){
		fishingTools = new boolean[2];
		fishingTools[0]=false;
		fishingTools[1]=false;
		caughtFish = 0;
		this.name = name;
		this.rods = rods;
		this.bait = bait;
		this.caughtFish = fishCaught;
	}
	/*
	 * getter and setters 
	 */
	public int getCaughtFish(){
		return caughtFish;
	}
	public synchronized void addCaughtFish(int c){
		caughtFish=caughtFish + c;
	}
	public int getVacationersName(){
		return this.name;
	}
//	public void SetName(int i){
//		name = i;
//	}
	
	public void exec(){
//		while(System.currentTimeMillis()< 24000){
//		/*
//		 * Use tryAcquire to check availability of rods/bait. If none is initially available then the thread will wait 50ms 
//		 * if no such rod becomes available then the thread progresses to try and acquire some bait. 
//		 * Repeat process until bait and/or rods become available without causing unnecessary deadlocks
//		 */
//		do{
//			try {
//				if(vac.getAvailableRods().tryAcquire(50, TimeUnit.MILLISECONDS)){
//				fishingTools[0]=true;
//				System.out.println("fishing rod acquired for vacationer: "+ name);
//				}
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
//			try{
//				if(vac.getAvailableBait().tryAcquire(50,TimeUnit.MILLISECONDS)){
//					fishingTools[1]=true;
//					System.out.println("bait acquired for vacationer: " + name);
//			}
//			}catch(Exception e){e.printStackTrace();
//		}
//	}while(fishingTools[0]&&fishingTools[1]);
//			/*
//			 * if both tools are obtained increment fish caught
//			 */
//				Random r = new Random();
//				setCaughtFish(getCaughtFish() + r.nextInt(10));
//				/*
//				 * time spent fishing is 20 minutes = 1 second = 1000ms.
//				 */
//				try{sleep(1000);}catch(Exception e){e.printStackTrace();}
//				/*
//				 * release bait, wait 50ms, then release rod.
//				 */
//				try{vac.getAvailableBait().release();}catch(Exception e){e.printStackTrace();}
//				fishingTools[1]=false;
//				System.out.println("bait released by vacationer: " + name); 
//	
//				try{sleep(50);}catch(Exception e){e.printStackTrace();}
//				
//				try{vac.getAvailableRods().release();}catch(Exception e){e.printStackTrace();}
//				fishingTools[0]=false;
//				System.out.println("Rod released by vacationer: " + name);
//	}
	}
	
	public void fish(){
		//used to indicated whether successfully acquired rod/bait
		boolean rodSuccess=false;
		boolean baitSuccess=false;
		int rodId =0;
		int baitId=0;
		
		//check if any rod is available
		while(!rodSuccess){
			for(int i=0; i < rods.length; i++){
				rodSuccess = rods[i].useRod();
				rodId = i;
			}
		}
		System.out.println("Vacationer_" + name + " acquired a rod.");
		
		while(!baitSuccess){
			for(int j=0; j < bait.length; j++){
				baitSuccess = bait[j].useBait();
				baitId = j;
			}
		}
		System.out.println("Vacationer_" + name + " acquired bait.");
		
		System.out.println("Vacationer_" + name + " is now fishing.");
		
		int caught = (int) (Math.random() * (1 - 10)) + 1;
		System.out.println("Vacationer_" + name + " caught " + caught + " fish. Adding to the bucket.");
		addCaughtFish(caught);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Test values: " + rodId + " and " + baitId );
		rods[rodId].free();
		bait[baitId].free();
		
		System.out.println("Vacationer_" + name + " stopped fishing.");
		
	}
	
	public void run(){
		while (true){
			fish();
		}
	}
}