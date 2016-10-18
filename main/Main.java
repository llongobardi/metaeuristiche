package main;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import bppaco.*;

public class Main {
	
	public static void main(String args[]){
		
	
		ArcEstimator est = new Estimator(120);
		ActorRef colony;
		ActorRef graph;
		List<ActorRef>  ants = new ArrayList<>(40);
		ActorSystem system = ActorSystem.create("BPP");
		colony = system.actorOf(Props.create(AntColony.class,120,100,est,ants));
		graph = system.actorOf(Props.create(AntGraph.class, est ,120,colony,40));
		for(int i = 0; i < 40; i++)
			ants.add(system.actorOf(Props.create(Ant.class,120,graph)));
		colony.tell(new Message(Message.MsgType.START),ActorRef.noSender());
		
		
	}
}

