package aco;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
				estimator.localUpdateRule(((Message) m).getLocalSolution());//modifico i contributi
				if (antEnded == spaceSize){ //spaceSize è anche il numero di formiche
					antEnded=0;
					this.globalUpdateRule();//update del feromone
					colony.tell(new Message(Message.MsgType.END), getSelf());
				}
			}
		}
	}
	
	private void globalUpdateRule(){//aggiornamento della traccia
		double temp[][] = new double[spaceSize][spaceSize];
		for(int i=0; i<spaceSize; i++){
			for(int j=0; j<spaceSize; j++){
				temp[i][j] = estimator.getEvapRate()*pheromone[i][j] + estimator.getContribute(i, j);
			}
		}
		pheromone = temp;	
	}
	
	public int stateTransitionRule(int index, List<Integer> localSolution) {
		
		List<Integer> visitableNodes = new LinkedList<>();
		double[] nodes = estimator.getLineWeight(index); //prendo i pesi per quello specifico indice 
		for(int i = 0; i < nodes.length; i++){
			if(nodes[i] != 0)
				visitableNodes.add(i);
		}
		
		//remove visited nodes
		visitableNodes.removeAll(localSolution);//tra i possibili nodi tolgo quelli già visitati
		
		List<Double> cumulateCostOfNodes = new LinkedList<>();
		Double temp = 0.0;
		
		for(Integer i : visitableNodes){
			cumulateCostOfNodes.add(Math.pow(nodes[i], estimator.getAlpha())*
					Math.pow(etha[index][i], estimator.getBeta())+temp);
			temp += Math.pow(nodes[i], estimator.getAlpha()) * Math.pow(etha[index][i], estimator.getBeta());
		}
		
		Random r = new Random();
		double choice = r.nextDouble();
		//montecarlo 
		for(int i = 0; i < cumulateCostOfNodes.size(); i++){
			if(choice <= cumulateCostOfNodes.get(i)/temp){
				return visitableNodes.get(i);//ritorno il prossimo stato
			}
		}
		
		
		return -1;
	}
}
