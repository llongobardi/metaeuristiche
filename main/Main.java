package main;

import genetic.*;

public class Main {
	
	public static void main(String args[]){
		
		Population pop = new Population(30,true,10);
		FitnessEstimator estimator = new Estimator();
		Algorithm algo = new Algorithm(new Individual(10,estimator));
		
		for(int i = 0; i< 1000 ; i++){
			pop = algo.evolvePopulation(pop);
			
			//System.out.println("Generation "+ i +": \n" +  pop.toString());
		}
		
		System.out.println("Fittest: " +  algo.getFittest());
		
	}

}
