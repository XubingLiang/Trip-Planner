import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TripPlanner<E> {
	
	private ArrayList<City<E>> cities ;
	private State<E> finalState;
	private ArrayList<Trip<E>> trips;
	private int nnodes;
	private City<E> startCity;
	private BasicHeuristic<E> h=new BasicHeuristic<E>();
	
	public TripPlanner (){
		cities=new ArrayList<City<E>>();
		trips=new ArrayList<Trip<E>>();
	}
	
	
	public static void main(String[] args){
		Scanner sc = null;
		String[] input;
		TripPlanner<String> planner=new TripPlanner<String>();
	      try {
	          sc = new Scanner(new FileReader("input.txt")); 
	          while (sc.hasNextLine()){
	        	  input=sc.nextLine().split(" ");
	        	  if(input[0].equals("Transfer")){ 
	        		  	int transfer=Integer.parseInt(input[1]);
	        			String name=(input[2]);
	        			City<String> n=new City<String>(name,transfer);
	        		  	planner.cityInit(n);
	        	  }else if (input[0].equals("Time")){	        		  	
	        		  	planner.addconnection(input);
	        	  }else if(input[0].equals("Trip")){
	        		  	City<String> from= planner.getaCity(input[1]);
	        			City<String> to=planner.getaCity(input[2]);
	        		    Trip<String> t=new Trip<String>(from,to);
	        		  	planner.addtrip(t);
	        	  }	     
	        
	          }
	          planner.initStartCity();
	         // planner.test();
	          planner.aStarSearch();
	          planner.printresult();
	      }
	      catch (FileNotFoundException e) {
	    	  System.out.println(e);
	      }
	      finally {
	          if (sc != null) sc.close();	          

	      }	      
	}
	
	public void cityInit(City<E> n){
		this.cities.add(n);
	}
		
	private void addtrip(Trip<E> t) {
		trips.add(t);
		
	}
	
	private void addconnection(String[] input) {
		int cost=Integer.parseInt(input[1]);
		City<E> from= this.getaCity(input[2]);
		City<E> to=this.getaCity(input[3]);
		this.addEdge(from, to, cost);
		this.addEdge(to, from, cost);
	}
	



	public void initStartCity(){
		City<E> start = this.getaCity("London");
		this.setStartCity(start);
	}
	
	
	private void setStartCity(City<E> start) {
		this.startCity=start;
		
	}

	
	
	public ArrayList<Trip<E>> copylist(){
		ArrayList<Trip<E>> triplist=new ArrayList<Trip<E>>();
		for(Trip<E> e : this.trips){
			triplist.add(e);
		}
		return triplist;
	}
	
	public void setNnodes(int nnodes) {
		this.nnodes = nnodes;
	}

	public void setFinalState(State<E> currentState) {
		this.finalState = currentState;
	}
	


	

	public void test(){
		System.out.println("start city is "+this.startCity.getName());
		for(City<E> c: this.cities){
			System.out.println("conntection of "+c.getName());
			for(Edge<E> e:c.getEdge()){
				System.out.println("from " + e.getFrom().getName()+" to "+e.getTo().getName()+" cost is "+e.getCost());
			}
			System.out.println("");
		}
		for(Trip<E> t: this.trips){
			System.out.println("From "+ t.getFrom().getName() +" to "+ t.getTo().getName());			
		}
		
		
	}






	public void addEdge(City<E> from, City<E> to, int c) {
		from.addEdge(from, to, c);
	}
	
	public City<E> getaCity(String name){
		for(City<E> c: cities){
			if(c.getName().equals(name)){
				return c;
			}
		}
		return null;
	}
	
	public int closestEdge(){
		int cost=cities.get(0).getEdge().get(0).getCost();
		for(City<E> c: cities){
			for(Edge<E> e: c.getEdge()){
				if(e.getCost()<cost){
					cost=e.getCost();
				}
			}
		}
		return cost;
	}
	
	
	public void aStarSearch(){
		State<E> iniState = new State<E>(this.startCity,0,null);
		PriorityQueue<State<E>> states = new PriorityQueue<State<E>>();
		//PriorityQueue<State<E>> tempstates=new PriorityQueue<State<E>>();
		State<E> tmpState;
		iniState.sethCost(h.getEstimateHCost(this,iniState));
		iniState.setfCost();
		states.add(iniState);
		int nodes=0;
		int cost=0;
		while(states.size()>0){
			State<E> current=states.poll();
			nodes++;
			ArrayList<Trip<E>> unvisitedtrips=new ArrayList<Trip<E>>();
			unvisitedtrips=tripsleft(current);
			if(unvisitedtrips.size()==0){
				current.setgCost(current.getgCost()-current.getCurrentNode().getTransfer());
				this.setFinalState(current);
				this.setNnodes(nodes);
				break;
			}
			for(Trip<E> trip : unvisitedtrips ){
				City<E> from = trip.getFrom();
				City<E> to =trip.getTo();
				State<E> newState;
				if(current.getCurrentNode()==from){
					cost=current.getgCost()+current.getCurrentNode().getEdgeCost(to)+to.getTransfer();
					newState=new State<E>(to,cost,current);
					newState.sethCost(h.getEstimateHCost(this,newState));
					newState.setfCost();
					states.add(newState);
				} else if (current.getCurrentNode() != from){
					cost=current.getgCost()+current.getCurrentNode().getEdgeCost(from)+from.getTransfer();
					newState=new State<E>(from,cost,current);
					cost=newState.getgCost()+newState.getCurrentNode().getEdgeCost(to)
						 +to.getTransfer();
					tmpState=newState;
					newState=new State<E>(to,cost,tmpState);
					newState.sethCost(h.getEstimateHCost(this,newState));
					newState.setfCost();
					states.add(newState);
				}
			
				
			}
			
			
			
		}




	}
	
	public ArrayList<Trip<E>> tripsleft(State<E> currentState){
		ArrayList<Trip<E>> triplist=this.copylist();		
		int i=0;
		while(currentState.getPstate() !=null){
			i=0;
			City<E> to=currentState.getCurrentNode();
			City<E> from=currentState.getPstate().getCurrentNode();
			currentState=currentState.getPstate();
			while(i<triplist.size()){
				
				if(from==triplist.get(i).getFrom()
					&& to ==triplist.get(i).getTo()){
					triplist.remove(i);
				}
				i++;
			}
		}
		
		return triplist;
	}
	
	public void printresult(){
		System.out.print(this.nnodes+" nodes expanded\n");
		State<E> s = this.finalState;
		s.printCurrentPathAndCosts();
	}
	
	public ArrayList<Trip<E>> getTrips(){
		return this.trips;
	}

	
}
	

