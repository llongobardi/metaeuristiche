package aco;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction of ant solution
 * Since bins are filled sequentially in ant system,
 * we can encapsulate the solution complexity
 *
 */
public class AntSolution {
	
	private List<Bin> solution;
	
	public AntSolution(){
		solution = new LinkedList<>();
	}
	
	/**
	 * adding a bin is transparent to the user
	 * in ant system you have not to worry about where the item is added,
	 * since the best solution is just a "path" of items in bins
	 * @param id
	 * @param value
	 */
	public void addItem(int id, int value){
		if(!solution.isEmpty()){
			
			if(solution.get(solution.size()-1).getLeftCapacity() < value){
				Bin b = new Bin();
				b.addObject(id, value);
			} else {
				solution.get(solution.size()-1).addObject(id, value);
			}
			
		} else {
			Bin b = new Bin();
			b.addObject(id, value);
			solution.add(b);
		}
		
	}
	
	/**
	 * Clear the solution by emptying the data set
	 */
	public void clear(){
		this.solution.clear();
	}
	
	public Bin getLastBin(){
		return this.solution.get(solution.size()-1);
	}
	
	/**
	 * 
	 * @return the size of the solution, calculated as the total items in
	 * all the bins
	 */
	public int numObjects(){
		int size = 0;
		for(Bin b : solution)
			size += b.getObjects().size();
		return size;
	}
	
	public int numBins(){
		return solution.size();
	}
	
	public int getMaxContent(){
		int max = 0;
		int temp = 0;
		for (Bin b : solution){
			temp = 0;
			temp = b.getObjects().size();
			if (temp>max){
				max = temp;
			}
		}
		return max;		
	}
	
	public int getObjInBin(Bin b){
		return b.getObjects().size();
	}
	
	/**
	 * 
	 * @return a copy of bin list (it is safe to work with)
	 */
	public List<Bin> getBinList(){
		LinkedList<Bin> l = new LinkedList<>();
		for(Bin b : solution)
			l.add(b.copyOf());
		return l;
	}

}
