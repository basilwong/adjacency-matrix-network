package ca.ubc.ece.cpen221.mp3.graph;
import java.util.*;

import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class AdjacencyMatrixGraph implements Graph {

	
	private ArrayList<LinkedList<Integer>> matrix;
	private ArrayList<Vertex> vertices;
	private List<Vertex> upStreamNeighbours;
    private List<Vertex> downStreamNeighbours;
	public static final int NO_EDGE = 0;
	public static final int DOWNSTREAM = 1;
    public static final int UPSTREAM = 2;
    public static final int BOTH = 3;
	
	
    public AdjacencyMatrixGraph(){
      matrix = new ArrayList<LinkedList<Integer>>(); 
      vertices = new ArrayList<Vertex>();
    }

    /**
	 * Adds a vertex to the graph.
	 *
	 * Precondition: v is not already a vertex in the graph
	 */
    @Override
	public void addVertex(Vertex v) {
		LinkedList<Integer> newList = new LinkedList<Integer>();
		for (int i = 0; i < matrix.size(); i++){
			newList.add(0);
		}
		vertices.add(v);
		matrix.add(newList);
		for (LinkedList<Integer> matrixRow : matrix){
			matrixRow.add(Integer.valueOf(0));
		}
		
	}
	
    /**
     * Replaces the edge type in the list of v1Index at the index of v2Index.
     * Precondition: indexes can't be the same.
     * @param 1st matrix index, 2nd matrix index, type of edge (0,1,2,3,4)
     */
    private void replaceInteger(int v1Index, int v2Index, int direction){
		matrix.get(v1Index).remove(v2Index);
		matrix.get(v1Index).add(v2Index, Integer.valueOf(direction));
    }

	/**
	 * Adds an edge from v1 to v2.
	 *
	 * Precondition: v1 and v2 are vertices in the graph
	 * Precondition: Cannot make an edge from a vertex to itself.
	 * 
	 * Postcondition: Will not add anything if the edge already exists.
	 */
    @Override
	public void addEdge(Vertex v1, Vertex v2){
   	
    	int v1Index = vertices.indexOf(v1);
    	int v2Index = vertices.indexOf(v2);
    	//The if statements checks for already existing edges:
    	if (matrix.get(v1Index).get(v2Index).intValue() == NO_EDGE) {
    		replaceInteger(v1Index, v2Index, DOWNSTREAM);    		
    	} else if (matrix.get(v1Index).get(v2Index).intValue() == UPSTREAM) {
    		replaceInteger(v1Index, v2Index, BOTH);
    	}
    	if (matrix.get(v2Index).get(v1Index).intValue() == NO_EDGE) {
    		replaceInteger(v2Index, v1Index, UPSTREAM);  		
    	} else if (matrix.get(v2Index).get(v1Index).intValue() == DOWNSTREAM) {
    		replaceInteger(v2Index, v1Index, BOTH);
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
    	
    	int v1Index = vertices.indexOf(v1);
    	int v2Index = vertices.indexOf(v2);
    	boolean edgeIsThere = false;
    	
    	if (matrix.get(v1Index).get(v2Index).intValue() == DOWNSTREAM ||
    			matrix.get(v1Index).get(v2Index).intValue() == BOTH){
    		edgeIsThere = true;
    	}
    	return edgeIsThere;
    }

	/**
	 * Get an array containing all downstream vertices adjacent to v.
	 *
	 * Precondition: v is a vertex in the graph
	 * 
	 * Postcondition: returns a list containing each vertex w such that there is
	 * an edge from v to w. The size of the list must be as small as possible
	 * (No trailing null elements). This method should return a list of size 0
	 * iff v has no downstream neighbors.
	 */
    @Override
	public List<Vertex> getDownstreamNeighbors(Vertex v){
    	
    	downStreamNeighbours = new ArrayList<Vertex>();
    	
    	for (int j = 0; j < matrix.size(); j++){
    		if (this.edgeExists(v, vertices.get(j))){
    			downStreamNeighbours.add(vertices.get(j));
    		}
    	}
    	return downStreamNeighbours;
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
    	
    	upStreamNeighbours = new ArrayList<Vertex>();
    	
    	for (int j = 0; j < matrix.size(); j++){
    		if (this.edgeExists(vertices.get(j), v)){
    			upStreamNeighbours.add(vertices.get(j));
    		}
    	}
    	return upStreamNeighbours;
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
    	
    	for(Vertex addedVertex: vertices) {
    		newList.add(addedVertex);
    	}
    	return newList;
    }

}
