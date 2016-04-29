
public class State<E extends Comparable<E>> implements Comparable<State<E>>{
	private City<E> currentNode;
	private State<E> pState;
	private int gCost;
	private int hCost;
	
	public State(City<E> node, int costsoFar, State<E> pState){
		this.currentNode=node;
		this.pState=pState;
		this.gCost=costsoFar;
	}
	
	public void printCurrentPath(){
		String toPrint = getPathString("");
		System.out.println(toPrint);
	}
	
	private String getPathString(String s){
		String returnString = this.currentNode.toString().concat(s);
		if(this.pState!=null){
			return pState.getPathString("->"+returnString);
		}
		return returnString;
	}
	public void printCurrentPathAndCosts() {
		String toPrint = getPathString("");
		System.out.println(toPrint + " fCost = " + calculateFCost() + " gCost = " + gCost + " hCost = " + currentNode.getTransfer());
	}
	
	public City<E> getCurrentNode() {
		return currentNode;
	}

	@Override
	public int compareTo(State<E> o) {
		return calculateFCost() - o.calculateFCost();
	}
	
	public int calculateFCost(){
		return gCost+hCost;
	}

	public int getgCost() {
		return gCost;
	}

	public void setgCost(int gCost) {
		this.gCost = gCost;
	}
	
	
}

