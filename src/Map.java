import java.util.ArrayList;
import java.util.Collections;
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
			

		public City<E> getaCity(String name){
			City<E> city = null;
			for(City<E> c: cities){
				if(c.getName().equals(name)){
					city= c;
				}
			}
			return city;
		}
		
		public City<E> getStartCity() {
			return startCity;
		}

		public ArrayList<City<E>> getCities() {
			return cities;
		}


		public void setStartCity(City<E> start) {
			this.startCity=start;
			
		}




		


}


