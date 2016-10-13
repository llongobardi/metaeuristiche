package main;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import genetic.*;
import aco.*;
import aco.Estimator;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Main {
	
	public static void main(String args[]){
		
	
		ArcEstimator est = new Estimator(120);
		ActorRef colony;// = new AntColony(spaceSize,100,weights,est);
		ActorSystem system = ActorSystem.create("BPP");
		colony = system.actorOf(Props.create(AntColony.class,120,10,est));
		colony.tell(new Message(Message.MsgType.START),ActorRef.noSender());
		
	}
}

