package aco;

import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class AntGraph extends UntypedActor{
	
	private ActorRef colony;
	
	private ArcEstimator estimator;
	private double pheromone[][];
	private double etha[][];
	private int antEnded;
	private int spaceSize;
	
	
	public AntGraph(ArcEstimator estimator,int spaceSize, ActorRef colony){
		this.estimator = estimator;
		this.colony = colony;
		pheromone = new double[spaceSize][spaceSize];
		this.etha = estimator.getEtha();
		antEnded = 0;
		this.spaceSize = spaceSize;
	}

	@Override
	public void onReceive(Object m) throws Throwable {
		if(m instanceof Message){
			if (((Message) m).getType().equals(Message.MsgType.STATETRANS)){
				List<Integer> lst = ((Message) m).getLocalSolution();
				int state = this.stateTransitionRule(((Message) m).getState(), lst);
				Message msg = new Message(Message.MsgType.STATETRANS);
				msg.setState(state);
				getSender().tell(msg, getSelf());
			} else if (((Message) m).getType().equals(Message.MsgType.END)){
				antEnded++;
				if (antEnded == spaceSize){ //spaceSize è anche il numero di formiche
					antEnded=0;
					this.globalUpdateRule();//update del feromone
					colony.tell(new Message(Message.MsgType.END), getSelf());
				}
			}
		}
	}
	
	private void globalUpdateRule(){
		
	}
	
	public int stateTransitionRule(int index, List<Integer> localSolution) {
		//Implementare la formula di calcolo probabilità più metodo montecarlo 
		return -1;
	}
}
