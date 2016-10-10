package genetic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BPPopulation{

	private BPPEstimator estimator = new BPPEstimator();
	private HashMap<Integer, Integer> objects;
	private List<BPPIndividual> population;
	private final int POP_SIZE = 40;
	
	
	public BPPopulation(boolean initialize){
		initialize(initialize);
	}
	
	/**
	 * Initialize first random solution.
	 * */
	private void initialize(boolean initialize){
		objects = InitializeBPP.model.getObjects(); //quelli letti dal file
		population = new LinkedList<>();
		if (initialize){
			for (int i=0; i<POP_SIZE; i++){ //costruisco gli individui della mia popolazione
				BPPIndividual newIndividual = new BPPIndividual(estimator);
				newIndividual.setObjects(objects);
				newIndividual.generateIndividual();
				population.add(newIndividual);
			}
		}
	}
	
	public BPPIndividual getIndividual(int position){		
		return population.get(position);
	}
	
	public BPPIndividual getFittest(){		
		BPPIndividual fittest = population.get(0);		
		for(int i = 0; i < population.size(); i++){			
			if(fittest.getFitness() <= getIndividual(i).getFitness()){
				fittest = getIndividual(i);
			}
		}		
		return fittest;		
	}
	
	
	public int getPopSize(){
		return population.size();
	}
	
	public void insertIndividual(int position, BPPIndividual newIndividual){
		population.add(position, newIndividual);;
	}
	
	@Override
	public String toString(){
		
		String result = "";
		
		for(BPPIndividual i : population){
			result += i.toString()+"\n";
		}
		
		return result+"\n";
		
	}
	

}
