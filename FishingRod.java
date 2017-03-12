
public class FishingRod {
	
	public boolean available;
	
	public FishingRod(){
		this.available = true;
	}
	
	public synchronized boolean useRod(){
		if(!available){
			return false;
		}
		
		available = false;
		
		//so if successfully used rod, return true
		return true;
	}
	
	public synchronized void free(){
		available = true;
	}

}
