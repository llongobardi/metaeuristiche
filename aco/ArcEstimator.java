package aco;

import java.util.List;

public interface ArcEstimator{
	
	public double getAlpha();
	
	public double getBeta();
	
	public double getEvapRate();
	
	//public double getEtha(int x_coord, int y_coord );
	
	public double[][] getEtha();
	
	public void localUpdateRule(List<Integer> solution);

}
