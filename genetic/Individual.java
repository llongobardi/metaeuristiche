package genetic;

import java.util.Random;

public class Individual {
	
	private int geneSize = 0;
	private int[] genes;
	private int fitness = 0;
	private FitnessEstimator estimator;
	
	
	public Individual(int geneSize, FitnessEstimator estimator){
		this.estimator = estimator;
		this.geneSize = geneSize;
		genes = new int[geneSize];
	}
	
	public void generateIndividual(){
		
		Random rand = new Random();
		
		for(int i = 0; i < geneSize; i++){
			genes[i] = rand.nextDouble() > 0.5 ? 1 : 0;
		}
		
	}
	
	public Individual copyOf(){
		Individual copy = new Individual(geneSize,estimator);
		
		for(int i = 0; i < geneSize; i++){
			copy.setGene(i, genes[i]);
		}
		
		return copy;
	}
	
	public int getGeneSize(){
		return genes.length;
	}
	
	public int getGene(int position){
		return genes[position];
	}
	
	public void setGene(int position, int gene){
		genes[position] = gene;
	}
	
	public int getFitness(){
		if(fitness == 0){
			fitness = estimator.estimateFitness(this);
		}
		
		return fitness;
	}
	
	@Override
	public String toString(){
		
		String res = "";
		
		for(int i = 0; i < this.geneSize;i++){
			res += "["+ genes[i]+"]";
		}
		
		return res;
		
	}

}
