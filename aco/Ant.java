package aco;

import java.util.LinkedList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Ant extends UntypedActor{
	
	private ActorRef graph;
	
	private List<Integer> localSolution;
	private int spaceSize;
	
	public Ant(int startNode, int spaceSize,ActorRef graph){
		
		this.graph = graph;
		localSolution = new LinkedList<>();
		this.spaceSize = spaceSize;
		localSolution.add(startNode);
	}

	@Override
	public void onReceive(Object m) throws Throwable {
		if(m instanceof Message){
			if(((Message)m).getType().equals(Message.MsgType.GO)){
				Message msg = new Message(Message.MsgType.STATETRANS);
				msg.setupStateRequest(localSolution.get(localSolution.size()-1), localSolution);
				graph.tell(msg,this.getSelf());
			} else if (((Message)m).getType().equals(Message.MsgType.STATETRANS)){
				localSolution.add(((Message) m).getState());
				if(localSolution.size() == this.spaceSize){
					Message msg = new Message(Message.MsgType.END);
					msg.setupSolution(localSolution);
					graph.tell(msg, getSelf()); //gli invio la mia soluzione
					//graph.tell(new Message(Message.MsgType.END), getSelf());
				} else {
					Message msg = new Message(Message.MsgType.STATETRANS);
					msg.setupStateRequest(localSolution.get(localSolution.size()-1), localSolution);
					graph.tell(msg,this.getSelf());
				}
			}
			
		}
		
	}

}
