import java.util.ArrayList;

public class BasicHeuristic implements heuristic {
	
	public BasicHeuristic (){
		
	}

	public int getEstimateHCost(ArrayList<Edge<String>> trips,State<String> state) {
		ArrayList<Edge<String>> triplist=new ArrayList<Edge<String>>();
		int mincost=0;
		int num=trips.size();
		int i=0;
		int hcost=0;
		for(Edge<String> e : trips){
			triplist.add(e);
			mincost += e.getCost();
		}
		if(state.getPstate()==null){
			hcost=mincost*num;
			System.out.println("A hcost is"+hcost);
			return hcost;
			
		}
		System.out.println(mincost);
		/*while(state.getPstate() !=null){
			i=0;
			City<String> to=state.getCurrentNode();
			City<String> from=state.getPstate().getCurrentNode();
			state=state.getPstate();
			while(i<triplist.size()){			
				if(from==triplist.get(i).getFrom()
					&& to ==triplist.get(i).getTo()){
					i++;
				}
			}
		}*/
		hcost=mincost*(num-i);
		System.out.println(hcost);
		return hcost;
	}

}
