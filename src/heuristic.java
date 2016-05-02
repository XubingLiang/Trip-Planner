import java.util.ArrayList;

public interface heuristic<E> {
	public int getEstimateHCost(ArrayList<Trip<E>> t, State<E> s);
}
