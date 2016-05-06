import java.util.ArrayList;

/**
 * this class if for saving the required trip from user input
 * @author Xubing Liang
 *
 * @param <E>
 */
public class Trip<E> {
	private City<E> from;
	private City<E> to;
	private int cost;
	
	
	/** 
	 * constructor of Trip class
	 * @param from
	 * @param to
	 */
	public Trip(City<E> from, City<E> to){
		this.from=from;
		this.to=to;
		setCost();
	}
	
	/**
	 * get the destination of one trip
	 * @return end city
	 */
	public City<E> getTo() {
		return to;
	}
	
	/**
	 * get the start city of one trip
	 * @return from city
	 */
	public City<E> getFrom() {
		return from;
	}
	
	/**
	 * get the cost of this trip
	 * @return the cost
	 */

	public int getCost() {
		return cost;
	}

	
	/**
	 * set the cost of this trip
	 */
	public void setCost(){
		if(from==null){
			cost=0;
			return;
		}
		cost=from.getEdgeCost(to);
	}
	

	

}
