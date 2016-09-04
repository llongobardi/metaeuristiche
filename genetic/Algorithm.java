package genetic;

import java.util.Random;

public class Algorithm {
	
	private Individual fittest;
	
	public Algorithm(Individual fittest){
		this.fittest = fittest;
	}
	
	public Population evolvePopulation(Population population){
		Population source = this.montecarloSelection(population);
		this.crossover(source);
		this.mutate(source);
		
		
		fittest = source.getFittest().getFitness() > fittest.getFitness() ? source.getFittest() : fittest;
		
		return source;
	}
	
	public Individual getFittest(){
		return fittest;
	}
	
	private void mutate(Population pop){
		
		for(int j=0;j<pop.getPopSize();j++){
			
			for(int i = 0; i < pop.getIndividual(j).getGeneSize(); i++){
				if(Math.random() <= 0.015){
					pop.getIndividual(j).setGene(i, 1 - pop.getIndividual(j).getGene(i));
				}
			}
		}
		
		
		
	}
	
	private void crossover(Population pop){
		Random randCutpoint = new Random();
		int cutpoint;
		int tempExchange;
		
		for(int i=0; i < pop.getPopSize();i+=2){
			cutpoint = (int)Math.floor(randCutpoint.nextDouble()*(pop.getPopSize()-2))+1; 
			
			for(int j=cutpoint; j<pop.getGeneLength();j++){
				tempExchange = pop.getIndividual(i).getGene(j);
				
				pop.getIndividual(i).setGene(j, pop.getIndividual(i+1).getGene(j));
				pop.getIndividual(i+1).setGene(j, tempExchange);
					
			}
			
		}
		
	}
	
	
	private Population montecarloSelection(Population source){
		
		Population selection = new Population(source.getPopSize(), false, source.getGeneLength());
		
		double[] cumulativeOdd = new double[source.getPopSize()];
		double temp = 0;
		Random randSelector = new Random();
		
		for(int i = 0; i < source.getPopSize(); i++){
			cumulativeOdd[i] = source.getIndividual(i).getFitness() + temp;
			temp += cumulativeOdd[i];
		}
		
		for(int i = 0; i< selection.getPopSize(); i++){
			double newRandom = randSelector.nextDouble();
			
			for(int j = 0; j< source.getPopSize(); j++){
				if(newRandom <= cumulativeOdd[j]/cumulativeOdd[source.getPopSize()-1]){
					
					selection.insertIndividual(i, source.getIndividual(j).copyOf());
					break;
					
				}
			}
		}
		
		
		return selection;
		
	}

}
