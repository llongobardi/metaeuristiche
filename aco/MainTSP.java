package aco;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class MainTSP {

	
	public static void main (String[] s){
		int spaceSize = 4;
		double[][] weights = new double[][]{
			  { 0, 1, 3, 1 },
			  { 1, 0, 1, 3 },
			  { 1, 3, 0, 1 },
			  { 1, 3, 1, 0 }
		};
		ArcEstimator est = new Estimator(weights,spaceSize);
		ActorRef colony;// = new AntColony(spaceSize,100,weights,est);
		ActorSystem system = ActorSystem.create("tsp");
		colony = system.actorOf(Props.create(AntColony.class,spaceSize,100,weights,est));
		colony.tell(new Message(Message.MsgType.START),ActorRef.noSender());
	}
}
