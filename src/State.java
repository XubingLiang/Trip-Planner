import java.util.ArrayList;


/**
 * this class is for storing the information of a state
 * @author Xubing Liang
 *
 * @param <E>
 */
public class State<E> implements Comparable<State<E>>{
	//private Trip<E> currentTrip;
	private City<E> currentNode;
	private State<E> pState;
	private int gCost;
	private int hCost;
	private int fCost;
	
	
	/**
	 * constructor of State
	 * @param city
	 * @param costsoFar
	 * @param pState
	 */
	public State(City<E> city, int costsoFar, State<E> pState){
		this.currentNode=city;
		this.pState=pState;
		this.gCost=costsoFar;
	}
	
	

	
	/**
	 * this method is to get information of a state
	 * @param s
	 * @return a string that contain all information of a state
	 */
	private String getPathString(String s){
		String returnString =this.currentNode.getName().toString().concat(s);
		if(this.pState!=null){
			return pState.getPathString("\n"+"Trip "+pState.getCurrentNode().getName()+" to "+returnString);
		}
		return returnString;
	}
	
	/**
	 * print the information  of a state
	 */
	public void printCurrentPathAndCosts() {
		String toPrint = getPathString("");
		String s="London"+"\n";
		toPrint=toPrint.substring(s.length());
		System.out.println("Cost = " + this.getgCost() + "\n"+toPrint);
	}
	
	/**
	 * this method aim at getting the current node of a state
	 * @return a City<E>
	 */
	
	public City<E> getCurrentNode() {
		return currentNode;
	}
	
	/**
	 * comparator
	 */

	public int compareTo(State<E> o) {
		return calculateFCost() - o.calculateFCost();
	}
	
	/**
	 * calculate the Fcost of a state
	 * @return
	 */
	
	public int calculateFCost(){
		return gCost+this.gethCost();
	}
	
	/**
	 * this method is to get previous state 
	 * @return previous state
	 */
	
	public State<E> getPstate (){
		return pState;
	}
	
	
	/**
	 * this method return the gCost of a state
	 * @return cost sofar
	 */
	public int getgCost() {
		return gCost;
	}
	
	/**
	 * gCost setter
	 * @param gCost
	 */
	public void setgCost(int gCost) {
		this.gCost = gCost;
	}
	
	/**
	 * a setter of hCost
	 * @param hcost
	 */
	
	public void sethCost(int hcost){
		this.hCost=hcost;
	}
	
	/**
	 * a getter for hCost
	 * @return hcost
	 */

	public int gethCost() {
		return hCost;
	}
	
	/**
	 * setter for fCost
	 */
	
	public void setfCost(){
		fCost=this.calculateFCost();
	}
	
	/**
	 * getter for fCost
	 * @return fcost
	 */
	
	public int getfCost(){
		return fCost;
	}
	
	

	
	
	
}

