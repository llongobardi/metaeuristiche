package aco;

import akka.actor.UntypedActor;

public class AntGraph extends UntypedActor{
	
	private ArcEstimator estimator;
	private double arcWeight[][];
	private double pherormone[][];
	
	public AntGraph(ArcEstimator estimator){
		this.estimator = estimator;
	}

	@Override
	public void onReceive(Object arg0) throws Throwable {
		// TODO Auto-generated method stub
		
	}
	
	private void localUpdateRule(int x_coord,int y_coord){
		
	}
	
	private void globalUpdateRule(){
		
	}
}
