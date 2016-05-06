import java.util.ArrayList;

public interface heuristic<E> {
	public int getEstimateHCost(TripPlanner<E> t, State<E> s);
}
