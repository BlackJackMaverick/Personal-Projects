
public class FishingRod {
	
	public boolean available;
	
	public FishingRod(){
		this.available = true;
	}
	
	//Tries to use rod if it is available. Returns boolean if it was successfully acquired or not
	public synchronized boolean useRod(){
		if(!available){
			return false;
		}
		
		available = false;
		
		//so if successfully used rod, return true
		return true;
	}
	
	//Releases hold on rod
	public synchronized void free(){
		available = true;
	}

}
