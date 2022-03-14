import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Dijkstra {

}
class DirectedEdge {

	private final int v;			//source	
    private final int w;			//dest
    private final double weight;	//weight

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() {
        return v;
    }
    public int to() {
        return w;
    }
    public double weight() {
        return weight;
    }
}



class Digraph {
	private final int V;       
    private int E;                      
    private LinkedList<DirectedEdge>[] adj;   
    
    
    @SuppressWarnings("unchecked")
	public Digraph(int V) { 
        this.V = V;
        this.E = 0;
        adj =(LinkedList<DirectedEdge>[]) new LinkedList[V];
        for (int v = 0; v < V; v++) {
           adj[v] = new LinkedList<DirectedEdge>();
        }
	}

    public int V() {
        return V;
    }
    public Iterable<DirectedEdge> adj(int v){
    	return adj[v];
    }
    public Iterable<DirectedEdge> edges() {
        LinkedList<DirectedEdge> list = new LinkedList<DirectedEdge>();
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    } 
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        adj[v].add(e);
        E++;
    }
}

class Map {
	 private HashMap<Integer, Integer> hmap = new HashMap<>();
	 List<Integer> indexToID=new ArrayList<Integer>();  
	 
	 public void addStop(int stopID) {
	     if (!hmap.containsKey(stopID)){
             hmap.put(stopID, hmap.size());
             indexToID.add(stopID);
	 	}
	 }
	 public int getIndex(int stopID) {
		 if(hmap.containsKey(stopID)) {
			 return hmap.get(stopID);
		 }
		 return -1;
		 
	 }
	 public int getStopID(int index) {
		 if(index<indexToID.size()) {
			 return indexToID.get(index);
		 }
		 return -1;
	 }
	


}
