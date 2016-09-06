package aco;

import java.util.List;

public class Message {
	
	public static enum MsgType {
		GO, END, GLOBALUPDATE, STATETRANS, START
	}
	
	private MsgType type;
	private int x_coord;
	private int y_coord;
	private int state;
	private List<Integer> localSolution;
	
	public Message(MsgType type){
		this.type = type;
	}
	
	public MsgType getType(){
		return type;
	}
	
	public void setupLocalCoord(int x_coord, int y_coord){
		this.x_coord = x_coord;
		this.y_coord = y_coord;
	}
	
	public void setupStateRequest(int state, List<Integer> localSolution){
		this.state = state;
		this.localSolution = localSolution;
	}
	
	public void setState(int state){
		this.state = state;
	}
	
	public void setupSolution(List<Integer> localSolution){
		this.localSolution = localSolution;
	}
	
	
	public int getXCoord(){
		return this.x_coord;
	}
	
	public int getYCoord(){
		return this.y_coord;
	}
	
	public int getState(){
		return state;
	}
	
	public List<Integer> getLocalSolution(){
		return this.localSolution;
	}

}
