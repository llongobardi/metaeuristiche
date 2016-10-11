package aco;

import java.util.HashMap;

public class Bin {
	
	private HashMap<Integer, Integer> objectsInBin;
	private int capacityLeft;
	private final int BIN_CAPACITY = 150;
	private double[] singlePheromones;
	
	public Bin(){
		objectsInBin = new HashMap<>();
		this.capacityLeft = BIN_CAPACITY;
		singlePheromones = new double[InitializeBPP.model.getObjects().size()];
		for (int i = 0; i<singlePheromones.length; i++){
			singlePheromones[i] = 1/InitializeBPP.model.getObjects().size();
		}
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
	
	public double[] getSinglePheromones(){
		return this.singlePheromones;
	}
	
	public Bin copyOf(){
		Bin b = new Bin();
		for (int i : objectsInBin.keySet()){
			b.addObject(i,objectsInBin.get(i));
		}
		return b;
	}
	
	

}
