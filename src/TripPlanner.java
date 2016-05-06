import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;


/**
 * TripPlanner class store information of map, and plan the trip
 * that required
 * taking control of use input and return planned trip to user
 *
 * @author Xubing Liang z5039153
 *
 * @param <E>  generic type
 */
public class TripPlanner<E> {
	
	private ArrayList<City<E>> cities ;
	private State<E> finalState;
	private ArrayList<Trip<E>> trips;
	private int nnodes;
	private City<E> startCity;
	private LegitHeuristic<E> heuristic=new LegitHeuristic<E>();
	
	/**
	 * constructor for Tripplanner
	 */
	public TripPlanner (){
		cities=new ArrayList<City<E>>();
		trips=new ArrayList<Trip<E>>();
	}
	
	/**
	 * the main function
	 * @param args  String[]
	 */
	public static void main(String[] args){
		Scanner sc = null;
		String[] input;
		TripPlanner<String> planner=new TripPlanner<String>();
	      try {
	          sc = new Scanner(new FileReader(args[0])); 
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
	
	/**
	 * method to initialize City information
	 * @param n  city
	 */
	
	public void cityInit(City<E> n){
		this.cities.add(n);
	}
		
	/**
	 * method to add required trip
	 * @param Trip t
	 */
	private void addtrip(Trip<E> t) {
		trips.add(t);
		
	}
	
	/**
	 * method to add connection between two cities
	 * @param input String[]
	 */
	
	private void addconnection(String[] input) {
		int cost=Integer.parseInt(input[1]);
		City<E> from= this.getaCity(input[2]);
		City<E> to=this.getaCity(input[3]);
		this.addEdge(from, to, cost);
		this.addEdge(to, from, cost);
	}
	


	/**
	 * method to initialize the startcity.
	 */
	public void initStartCity(){
		City<E> start = this.getaCity("London");
		this.setStartCity(start);
	}
	
	/**
	 * method to set the start city
	 * @param start  start city<E>
	 */
	private void setStartCity(City<E> start) {
		this.startCity=start;
		
	}

	
	/**
	 * method to copy the whole required list 
	 * @return a list of trips
	 */
	
	public ArrayList<Trip<E>> copylist(){
		ArrayList<Trip<E>> triplist=new ArrayList<Trip<E>>();
		for(Trip<E> e : this.trips){
			triplist.add(e);
		}
		return triplist;
	}
	
	/**
	 * method to set how many nodes expanded for the search
	 * @param nnodes  number of nodes expanded
	 */
	public void setNnodes(int nnodes) {
		this.nnodes = nnodes;
	}
	
	/**
	 * set the final state
	 * @param currentState  State current state
	 */

	public void setFinalState(State<E> currentState) {
		this.finalState = currentState;
	}
	
	/**
	 * method to initialize an edge
	 * @param from start city
	 * @param to   end city
	 * @param c    travel cost
	 */

	public void addEdge(City<E> from, City<E> to, int c) {
		from.addEdge(from, to, c);
	}
	
	/**
	 * method to get a city by name
	 * @param name City anme
	 * @return  get a exact city
	 */
	
	public City<E> getaCity(String name){
		for(City<E> c: cities){
			if(c.getName().equals(name)){
				return c;
			}
		}
		return null;
	}
	
	/**
	 * return the cheapest cost of a edge in the map
	 * @return the smallest travel time in the map
	 */
	
	public int closestEdge(){
		int cost=cities.get(0).getEdge().get(0).getCost();
		for(City<E> c: this.cities){
			for(Edge<E> e: c.getEdge()){
				if(e.getCost()<cost){
					cost=e.getCost();
				}
			}
		}
		return cost;
	}
	
	/**
	 * method to search the whole map to get trips planned
	 */
	
	
	public void aStarSearch(){
		State<E> iniState = new State<E>(this.startCity,0,null);
		PriorityQueue<State<E>> states = new PriorityQueue<State<E>>();
		//PriorityQueue<State<E>> tempstates=new PriorityQueue<State<E>>();
		State<E> tmpState;
		iniState.sethCost(heuristic.getEstimateHCost(this,iniState));
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
				current.setfCost();
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
					newState.sethCost(heuristic.getEstimateHCost(this,newState));
					newState.setfCost();
					states.add(newState);
				} else if (current.getCurrentNode() != from){
					cost=current.getgCost()+current.getCurrentNode().getEdgeCost(from)+from.getTransfer();
					newState=new State<E>(from,cost,current);
					cost=newState.getgCost()+newState.getCurrentNode().getEdgeCost(to)
						 +to.getTransfer();
					tmpState=newState;
					newState=new State<E>(to,cost,tmpState);
					newState.sethCost(heuristic.getEstimateHCost(this,newState));
					newState.setfCost();
					states.add(newState);
				}
			
				
			}
			
			
			
		}




	}
	
	/**
	 * method to return the trips left to be planned
	 * @param currentState   trip planned so far
	 * @return  remaining trips to travel
	 */
	
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
	
	/**
	 * print the final result out
	 */
	
	public void printresult(){
		System.out.print(this.nnodes+" nodes expanded\n");
		State<E> s = this.finalState;
		s.printCurrentPathAndCosts();
	}
	
	/**
	 * getter for trips list
	 * @return  the list of all trips need to be planned
	 */
	
	public ArrayList<Trip<E>> getTrips(){
		return this.trips;
	}

	
}
	

