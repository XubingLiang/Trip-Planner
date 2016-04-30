
public class State<E> implements Comparable<State<E>>{
	private City<E> currentNode;
	private State<E> pState;
	private int gCost;
	private int hCost;
	private int fCost;
	
	public State(City<E> node, int costsoFar, State<E> pState){
		this.currentNode=node;
		this.pState=pState;
		this.gCost=costsoFar;
		this.setfCost(calculateFCost());
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
		return gCost+hCost;
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

	public int getfCost() {
		return fCost;
	}
	public void sethCost(int hcost){
		this.hCost=hcost;
	}

	public void setfCost(int fCost) {
		this.fCost = fCost;
	}
	
	
	
}

