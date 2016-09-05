package aco;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class AntColony extends UntypedActor {

	
	
	public AntColony(int spaceSize, int iterations){
		
		ActorSystem system = ActorSystem.create();
		List<ActorRef> ants = new ArrayList<>(spaceSize);
		
		for(int i = 0; i < spaceSize; i++)
			ants.add(system.actorOf(Props.create(Ant.class,0,spaceSize)));
		
		for(int i = 0; i < iterations; i++)
			iteration();
	}
	
	@Override
	public void onReceive(Object arg0) throws Throwable {
		// TODO Auto-generated method stub
		
	}
	
	
	private void iteration(){
		//sincronizzazione
	}

}
