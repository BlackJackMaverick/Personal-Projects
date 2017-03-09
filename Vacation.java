import java.util.concurrent.Semaphore;

public class Vacation implements Runnable {
	/*
	 * number of vacationers, initialized to 10 in constructor
	 */
	Vacationer[] Vacationers;
	/*
	 * semaphores to handle the number of available rods and bait
	 */
	Semaphore AvailableRods;
	Semaphore AvailableBait;
	/*
	 * constructor to initialize variables, and vacationers
	 */
	public Vacation(int R, int B){
		Vacationers = new Vacationer[10];
		AvailableBait=new Semaphore(B,true);
		AvailableRods=new Semaphore(R,true);
		for(int i =0;i<Vacationers.length;i++){
			Vacationers[i]=new Vacationer(this);
		}
	}
	
	public void run() {
		for(int i =0;i<Vacationers.length;i++){
			Vacationers[i].run();
		}
		/*
		 * Total running time for thread is 8 hours = 24*20mins = 24 seconds = 24000 milliseconds
		 */
		while(System.currentTimeMillis()< 24000){			
		}
	}

}
