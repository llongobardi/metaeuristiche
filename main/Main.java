package main;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import bppaco.*;

public class Main {
	
	public static void main(String args[]){
		
	
		ArcEstimator est = new Estimator(120);
		ActorRef colony;
		ActorSystem system = ActorSystem.create("BPP");
		colony = system.actorOf(Props.create(AntColony.class,120,100,est));
		colony.tell(new Message(Message.MsgType.START),ActorRef.noSender());
	
		//ind.setObjects(InitializeBPP.model.getObjects());
		//ind.generateIndividual();
		
		//pop.insertIndividual(0, ind);
		
		
		
	}
}

