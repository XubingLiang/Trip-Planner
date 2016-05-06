import java.util.ArrayList;

public class State<E> implements Comparable<State<E>>{
	//private Trip<E> currentTrip;
	private City<E> currentNode;
	private State<E> pState;
	private int gCost;
	private int hCost;
	private int fCost;
	
	public State(City<E> city, int costsoFar, State<E> pState){
		this.currentNode=city;
		this.pState=pState;
		this.gCost=costsoFar;
	}
	
	public void printCurrentPath(){
		String toPrint = getPathString("");
		System.out.println(toPrint);
	}
	
	
	private String getPathString(String s){
		String returnString =this.currentNode.getName().toString().concat(s);
		if(this.pState!=null){
			return pState.getPathString("\n"+"Trip "+pState.getCurrentNode().getName()+" to "+returnString);
		}
		return returnString;
	}
	public void printCurrentPathAndCosts() {
		String toPrint = getPathString("");
		String s="London"+"\n";
		toPrint=toPrint.substring(s.length());
		System.out.println("Cost = " + this.getgCost() + "\n"+toPrint);
	}
	
	
	public City<E> getCurrentNode() {
		return currentNode;
	}

	public int compareTo(State<E> o) {
		return calculateFCost() - o.calculateFCost();
	}
	
	public int calculateFCost(){
		return gCost+this.gethCost();
	}
	
	public State<E> getPstate (){
		return pState;
	}
	

	public int getgCost() {
		return gCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}
	
	public void sethCost(int hcost){
		this.hCost=hcost;
	}

	public int gethCost() {
		return hCost;
	}
	
	public void setfCost(){
		fCost=this.calculateFCost();
	}
	
	public int getfCost(){
		return fCost;
	}
	
	

	
	
	
}

