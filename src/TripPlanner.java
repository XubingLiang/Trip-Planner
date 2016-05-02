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
	          //planner.test();
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
	


	
	public int getTripsCost(){
		int mincost=0;
		for(Trip<E> e : trips){
			mincost += e.getCost()+e.getFrom().getTransfer()+e.getTo().getTransfer();
		}
		return mincost;
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
		System.out.println(this.getTripsCost());
		
		
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
	
	
	public void aStarSearch(){
		State<E> iniState = new State<E>(this.startCity,0,null);
		PriorityQueue<State<E>> stateQueue = new PriorityQueue<State<E>>();
		iniState.sethCost(h.getEstimateHCost(this.trips,iniState));
		iniState.setfCost();
		System.out.println(iniState.gethCost());
		stateQueue.add(iniState);
		int nodes=0;
		while(stateQueue.size()>0){
			State<E> currentState=stateQueue.poll();
			nodes++;
			if(currentState.getPstate()!=null){
				//System.out.println(currentState.getfCost());
				//currentState.printCurrentPathAndCosts();
			}
			if(tripsfound(currentState)){
				this.setFinalState(currentState);
				this.setNnodes(nodes);
				break;
			}
			for(City<E> e:currentState.getCurrentNode().getConnected()){
				if(currentState.getPstate()==null){					
					State<E> newState = new State<E>(e,currentState.getgCost()+
									currentState.getCurrentNode().getEdge(e).getCost(),currentState);
					newState.setfCost();
					stateQueue.add(newState);
				} else {
					State<E> newState = new State<E>(e,currentState.getgCost()+currentState.getCurrentNode().getTransfer()+
							currentState.getCurrentNode().getEdge(e).getCost(),currentState);
					newState.setfCost();
					stateQueue.add(newState);
				}
			}
		}
	}
	
	public boolean tripsfound(State<E> currentState){
		ArrayList<Trip<E>> triplist=copylist();		
		Boolean found=false;
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
		
		if(triplist.size()==0){
			found=true;
		}
		
		return found;
	}
	
	public void printresult(){
		System.out.print(this.nnodes+" nodes expanded\n");
		State<E> s = this.finalState;
		s.printCurrentPathAndCosts();
	}

	
}
	

