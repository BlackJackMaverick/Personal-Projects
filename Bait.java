
public class Bait {

	public boolean available;
	
	public Bait(){
		this.available = true;
	}
	
	public synchronized boolean useBait(){
		if(!available){
			return false;
		}
		
		available = false;
		
		//so if successfully used bait, return true
		return true;
	}
	
	public synchronized void free(){
		available = true;
	}
	
}
