package aco;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction of ant solution
 * Since bins are filled sequentially in ant system,
 * we can encapsulate the solution complexity
 * @author luca
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
			
			if(solution.get(solution.size()).getLeftCapacity() < value){
				Bin b = new Bin();
				b.addObject(id, value);
			} else {
				solution.get(solution.size()).addObject(id, value);
			}
			
		} else {
			Bin b = new Bin();
			b.addObject(id, value);
			solution.add(b);
		}
		
	}

}
