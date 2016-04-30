import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;




public class Map<E extends Comparable<E>>  {

		private ArrayList<City<E>> cities;
		private City<E> startCity;
		private ArrayList<Edge<E>> trips;
		private State<E> finalState;
		private int nnodes;
		
		public Map(){
			cities = new ArrayList<City<E>>();
			trips = new ArrayList<Edge<E>>();
		}
		

		public void addNode(City<E> n) {
			cities.add(n);
		}
		
		public void addTrip(City<E> from, City<E> to){
			Edge<E> newtrip=new Edge<E>(from,to,0);
			trips.add(newtrip);
		}
		
		public void printresult(){
			System.out.print(this.getNnodes()+" nodes expanded\n");
			State<E> s = this.getState();
			ArrayList<Edge<E>> temp=new ArrayList<Edge<E>>();
			s.printCurrentPathAndCosts();
		}

		
		public void addEdge(City<E> from, City<E> to, int c) {
			from.addEdge(from, to, c);
		}
		
		public void aStarSearch(){
			State<E> iniState = new State<E>(this.getStartCity(),0,null);
			PriorityQueue<State<E>> stateQueue = new PriorityQueue<State<E>>();
			stateQueue.add(iniState);
			int nodes=0;
			while(stateQueue.size()>0){
				State<E> currentState=stateQueue.poll();
				nodes++;
				if(currentState.getPstate()!=null){
				currentState.printCurrentPathAndCosts();
				}
				if(tripsfound(currentState)){
					this.finalState=currentState;
					this.nnodes=nodes;
					break;
				}
				for(City<E> e:currentState.getCurrentNode().getConnected()){
					if(currentState.getPstate()==null){
						State<E> newState = new State<E>(e,currentState.getgCost()+
								currentState.getCurrentNode().getEdge(e).getCost(),currentState);
						stateQueue.add(newState);
					} else {
						State<E> newState = new State<E>(e,currentState.getgCost()+currentState.getCurrentNode().getTransfer()+
								currentState.getCurrentNode().getEdge(e).getCost(),currentState);
						stateQueue.add(newState);
					}
				}
			}
		}
		
		public boolean tripsfound(State<E> currentState){
			ArrayList<Edge<E>> triplist=new ArrayList<Edge<E>>();
			for(Edge<E> e : trips){
				triplist.add(e);
			}		
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
		

		public City<E> getaCity(String name){
			City<E> city = null;
			for(City<E> c: cities){
				if(c.getName().equals(name)){
					city= c;
				}
			}
			return city;
		}
		
		public void test(){
			System.out.println("start city is "+this.getStartCity().getName());
			for(City<E> c: cities){
				System.out.println("conntection of "+c.getName());
				for(Edge<E> e:c.getEdge()){
					System.out.println("from " + e.getFrom().getName()+" to "+e.getTo().getName()+" cost is "+e.getCost());
				}
				System.out.println("");
			}
			for(Edge<E> t: trips){
				System.out.println("trip require "+t.getFrom().getName() + " to "+ t.getTo().getName());
			}
			
		}

		public City<E> getStartCity() {
			return startCity;
		}

		public State<E> getState(){
			return finalState;
		}
		
		public int getNnodes() {
			return nnodes;
		}
		public ArrayList<City<E>> getCities() {
			return cities;
		}


		public void setStartCity(City<E> start) {
			this.startCity=start;
			
		}



		


}


