package bppaco;


public class Message {
	
	public static enum MsgType {
		GO, END, GLOBALUPDATE, STATETRANS, START
	}
	
	private MsgType type;
	private int x_coord;
	private int y_coord;
	private int state;
	private AntSolution localSolution;
	
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
	
	public void setupStateRequest(AntSolution localSolution){
		this.setupSolution(localSolution);
	}
	
	public void setState(int state){
		this.state = state;
	}
	
	public void setupSolution(AntSolution localSolution){
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
	
	public AntSolution getLocalSolution(){
		return this.localSolution;
	}

}
