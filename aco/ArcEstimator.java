package aco;

import java.util.List;

public interface ArcEstimator{
	
	public double getAlpha();
	
	public double getBeta();
	
	public double getEvapRate();
	
	public int[] getEtha();
	
	public void localUpdateRule(AntSolution solution);
	
	public double getContribute(int index1, int index2);
	
	public double getBestSolutionCost();
	
	public List<Bin> getBestSolution();
	//public List<Integer> getBestSolution();

}
