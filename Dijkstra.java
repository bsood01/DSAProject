import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

//Dijkstra algorithm to get shortest path
public class Dijkstra {
	
	private double[] distTo;          
	private DirectedEdge[] edgeTo; 
    PriorityQueue<Vertex> pq;   
    
	public void shortestPath(int s, Digraph G) {
		 distTo = new double[G.V()];
	     edgeTo = new DirectedEdge[G.V()];

	    for (int v = 0; v < G.V(); v++) {
 			distTo[v] = -1;
 		}
 		
     	distTo[s] = 0.0;

     	// relax vertices in order of distance from s
     	pq = new PriorityQueue<Vertex>();
     	pq.add(new Vertex(s, distTo[s]));
     	while (!pq.isEmpty()) {
     		Vertex temp=pq.poll();
     		int v =temp.s ;
     		for (DirectedEdge e : G.adj(v)) {
         		relax(e);
     		}
     	}

	}
	public double[] getDistTo() {
		return distTo;
	}
	public DirectedEdge[] getEdgeTo() {
		return edgeTo;
	}
	//auxiliary function to relax edge, uses built-in PQ
	 private void relax (DirectedEdge e) {
	        int v = e.from(), w = e.to();
	        if (distTo[w]==-1||distTo[w] > distTo[v] + e.weight()) {
	            distTo[w] = distTo[v] + e.weight();
	            edgeTo[w] = e;
	            Vertex temp = new Vertex(w,distTo[w]);
	            if (pq.contains(temp)) {  
	            	pq.remove(temp);
	            	pq.add(new Vertex(w,distTo[w]));
	            }
	            else {
	            	pq.add(new Vertex(w, distTo[w]));
	            }
	        }
	    }
}

//Class to store edge information
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
	@Override
    public boolean equals(Object obj) {
        if (v==((DirectedEdge) obj).from()&&w==((DirectedEdge) obj).to()&&
        		weight==((DirectedEdge) obj).weight()) {
        	return true;
        }
        else{
        	return false;
        }
    }
    @Override
    public String toString() {
    	
    	return v + "->" + w + " " + String.format("%5.2f", weight);
    }
}


//Class to store the graph information
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
    public int E() {
        return E;
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

	public boolean contains(DirectedEdge e, int v) {
		for (DirectedEdge tmp : adj(v)) {
            if(tmp.equals(e)) {
            	return true;
            }
        }
		return false;
	}
}

// Mapping from stopID to an Array Index
//Since we don't know how long the ID could be
class Map {
	 private HashMap<Integer, Integer> hmap = new HashMap<>();
	 List<Integer> indexToID=new ArrayList<Integer>();  
	 
	 public void addStop(int stopID) {
	     if (!hmap.containsKey(stopID)){
             hmap.put(stopID, hmap.size());
             indexToID.add(stopID);
	 	}
	 }
	 public HashMap<Integer, Integer> getHmap() {
		 return hmap;
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
	public boolean contains(int stopID) {
		return hmap.containsKey(stopID);
	}
	public int size() {
		return hmap.size();
	}
	
}

class Vertex implements Comparable {
	int s;
	double dist;
	public Vertex(int s, double dist) {
		this.s=s;
		this.dist=dist;
	}
	public double getDist() {
		return dist;
	}
	public int getVertex() {
		return s;
	}
	@Override
	public int compareTo(Object o) {
	     if (dist < ((Vertex)o).getDist()) {
	            return -1; 
	     }
	     if (dist > ((Vertex)o).getDist()) {
	            return 1; 
	     }
	        return 0; 
	}
	@Override
    public boolean equals(Object obj) {
        if (s==((Vertex) obj).getVertex()) {
        	return true;
        }
        else{
        	return false;
        }
    }
	
}
