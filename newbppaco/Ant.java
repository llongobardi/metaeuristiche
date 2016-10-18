package newbppaco;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Ant extends Thread{
	
	private AntGraph graph;
	
	private AntSolution localSolution;
	private int spaceSize, startNode;
	private boolean stop = false;
	
	public Ant( int spaceSize,AntGraph graph){
		
		this.graph = graph;
		localSolution = new AntSolution();
		this.spaceSize = spaceSize;
		this.startNode = startNode;
		//localSolution.addItem(startNode, InitializeBPP.model.getObjects().get(startNode));
	}

	/*@Override
	public void onReceive(Object m) throws Throwable {
		if(m instanceof Message){
			if(((Message)m).getType().equals(Message.MsgType.GO)){
				localSolution.clear();
				Message msg = new Message(Message.MsgType.STATETRANS);
				msg.setupStateRequest(localSolution);
				graph.tell(msg,this.getSelf());
			} else if (((Message)m).getType().equals(Message.MsgType.STATETRANS)){
				localSolution.addItem(((Message) m).getState(),
						InitializeBPP.model.getObjects().get(((Message) m).getState()));
				//System.out.println("Numero di oggetti nella soluzione: "+localSolution.numBins());
				if(localSolution.numObjects() == this.spaceSize){
					Message msg = new Message(Message.MsgType.END);
					msg.setupSolution(localSolution);
					graph.tell(msg, getSelf()); //gli invio la mia soluzione
					System.out.println("Ho finito la soluzione");
				} else {
					Message msg = new Message(Message.MsgType.STATETRANS);
					msg.setupStateRequest(localSolution);
					graph.tell(msg,this.getSelf());
				}
			}
			
		}*/
	
	public void setStop(boolean stop){
		this.stop = stop;
	}
	
	@Override
	public void run(){
		
		while(!stop){
			//richiama il metodo per la state transition rule
		}
		
	}

}
