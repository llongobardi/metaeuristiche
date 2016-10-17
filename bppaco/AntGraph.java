package bppaco;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class AntGraph extends UntypedActor{
	
	private ActorRef colony;
	
	private ArcEstimator estimator;
	private double pheromone[][];
	private int etha[];
	private int antEnded;
	private int spaceSize;
	private int numAnts;
	
	public AntGraph(ArcEstimator estimator,int spaceSize, ActorRef colony, int numAnts){
		this.estimator = estimator;
		this.colony = colony;
		this.etha = estimator.getEtha();
		antEnded = 0;
		this.numAnts = numAnts;
		this.spaceSize = spaceSize;
		this.pheromone = new double[spaceSize][spaceSize];
		for (int i=0; i<spaceSize; i++){
			for (int j =0; j<spaceSize; j++){
				this.pheromone[i][j] = 0;
			}
		}
	}

	@Override
	public void onReceive(Object m) throws Throwable {
		if(m instanceof Message){
			if (((Message) m).getType().equals(Message.MsgType.STATETRANS)){
				AntSolution sol = ((Message) m).getLocalSolution();
				int state = this.stateTransitionRule(sol);
				Message msg = new Message(Message.MsgType.STATETRANS);
				msg.setState(state);
				getSender().tell(msg, getSelf());
			} else if (((Message) m).getType().equals(Message.MsgType.END)){
				antEnded++;
				estimator.localUpdateRule(((Message) m).getLocalSolution());//modifico i contributi
				if (antEnded == this.numAnts){ //spaceSize � anche il numero di formiche
					antEnded=0;
					this.globalUpdateRule();//update del feromone
					colony.tell(new Message(Message.MsgType.END), getSelf());
				}
			}
		}
	}
	
	//Equazione (8)
	private void globalUpdateRule(){//aggiornamento della traccia
		double temp[][] = new double[spaceSize][spaceSize];

		int couples[][] = estimator.getCouples();
		
		for (int i =0; i<spaceSize; i++){
			for (int j=0; j<spaceSize; j++){
				temp[i][j] = estimator.getEvapRate()*pheromone[i][j] +(couples[i][j] == 1 ? 1:0)*estimator.getBestSolutionCost();
			}
		}
		pheromone = temp;	
	}
	
	public double[][] getPheromones(){
		return this.pheromone;
		
	}
	
	//equazione (6) per BPP
	public int stateTransitionRule(AntSolution localSolution) {
		
		//lista di oggetti da poter inserire - before size checking
		List<Integer> visitableNodes = new LinkedList<>(InitializeBPP.model.getItemSet());
		//lightEnough is the set of items that qualifies for inclusion
		List<Integer> lightEnough = new LinkedList<>();
		
		
		for(Bin b : localSolution.getBinList())
			visitableNodes.removeAll(b.getObjects().keySet());
		
		//per gli oggetti rimasti, devi togliere quelli che non ci stanno
		Bin lastBin = localSolution.getLastBin();
		int leftCapacity = lastBin.getLeftCapacity();
		
		for (int i: visitableNodes){
			if (InitializeBPP.model.getObjects().get(i) <= leftCapacity){
				lightEnough.add(i); //aggiungo la chiave dell'oggetto abbastanza leggero
				//leftCapacity -= InitializeBPP.model.getObjects().get(i);//diminuisco la capacit� del bin
			}
		}
		
		if(!lightEnough.isEmpty()){
			List<Double> cumulateCostOfNodes = new LinkedList<>();//probabilit� di includere un oggetto
			double sumDivide = 0;
			double cumulate = 0;
			
			
			//calculating sumDivide
			for (int i: lightEnough){
				sumDivide += lastBin.getSinglePheromones()[i] * Math.pow(this.etha[i], estimator.getBeta());
			}
			
			for (int i : lightEnough){
				cumulateCostOfNodes.add(lastBin.getSinglePheromones()[i]*Math.pow(this.etha[i], estimator.getBeta())/sumDivide +cumulate);
				cumulate+=lastBin.getSinglePheromones()[i]*Math.pow(this.etha[i], estimator.getBeta())/sumDivide;
			}
			
			
			Random r = new Random();
			double choice = r.nextDouble();
			//montecarlo 
			for(int i = 0; i < cumulateCostOfNodes.size(); i++){
				if(choice <= cumulateCostOfNodes.get(i)/cumulate){
					return lightEnough.get(i);//ritorno il prossimo oggetto da aggiungere
				}
			}
			
		} else {
			List<Double> cumulateCostOfNodes = new LinkedList<>();//probabilit� di includere un oggetto
			double sumDivide = 0;
			double cumulate = 0;
			
			
			//calculating sumDivide
			for (int i: visitableNodes){
				sumDivide += lastBin.getSinglePheromones()[i] * Math.pow(this.etha[i], estimator.getBeta());
			}
			
			for (int i : visitableNodes){
				cumulateCostOfNodes.add(lastBin.getSinglePheromones()[i]*Math.pow(this.etha[i], estimator.getBeta())/sumDivide +cumulate);
				cumulate+=lastBin.getSinglePheromones()[i]*Math.pow(this.etha[i], estimator.getBeta())/sumDivide;
			}
			
			
			Random r = new Random();
			double choice = r.nextDouble();
			//montecarlo 
			for(int i = 0; i < cumulateCostOfNodes.size(); i++){
				if(choice <= cumulateCostOfNodes.get(i)/cumulate){
					return visitableNodes.get(i);//ritorno il prossimo oggetto da aggiungere
				}
			}

			
		}
		
		return -1;
	}
}
