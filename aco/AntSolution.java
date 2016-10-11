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
	
	/**
	 * 
	 * @return the size of the solution, calculated as the total items in
	 * all the bins
	 */
	public int size(){
		int size = 0;
		for(Bin b : solution)
			size += b.getObjects().size();
		return size;
	}

}
