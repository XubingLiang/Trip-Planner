import java.util.ArrayList;

public class Trip<E> {
	private City<E> from;
	private City<E> to;
	private int cost;
	
	public Trip(City<E> from, City<E> to){
		this.from=from;
		this.to=to;
	}
	public City<E> getTo() {
		return to;
	}
	public void setTo(City<E> to) {
		this.to = to;
	}
	public City<E> getFrom() {
		return from;
	}
	public void setFrom(City<E> from) {
		this.from = from;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
}
