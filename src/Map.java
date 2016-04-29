import java.util.ArrayList;
import java.util.PriorityQueue;




public class Map<E extends Comparable<E>>  {

		private ArrayList<City<E>> cities;
		private City<E> startCity;
		
		public Map(){
			cities = new ArrayList<City<E>>();
		}
		

		public void addNode(City<E> n) {
			cities.add(n);
		}
		
		public void addEdge(City<E> from, City<E> to, int c) {
			from.addEdge(from, to, c);
		}
		
		public void aStarSearch(City<E> from, City<E> to){
			State<E> iniState = new State<E>(from,0,null);
			PriorityQueue<State<E>> stateQueue = new PriorityQueue<State<E>>();
			stateQueue.add(iniState);
			while(stateQueue.size()>0){
				State<E> currentState=stateQueue.poll();
				if(currentState.getCurrentNode()==to){
					break;
				}
				for(City<E> e:currentState.getCurrentNode().getConnected()){
					State<E> newState = new State<E>(e,currentState.getgCost()+currentState.getCurrentNode().getEdge(e).getCost(),currentState);
					stateQueue.add(newState);
				}
			}
		}
		
		public ArrayList<City<E>> getCities() {
			return cities;
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
			
		}

		public City<E> getStartCity() {
			return startCity;
		}

		public void setStartCity(City<E> startCity) {
			this.startCity = startCity;
		}


}


