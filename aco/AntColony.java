package aco;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class AntColony extends UntypedActor {

	private final int ANTS = 40;
	private ActorRef graph;
	private int numIter,maxIter;
	List<ActorRef> ants;
	ArcEstimator estimator;
	
	public AntColony(int spaceSize, int iterations, ArcEstimator estimator){
		
		this.estimator = estimator;
		this.maxIter = iterations;
		numIter = 0;
		ActorSystem system = ActorSystem.create();
		
		ants = new ArrayList<>(ANTS);
		
		graph = system.actorOf(Props.create(AntGraph.class, estimator ,spaceSize,this.getSelf()));
		
		for(int i = 0; i < ANTS; i++)
			ants.add(system.actorOf(Props.create(Ant.class,0,spaceSize,graph)));
		
		Iterator<ActorRef> iterator = ants.iterator();
		
		while(iterator.hasNext()){
			iterator.next().tell(new Message(Message.MsgType.GO), this.getSelf());
		}
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
					startAnts();
				} else {
					System.out.print("Sol migliore: "+estimator.getBestSolution().toString() + "Costo sol: " + estimator.getBestSolutionCost());
				}
			} else if (((Message) m).getType().equals(Message.MsgType.START)){
				startAnts();
			}
		}
		
		
	}

}
