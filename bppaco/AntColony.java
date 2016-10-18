package bppaco;

import java.util.Iterator;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class AntColony extends UntypedActor {

	private final int ANTS = 6;
	private int numIter,maxIter;
	List<ActorRef> ants;
	ArcEstimator estimator;
	
	public AntColony(int spaceSize, int iterations, ArcEstimator estimator,List<ActorRef> ants){
		
		this.estimator =estimator;
		this.maxIter = iterations;
		numIter = 0;
		this.ants = ants;
	}
	
	public int getNumAnts(){
		return this.ANTS;
	}
	
	public void startAnts(){
		
		
		Iterator<ActorRef> iterator = ants.iterator();
		
		while(iterator.hasNext()){
			iterator.next().tell(new Message(Message.MsgType.GO), this.getSelf());
		}
	}
	@Override
	public void onReceive(Object m) throws Throwable {
		if(m instanceof Message){			
			if (((Message) m).getType().equals(Message.MsgType.END)){
				numIter++;
				if (numIter<maxIter){
					System.out.print("Costo soluzione migliore (in numero di bin): " + estimator.getBestSolBins()+"\n");
					startAnts();
				} else {
					System.out.print("Costo soluzione migliore (in numero di bin): " + estimator.getBestSolBins()+"\n");
				}
			} else if (((Message) m).getType().equals(Message.MsgType.START)){
				startAnts();
			}
		}
		
		
	}
	
	public int numIter(){
		return this.numIter;
	}

}
