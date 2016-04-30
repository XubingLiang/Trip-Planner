import java.util.ArrayList;

public interface heuristic {
	public int getEstimateHCost(ArrayList<Edge<String>> trips,State<String> s);
}
