import java.util.ArrayList;

public class BasicHeuristic<E> implements heuristic<E> {
	
	public BasicHeuristic (){
	}

	public int getEstimateHCost(ArrayList<Trip<E>> trips,State<E> s,int cost) {
		int hCost=cost;
		ArrayList<Trip<E>> temp2=new ArrayList<Trip<E>>();
		temp2=s.gettripsinState(s);
		int i=0;
		for(Trip<E> t: trips){
			for(Trip<E>r: temp2){
				if(t.getFrom().getName().equals(r.getFrom().getName())&&
						t.getTo().getName().equals(r.getTo().getName())){
					//hCost=hCost-r.getCost();
					i++;
					break;
					
				}
			}
		}
		
		hCost=hCost*(trips.size()-i)/trips.size();
		return hCost ;
	}
	
	
		
	
	


}
