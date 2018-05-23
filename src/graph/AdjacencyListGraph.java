package ca.ubc.ece.cpen221.mp3.graph;

import java.util.*;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AdjacencyListGraph implements Graph {
	
    private HashMap<Vertex, ArrayList<ArrayList<Vertex>>> listGraph;
    private static final int DOWNSTREAMLIST = 0;
    private static final int UPSTREAMLIST = 1;
    

	public AdjacencyListGraph(){ 
		listGraph = new HashMap<Vertex, ArrayList<ArrayList<Vertex>>>();
	    }
	
	
	/**
	 * Adds a vertex to the graph. 
	 *
	 * Precondition: v is not already a vertex in the graph
	 */
	@Override
	public void addVertex(Vertex v){
		ArrayList<ArrayList<Vertex>> newVertexList = new ArrayList<ArrayList<Vertex>>();
		ArrayList<Vertex> downstreamList = new ArrayList<Vertex>();
		ArrayList<Vertex> upstreamList = new ArrayList<Vertex>();
		newVertexList.add(downstreamList);
		newVertexList.add(upstreamList);
		listGraph.put(v, newVertexList);
	}

	/**
	 * Adds an edge from v1 to v2.
	 *
	 * Precondition: v1 and v2 are vertices in the graph. 
	 * Precondition: Cannot make an edge from a vertex to itself.
	 * 
	 * Postcondition: Will not add anything if the edge already exists 
	 */
	@Override
	public void addEdge(Vertex v1, Vertex v2){
		
		if (!this.edgeExists(v1, v2)){
			listGraph.get(v1).get(DOWNSTREAMLIST).add(v2);
			listGraph.get(v2).get(UPSTREAMLIST).add(v1);
		}
	}

	/**
	 * Check if there is an edge from v1 to v2.
	 *
	 * Precondition: v1 and v2 are vertices in the graph Postcondition: return
	 * true if an edge from v1 connects to v2
	 */
	@Override
	public boolean edgeExists(Vertex v1, Vertex v2){
		
		boolean existingEdge = false;
		
		if (listGraph.get(v1).get(DOWNSTREAMLIST).contains(v2)){
				existingEdge = true;	
		}
		return existingEdge;
	}

	/**
	 * Get an array containing all downstream vertices adjacent to v.
	 *
	 * Precondition: v is a vertex in the graph
	 * 
	 * Postcondition: returns a list containing each vertex w such that there is
	 * an edge from v to w. The size of the list must be as small as possible
	 * (No trailing null elements). This method should return a list of size 0
	 * if v has no downstream neighbors.
	 */
	@Override
	public List<Vertex> getDownstreamNeighbors(Vertex v){
		return  listGraph.get(v).get(DOWNSTREAMLIST);
    }
	/**
	 * Get an array containing all upstream vertices adjacent to v.
	 *
	 * Precondition: v is a vertex in the graph
	 * 
	 * Postcondition: returns a list containing each vertex u such that there is
	 * an edge from u to v. The size of the list must be as small as possible
	 * (No trailing null elements). This method should return a list of size 0
	 * iff v has no upstream neighbors.
	 */
	@Override
	public List<Vertex> getUpstreamNeighbors(Vertex v){
		return listGraph.get(v).get(UPSTREAMLIST);
    }
	

	/**
	 * Get all vertices in the graph.
	 *
	 * Postcondition: returns a list containing all vertices in the graph. This
	 * method should return a list of size 0 iff the graph has no vertices.
	 */
	@Override
	public List<Vertex> getVertices(){
		List<Vertex> newList = new ArrayList<Vertex>();
    	
    	for(Vertex addedVertex: listGraph.keySet()) {
    		newList.add(addedVertex);
    	}
    	return newList;
    }
}