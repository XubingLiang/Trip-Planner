import java.util.ArrayList;


/**
 * this heuristic class get the estimate by calculating the remain 
 * cost of all trips that needed and the trip that may be added to link 
 * two trips during planning
 * @author Xubing Liang    z5039153
 *	
 * @param <E>
 */
public class LegitHeuristic<E> implements heuristic<E> {
	private ArrayList<Trip<E>> tripstogo;
	
	public LegitHeuristic (){
	}
	
	/**
	 * this is to get the estimate cost from one state to the end
	 * 
	 * 
	 */
	public int getEstimateHCost(TripPlanner<E> t,State<E> s) {
		int hCost=0;
		ArrayList<Trip<E>> unvisited=t.tripsleft(s);
		tripstogo=unvisited;
		//calcute the remaining trip Edgecost;
		int time =getEdgeCost(unvisited);
		//calcute the remaining city transfer time 
		int transfertime=getlefttransfer(unvisited);
		//calcute the num of link trip to add;
		int numoflinktrip=getnumofLinkTrip(unvisited,s.getCurrentNode());
		//calcute the cheapest trip in the map to estimate;
		int shortestEdge=t.closestEdge();
		//the estimate time to the end;
		hCost=time+transfertime+shortestEdge*numoflinktrip;

		return hCost ;
	}
	
	
	/**
	 * this method is to get the num of link trip that needed to be add to the whole 
	 * trip, a link trip is the trip that are not required.
	 * @param unvisited
	 * @param c
	 * @return total number of link trip
	 */
	private int getnumofLinkTrip(ArrayList<Trip<E>> unvisited,City<E> c) {
		int num=0;
		boolean adj=false;
		for(Trip<E> t : this.tripstogo){
			adj=false;
			for(Trip<E> e: this.tripstogo){
				if(t.getTo()==e.getFrom()){
					adj=true;
					break;
				}
			}
			if(!adj){
				num++;
			}
		}
		
		for(Trip<E> a:this.tripstogo){
			if(a.getFrom().equals(c)){
				num--;
				break;
			}
		}
		num++;
		return num;
	}
	
	/**
	 * this method is to get the total travel cost among the trips
	 * @param trips
	 * @return the travel time
	 */

	public int getEdgeCost(ArrayList<Trip<E>> trips){
		int time=0;
		for (Trip<E> t : trips){
			time=time+t.getCost();
		}
		return time;
	}
	
	/**
	 * this is to get the remaining transfer among the required trips.
	 * @param unvisited
	 * @return return the remaining transfer time
	 */
	
	public int getlefttransfer(ArrayList<Trip<E>> unvisited){
		int transfer =0;
		int start=0;
		int end=0;
		for (Trip<E> t: unvisited){
			start=t.getFrom().getTransfer();
			end =t.getTo().getTransfer();
			if(!isEnd(t.getTo())){
				end=0;
			}
			
			transfer=transfer+start+end;
			
		}
			
		return transfer;
	}
	
	/**
	 * check if the City in two trip the destination
	 * @param c   City<E> city
	 * @return true/false
	 */
	
	public boolean isEnd(City<E> c){
		for(Trip<E> t: this.tripstogo ){
			if(t.getFrom().equals(c)){
				return false;
				
			}
		}
		return true;
		
	}
	
		
	
	


}
