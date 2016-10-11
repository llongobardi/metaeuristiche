package aco;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Ant extends UntypedActor{
	
	private ActorRef graph;
	
	private AntSolution localSolution;
	private int spaceSize, startNode;
	
	public Ant(int startNode, int spaceSize,ActorRef graph){
		
		this.graph = graph;
		localSolution = new AntSolution();
		this.spaceSize = spaceSize;
		this.startNode = startNode;
		localSolution.addItem(startNode, InitializeBPP.model.getObjects().get(startNode));
	}

	@Override
	public void onReceive(Object m) throws Throwable {
		if(m instanceof Message){
			if(((Message)m).getType().equals(Message.MsgType.GO)){
				localSolution.clear();
				localSolution.addItem(startNode,InitializeBPP.model.getObjects().get(startNode));
				Message msg = new Message(Message.MsgType.STATETRANS);
				msg.setupStateRequest(localSolution);
				graph.tell(msg,this.getSelf());
			} else if (((Message)m).getType().equals(Message.MsgType.STATETRANS)){
				localSolution.addItem(((Message) m).getState(),
						InitializeBPP.model.getObjects().get(((Message) m).getState()));
				if(localSolution.size() == this.spaceSize){
					Message msg = new Message(Message.MsgType.END);
					msg.setupSolution(localSolution);
					graph.tell(msg, getSelf()); //gli invio la mia soluzione
				} else {
					Message msg = new Message(Message.MsgType.STATETRANS);
					msg.setupStateRequest(localSolution);
					graph.tell(msg,this.getSelf());
				}
			}
			
		}		
	}

}
