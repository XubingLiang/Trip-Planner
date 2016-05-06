
/**
 * this class is to store the information of all the connection of the map
 * @author Xubing Liang z5039153
 *
 * @param <E>
 */
public class Edge<E> {
	private City<E> from;
	private City<E> to;
	private int cost;
	
	
	/**
	 * Constructor of Edge
	 * @param from
	 * @param to
	 * @param c
	 */
	public Edge(City<E> from,City<E> to, int c){
		this.from=from;
		this.to=to;
		this.cost=c;
	}
	
	/**
	 * method to return the end of a edge
	 * @return City<E>
	 */
	
	public City<E> getTo() {
		return to;
	}
	
	/**
	 * Method to return the other end of an edge.
	 * NOTE: this method does not check if this node actually contains
	 * the edge that is passed into it.
	 * @param City<E>
	 * @return City<E>
	 */
	public City<E> getOtherEnd(City<E> node) {
		if (from == node) {
			return to;
		}
		return from;
	}
	
	/**
	 * method to get the cost of the edge
	 * @return Cost
	 */


	public int getCost() {
		return cost;
	}
	
	/**
	 * method to return the start city of a edge
	 * @return City<E>
	 */
	

	public City<E> getFrom() {
		return from;
	}


}
