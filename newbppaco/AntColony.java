package newbppaco;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class AntColony{

	private final int ANTS = 6;
	private AntGraph graph;
	private int numIter,maxIter;
	List<Ant> ants;
	ArcEstimator estimator;
	
	public AntColony(int spaceSize, int iterations, ArcEstimator estimator){
		
		this.estimator =estimator;
		
		this.maxIter = iterations;
		numIter = 0;
		//ActorSystem system = ActorSystem.create();
		
		ants = new ArrayList<>(ANTS);
		
		graph = new AntGraph(this.estimator, spaceSize,this,ANTS);
		//this.estimator = new Estimator(120);
		for(int i = 0; i < ANTS; i++)
			ants.add(new Ant(spaceSize, this.graph));
		
		/*Iterator<ActorRef> iterator = ants.iterator();
		
		while(iterator.hasNext()){
			iterator.next().tell(new Message(Message.MsgType.GO), this.getSelf());
		}*/
	}
	
	public int getNumAnts(){
		return this.ANTS;
	}
	
	public void startAnts(){
		
		
		Iterator<Ant> iterator = ants.iterator();
		
		while(iterator.hasNext()){
			iterator.next().start();
		}
	}
	
	
	
	
	/*@Override
	public void onReceive(Object m) throws Throwable {
		if(m instanceof Message){			
			if (((Message) m).getType().equals(Message.MsgType.END)){
				numIter++;
				if (numIter<maxIter){
					System.out.print("Costo soluzione migliore (in numero di bin): " + estimator.getBestSolution().numBins()+"\n");
					startAnts();
				} else {
					System.out.print("Costo soluzione migliore (in numero di bin): " + estimator.getBestSolBins()+"\n");
				}
			} else if (((Message) m).getType().equals(Message.MsgType.START)){
				startAnts();
			}
		}
		
		
	}*/
	
	public int numIter(){
		return this.numIter;
	}

}
