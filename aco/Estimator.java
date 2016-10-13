package aco;

import java.util.Arrays;

public class Estimator implements ArcEstimator {
	
	private final double alpha = 1.0;
	private final double beta = 2.0;
	private final double evaporation_rate = 0.5;
	private final int K = 2;
	private int[] etha; //nel caso di BPP, etha e' il peso dell'oggetto
	private double contributes[][];
	private double bestCost;
	private int spaceSize;
	private AntSolution bestSolution;
	
	public Estimator(int spaceSize){
		this.contributes = new double[spaceSize][spaceSize];
		this.etha = new int[spaceSize];
		this.bestCost = Double.MAX_VALUE;
		this.spaceSize = spaceSize;
		this.bestSolution = new AntSolution();
		for(Integer i : InitializeBPP.model.getItemSet())
			etha[i] = InitializeBPP.model.getObjects().get(i);
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
	
	public int[] getEtha() {
		return etha;
	}
	
	public double getContribute(int index1, int index2){ //get tau matrix
		return this.contributes[index1][index2];
	}
		
	@Override
	public double getBestSolutionCost(){
		return this.bestCost;
	}
	
	@Override
	public AntSolution getBestSolution(){
		return this.bestSolution;
	}
	
	public int[][] getCouples(){
		
		int couples[][] = new int[spaceSize][spaceSize];
		Arrays.fill(couples, 0);
		for (Bin b: bestSolution.getBinList()){
			for (Integer i: b.getObjects().keySet()){
				for (Integer j: b.getObjects().keySet()){
					if (!(i.equals(j))){
						couples[i][j] = 1;
						couples[j][i] = 1;
					}
				}
			}
		}		
		return couples;
	}

	/**
	 * Called everytime an ant has finished.
	 * */
	@Override
	public void localUpdateRule(AntSolution solution) {
		
		int cost = 0;
		
		int toDivide = 0;
		int maxContent = solution.getMaxContent();
		for (Bin b : solution.getBinList()){
			toDivide+= Math.pow(solution.getObjInBin(b)/maxContent, K);
		}
		
		cost = toDivide/solution.getBinList().size();
		
		if (cost < bestCost){
			bestCost = cost;
			this.bestSolution = solution;
		}
		
		double sum = 0;
		//update pheromone for single object (equazione 7)
		for (Bin b: solution.getBinList()){
			for (int obj : b.getObjects().keySet()){
				for (int obj1 : b.getObjects().keySet()){
					sum += contributes[obj][obj1];
				}
				(b.getSinglePheromones())[obj] = sum/b.getObjects().size();
			}
		}
		
	}

}
