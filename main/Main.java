package main;

import aco.*;
import aco.Estimator;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
	
	public static void main(String args[]){
		
	
		ArcEstimator est = new Estimator(120);
		ActorRef colony;
		ActorSystem system = ActorSystem.create("BPP");
		colony = system.actorOf(Props.create(AntColony.class,120,100,est));
		colony.tell(new Message(Message.MsgType.START),ActorRef.noSender());
		
	}
}

