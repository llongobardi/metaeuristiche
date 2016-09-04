package genetic;

public class Population {

	private FitnessEstimator estimator = new Estimator();
	Individual[] individuals;
	private static final int defaultGeneLength = 10;
	private int customGeneLength = 0;
	
	public Population(int populationSize,boolean initialize){
		
		initialize(populationSize,initialize,defaultGeneLength);
		
	}
	
	public Population(int populationSize, boolean initialize, int geneLength){
		customGeneLength = geneLength;
		initialize(populationSize,initialize,customGeneLength);
	}
	
	public Population(int populationSize, Individual[] customIndividuals){
		if(customIndividuals.length != populationSize){
			throw new UnsupportedOperationException();
		}
		
		individuals = customIndividuals;
	}
	
	private void initialize(int populationSize, boolean initialize, int geneLength){
		
		individuals = new Individual[populationSize];
		
		if(initialize){
			for(int i = 0; i < individuals.length; i++){
				Individual newIndividual = new Individual(geneLength,estimator);
				newIndividual.generateIndividual();
				insertIndividual(i,newIndividual);
			}
		}
		
		
	}
	
	public Individual getIndividual(int position){
		
		return individuals[position];
	}
	
	public Individual getFittest(){
		
		Individual fittest = individuals[0];
		
		for(int i = 0; i < individuals.length; i++){
			
			if(fittest.getFitness() <= getIndividual(i).getFitness()){
				fittest = getIndividual(i);
			}
		}
		
		return fittest;
		
	}
	
	public int getGeneLength(){
		if(this.customGeneLength != 0){
			return customGeneLength;
		}
		
		return defaultGeneLength;
	}
	
	public int getPopSize(){
		return individuals.length;
	}
	
	public void insertIndividual(int position, Individual newIndividual){
		individuals[position] = newIndividual;
	}
	
	@Override
	public String toString(){
		
		String result = "";
		
		for(Individual i : individuals){
			result += i.toString()+"\n";
		}
		
		return result+"\n";
		
	}
}
