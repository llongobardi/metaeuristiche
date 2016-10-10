package genetic;

import java.util.HashMap;

public class Bin {
	
	private HashMap<Integer, Integer> objectsInBin;
	private int capacityLeft;
	private final int BIN_CAPACITY = InitializeBPP.model.getBinCapacity();
	
	public Bin(){
		objectsInBin = new HashMap<>();
		this.capacityLeft = BIN_CAPACITY;
	}
	
	public int getLeftCapacity(){
		return this.capacityLeft;
	}
	
	public HashMap<Integer, Integer> getObjects(){
		return this.objectsInBin;
	}
	public void addObject(int num, int volume){
		objectsInBin.put(num,volume);
		this.capacityLeft -= volume;
	}
	
	public Bin copyOf(){
		Bin b = new Bin();
		for (int i : objectsInBin.keySet()){
			b.addObject(i,objectsInBin.get(i));
		}
		return b;
	}
	
	

}
