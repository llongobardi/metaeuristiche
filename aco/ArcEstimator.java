package aco;

public interface ArcEstimator{
	
	public double getAlpha();
	
	public double getBeta();
	
	public double getEvapRate();
	
	public int[] getEtha();
	
	public int[][] getCouples();
	
	public void localUpdateRule(AntSolution solution);
	
	public double getContribute(int index1, int index2);
	
	public double getBestSolutionCost();
	
	public AntSolution getBestSolution();

}
