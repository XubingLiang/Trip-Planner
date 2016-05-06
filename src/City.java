import java.util.ArrayList;
/**
 * this Class store the information of a City and the Edge of this City
 * @author Xubing Liang
 *
 * @param <E>  generic type
 */
public class City<E>{
	private E name;
	private ArrayList<Edge<E>> edge;
	private int transfer;
	
	
	/**
	 * constructor of City
	 * @param name  name of city
	 * @param transfer   transfer time
	 */
	
	public City(E name,int transfer){
		this.name = name;
		edge = new ArrayList<Edge<E>>();
		this.transfer=transfer;
	}
	
	/**
	 * add edge to the city
	 * @param from  start City
	 * @param to   end Citys
	 * @param cost  travel cost
	 */
	
	public void addEdge(City<E> from,City<E> to, int cost){
		edge.add(new Edge<E>(from,to,cost));
	}
	
	/**
	 * get all connected node of a city
	 * @return a list of all connected city from one city
	 */
	public ArrayList<City<E>> getConnected(){
		ArrayList<City<E>> returnData=new ArrayList<City<E>>();
		for(Edge<E> e:this.edge){
			returnData.add(e.getTo());
		}
		return returnData;
	}
	
	/**
	 * get the edge between two city
	 * @param to   destination
	 * @return a Edge
	 */
	public Edge<E> getEdge(City<E> to){
		for(Edge<E> e:edge){
			if(to==e.getTo()){
				return e;
			}
		}
		return null;
	}
	
	/**
	 * getter of City information
	 * @return city name
	 */

	public E getName() {
		return name;
	}
	
	/**
	 * get the edge cost to one exact city
	 * @param to  destination
	 * @return   the cost from one city to another city
	 */

	public int getEdgeCost(City<E> to){
		for(Edge<E> e:edge){
			if(to==e.getTo()){
				return e.getCost();
			}
		}
		return 0;
	}
	
	/**
	 *  getting the edge list of one city
	 * @return a list of edges
	 */
	public ArrayList<Edge<E>> getEdge() {
		return edge;
	}
	
	/**
	 * get the transfer time of one city
	 * @return transfer time
	 */


	public int getTransfer() {
		return transfer;
	}
	
	


	
}
