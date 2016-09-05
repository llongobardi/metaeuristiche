package aco;

import java.util.List;

public class Estimator implements ArcEstimator {
	
	private static final double alpha = 1.0;
	private static final double beta = 2.0;
	private static final double evaporation_rate = 0.5;
	private double[][] etha;
	private double contributes[][];
	private double arcWeight[][];
	
	public Estimator(double[][] arcWeight, int spaceSize){
		this.arcWeight = arcWeight;
		this.contributes = new double[spaceSize][spaceSize];
		this.etha = new double[spaceSize][spaceSize];
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

	/**
	 * Uses contributes matrix
	 * */
	@Override
	public void localUpdateRule(List<Integer> solution) {
		//To calculate the contributes
		
		
	}

}
