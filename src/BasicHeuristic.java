import java.util.ArrayList;

public class BasicHeuristic implements heuristic {
	
	public BasicHeuristic (){
	}

	public int getEstimateHCost(int i,int size,int cost) {
		int mincost=cost;
		int num=size;
		int hcost=0;
		hcost=mincost*(num-i)/size;
		return hcost;
	}
	


}
