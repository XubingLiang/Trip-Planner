import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class TripPlanner {
	
	private Map<String> map ;
	public TripPlanner (){
		map=new Map<String>();
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
	          //planner.getMap().test();
	          planner.getMap().aStarSearch();
	          planner.getMap().printresult();
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
		map.addTrip(from, to);
		
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
	
	
	
	

	
}
	

