import java.util.ArrayList;

public class City<E>{
	private E name;
	private ArrayList<Edge<E>> edge;
	private int transfer;
	
	
	public City(E name,int transfer){
		this.name = name;
		edge = new ArrayList<Edge<E>>();
		this.transfer=transfer;
	}
	
	public void addEdge(City<E> from,City<E> to, int cost){
		edge.add(new Edge<E>(from,to,cost));
	}
	
	//All connected nodes
	public ArrayList<City<E>> getConnected(){
		ArrayList<City<E>> returnData=new ArrayList<City<E>>();
		for(Edge<E> e:this.edge){
			returnData.add(e.getTo());
		}
		return returnData;
	}
	
	//Edge to to
	public Edge<E> getEdge(City<E> to){
		for(Edge<E> e:edge){
			if(to==e.getTo()){
				return e;
			}
		}
		return null;
	}
	


	public E getName() {
		return name;
	}


	public ArrayList<Edge<E>> getEdge() {
		return edge;
	}


	public int getTransfer() {
		return transfer;
	}

	public void sethCost(int hCost) {
		this.transfer = hCost;
	}



	
}
