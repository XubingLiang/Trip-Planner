import java.util.ArrayList;

public class BasicHeuristic<E> implements heuristic<E> {
	
	public BasicHeuristic (){
	}

	public int getEstimateHCost(ArrayList<Trip<E>> trips,State<E> s) {
		int hCost=0;
		if(s.getPstate()==null){
			for(Trip<E> t : trips){
				hCost += t.getCost();
			}
		}else {
			while(s.getPstate()!=null){
				ArrayList<Trip<E>> temp=new ArrayList<Trip<E>>();
			}
		}
		
		return hCost;
	}
	
	


}
