package aco;

import java.util.List;

public class Estimator implements ArcEstimator {
	
	private static final double alpha = 1.0;
	private static final double beta = 2.0;
	private static final double evaporation_rate = 0.5;
	private double[][] etha;
	private double contributes[][];
	private double arcWeight[][];
	private int spaceSize;
	
	public Estimator(double[][] arcWeight, int spaceSize){
		this.arcWeight = arcWeight;
		this.contributes = new double[spaceSize][spaceSize];
		this.etha = new double[spaceSize][spaceSize];
		this.spaceSize = spaceSize;
	}

	@Override
	public double getAlpha() {
		return alpha;
	}

	@Override
	public double getBeta() {
		return beta;
	}

	@Override
	public double getEvapRate() {
		return evaporation_rate;
	}
	
	public double getArcWeight(int index1, int index2){
		return this.arcWeight[index1][index2];
	}
	
	@Override
	public double[] getLineWeight(int index){
		return this.arcWeight[index];
	}

	/*@Override
	public double getEtha(int x_coord, int y_coord) {
		return etha[x_coord][y_coord];
	}*/
	
	public double[][] getEtha() {
		return etha;
	}
	
	public double getContribute(int index1, int index2){
		return this.contributes[index1][index2];
	}

	/**
	 * Modifies contributes according to the solution of each single ant.
	 * */
	@Override
	public void localUpdateRule(List<Integer> solution) {
		//To calculate the contributes
		double cost = 0.0;
		for(int i=0; i<solution.size()-2; i++){
			cost+= arcWeight[solution.get(i)][solution.get(i+1)]; //ottengo il costo della soluzione trovata
		}
		//Trovato il costo, lo aggiungo alla matrice dei contributi
		for(int i=0; i<solution.size()-2; i++){
			contributes[solution.get(i)][solution.get(i+1)]+=1/cost; //regola per TSP
			}
		
	}

}
