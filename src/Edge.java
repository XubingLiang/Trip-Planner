

public class Edge<E> {
	private City<E> from;
	private City<E> to;
	private int cost;
	
	public Edge(City<E> from,City<E> to, int c){
		this.from=from;
		this.to=to;
		this.cost=c;
	}
	
	public City<E> getTo() {
		return to;
	}
	
	/**
	 * Method to return the other end of an edge.
	 * NOTE: this method does not check if this node actually contains
	 * the edge that is passed into it.
	 * @param node
	 * @return
	 */
	public City<E> getOtherEnd(City<E> node) {
		if (from == node) {
			return to;
		}
		return from;
	}

	public void setTo(City<E> to) {
		this.to = to;
	}

	public int getCost() {
		return cost;
	}
	

	public City<E> getFrom() {
		return from;
	}


}
