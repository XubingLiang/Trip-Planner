import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class TripPlanner {
	
	private Map<String> map ;
	private State<String> finalState;
	private int nnodes;
	private BasicHeuristic h=new BasicHeuristic();
	private ArrayList<Edge<String>> trips;
	public TripPlanner (){
		map=new Map<String>();
		trips=new ArrayList<Edge<String>>();
	}
	public static void main(String[] args){
		Scanner sc = null;
		String[] input;
		TripPlanner planner=new TripPlanner();
	      try {
	          sc = new Scanner(new FileReader("input.txt")); 
	          while (sc.hasNextLine()){
	        	  input=sc.nextLine().split(" ");
	        	  if(input[0].equals("Transfer")){ 
	        		  	planner.cityInit(input);
	        	  }else if (input[0].equals("Time")){
	        		  	planner.addconnection(input);
	        	  }else if(input[0].equals("Trip")){
	        		  	planner.addtrip(input);
	        	  }	     
	        
	          }
	          planner.initStartCity();
	          planner.test();
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
	
	private void addtrip(String[] input) {
		City<String> from= map.getaCity(input[1]);
		City<String> to=map.getaCity(input[2]);
		this.addTrip(from, to);
		
	}
	public Map<String> getMap () {
		return this.map;
	}
	
	private void addconnection(String[] input) {
		int cost=Integer.parseInt(input[1]);
		City<String> from= map.getaCity(input[2]);
		City<String> to=map.getaCity(input[3]);
		map.addEdge(from, to, cost);
		map.addEdge(to, from, cost);
	}
	
	public void printresult(){
		System.out.print(this.getNnodes()+" nodes expanded\n");
		State<String> s = this.getFinalState();
		s.printCurrentPathAndCosts();
	}

	public void cityInit(String[] input){
		int transfer=Integer.parseInt(input[1]);
		String name=input[2];
		City<String> n=new City<String>(name,transfer);
		map.addNode(n);
	}
	
	public void initStartCity(){
		City<String> start = map.getaCity("London");
		this.getMap().setStartCity(start);
	}
	
	public void aStarSearch(){
		State<String> iniState = new State<String>(this.getMap().getStartCity(),0,null);
		PriorityQueue<State<String>> stateQueue = new PriorityQueue<State<String>>();
		iniState.sethCost(h.getEstimateHCost(this.getTrips(),iniState));
		stateQueue.add(iniState);
		int nodes=0;
		while(stateQueue.size()>0){
			State<String> currentState=stateQueue.poll();
			nodes++;
			//if(currentState.getPstate()!=null){
			//currentState.printCurrentPathAndCosts();
			//}
			if(tripsfound(currentState)){
				this.setFinalState(currentState);
				this.setNnodes(nodes);
				break;
			}
			for(City<String> e:currentState.getCurrentNode().getConnected()){
				if(currentState.getPstate()==null){
					
					State<String> newState = new State<String>(e,currentState.getgCost()+
							currentState.getCurrentNode().getEdge(e).getCost(),currentState);
					newState.sethCost(h.getEstimateHCost(this.getTrips(), newState));
					stateQueue.add(newState);
				} else {
					State<String> newState = new State<String>(e,currentState.getgCost()+currentState.getCurrentNode().getTransfer()+
							currentState.getCurrentNode().getEdge(e).getCost(),currentState);
					//newState.sethCost(h.getEstimateHCost(this.getTrips(), newState));
					stateQueue.add(newState);
				}
			}
		}
	}
	
	public boolean tripsfound(State<String> currentState){
		ArrayList<Edge<String>> triplist=copylist();		
		Boolean found=false;
		int i=0;
		while(currentState.getPstate() !=null){
			i=0;
			City<String> to=currentState.getCurrentNode();
			City<String> from=currentState.getPstate().getCurrentNode();
			currentState=currentState.getPstate();
			while(i<triplist.size()){
				
				if(from==triplist.get(i).getFrom()
					&& to ==triplist.get(i).getTo()){
					triplist.remove(i);
				}
				i++;
			}
		}
		
		if(triplist.size()==0){
			found=true;
		}
		
		return found;
	}
	
	public ArrayList<Edge<String>> copylist(){
		ArrayList<Edge<String>> triplist=new ArrayList<Edge<String>>();
		for(Edge<String> e : this.getTrips()){
			triplist.add(e);
		}
		return triplist;
	}
	
	public int getNnodes() {
		return nnodes;
	}
	public void setNnodes(int nnodes) {
		this.nnodes = nnodes;
	}
	public State<String> getFinalState() {
		return finalState;
	}
	public void setFinalState(State<String> finalState) {
		this.finalState = finalState;
	}
	
	public void addTrip(City<String> from, City<String> to){
		Edge<String> newtrip=new Edge<String>(from,to,from.getEdgeCost(to));
		this.trips.add(newtrip);
	}
	
	public ArrayList<Edge<String>> getTrips() {
		return trips;
	}


	public void test(){
		System.out.println("start city is "+this.getMap().getStartCity().getName());
		for(City<String> c: this.map.getCities()){
			System.out.println("conntection of "+c.getName());
			for(Edge<String> e:c.getEdge()){
				System.out.println("from " + e.getFrom().getName()+" to "+e.getTo().getName()+" cost is "+e.getCost());
			}
			System.out.println("");
		}
		
		
	}

	
}
	

