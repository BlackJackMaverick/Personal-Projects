
public class Bait {

	public boolean available;
	
	public Bait(){
		this.available = true;
	}
	
	//Tries to use bait if it is available. Returns boolean if it was successfully acquired or not
	public synchronized boolean useBait(){
		if(!available){
			return false;
		}
		
		available = false;
		
		//so if successfully used bait, return true
		return true;
	}
	
	//Releases hold on bait
	public synchronized void free(){
		available = true;
	}
	
}
