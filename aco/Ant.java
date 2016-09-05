package aco;

import java.util.LinkedList;
import java.util.List;

import akka.actor.UntypedActor;

public class Ant extends UntypedActor{
	
	private List<Integer> localSolution;
	private int spaceSize;
	
	public Ant(int startNode, int spaceSize){
		
		localSolution = new LinkedList<>();
		this.spaceSize = spaceSize;
		localSolution.add(startNode);
	}

	@Override
	public void onReceive(Object arg0) throws Throwable {
		if(arg0 instanceof Message){
			
			
		}
		
	}

}
