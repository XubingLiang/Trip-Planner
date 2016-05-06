import java.util.ArrayList;

public class BasicHeuristic<E> implements heuristic<E> {
	
	public BasicHeuristic (){
	}

	public int getEstimateHCost(TripPlanner<E> t,State<E> s) {
		int hCost=0;
		ArrayList<Trip<E>> unvisited=t.tripsleft(s);
		int time =0;
		City<E> city = s.getCurrentNode();
		City<E> start=s.getCurrentNode();
		for (Trip<E> e: unvisited){
			City<E> from= e.getFrom();
			City<E> to=e.getTo();
			if(start==from){
				time=start.getTransfer()+to.getTransfer()+from.getEdgeCost(to);
			} else if(start != from){
				time=start.getTransfer()+start.getEdgeCost(from)+from.getTransfer()+from.getEdgeCost(to)+to.getTransfer();
			}
			start=to;
		}
		hCost=time;

		return hCost ;
	}
	
	/*for(Trip<E> trip : unvisitedtrips ){
		City<E> from = trip.getFrom();
		City<E> to =trip.getTo();
		State<E> newState;
		if(current.getCurrentNode()==from){
			cost=current.getgCost()+current.getCurrentNode().getEdgeCost(to)+to.getTransfer();
			newState=new State<E>(to,cost,current);
			states.add(newState);
		} else if (current.getCurrentNode() != from){
			cost=current.getgCost()+current.getCurrentNode().getEdgeCost(from)+from.getTransfer();
			newState=new State<E>(from,cost,current);
			cost=newState.getgCost()+newState.getCurrentNode().getEdgeCost(to)
				 +to.getTransfer();
			tmpState=newState;
			newState=new State<E>(to,cost,tmpState);
			states.add(newState);
		}*/
	
	
	/*public int getlefttransfer(ArrayList<Trip<E>> unvisited){
		int transfer =0;
		int start=0;
		int end=0;
		for (Trip<E> t: unvisited){
			
			
			transfer=transfer+start+end;
			
		}
			
		return transfer;
	}*/
	
		
	
	


}
