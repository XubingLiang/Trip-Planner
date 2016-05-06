import java.util.ArrayList;


/**
 * a heuristic interface that have method to get the estimate
 * @author Xubing Liang  5039153
 *
 * @param <E>
 */
public interface heuristic<E> {
	public int getEstimateHCost(TripPlanner<E> t, State<E> s);
}
