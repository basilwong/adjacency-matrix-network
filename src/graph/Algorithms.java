package ca.ubc.ece.cpen221.mp3.graph;

import java.util.*;


import ca.ubc.ece.cpen221.mp3.staff.Graph;
import ca.ubc.ece.cpen221.mp3.staff.NoPathException;
import ca.ubc.ece.cpen221.mp3.staff.Vertex;

public class Algorithms {

	/**
	 * Returns a random vertex from contents, which is not contained in container.
	 * Precondition: Container must not contain all of contents.
	 * @param container
	 * @param downstreamNeighbours
	 * @return
	 */
	public static Vertex listDoesNotContain(List<Vertex> downstreamNeighbours,
			ArrayList<Vertex> container){
		
		for (Vertex vertexCheck : downstreamNeighbours){
			if (!container.contains(vertexCheck)){
				return vertexCheck;
			}
		}		
		return null;
	}
	
	/**
	 * Follows the breadth first search algorithm to search throughout the graph.
	 * Precondition: Graph can't be empty.
	 * @param graph to be searched
	 * @return a set of lists containing the BFS of each vertex in the graph.
	 */
	public static Set<ArrayList<Vertex>> BFS(Graph graph){
		
		Set<ArrayList<Vertex>> setOfLists = new HashSet<ArrayList<Vertex>>();
	
		List<Vertex> vertices = graph.getVertices();
		List<Vertex> allDownstreamNeighbours = new ArrayList<Vertex>();
		List<Vertex> iterateNeighbours = new ArrayList<Vertex>();
		
		for (Vertex eachVertex : vertices){
			ArrayList<Vertex> vertexBFS = new ArrayList<Vertex>();
			List<Vertex> unvisitedVertices = graph.getVertices();
			iterateNeighbours.add(eachVertex);
			vertexBFS.add(eachVertex);
			unvisitedVertices.remove(eachVertex);
						
			while (!iterateNeighbours.isEmpty()){
			
				for (Vertex nodeLayer1 : iterateNeighbours){
				
					for (Vertex nodeLayer2 :
						graph.getDownstreamNeighbors(nodeLayer1)){
					/*
					 * If vertex/node is unvisited, adds to next iteration wave, 
					 * removes from unvisited list and adds it to the BFS list.
					 */
						if (unvisitedVertices.contains(nodeLayer2)){
							allDownstreamNeighbours.add(nodeLayer2);
							unvisitedVertices.remove(nodeLayer2);
							vertexBFS.add(nodeLayer2);
						}
					}
				}
				/*
				 * iterateNeighbours is used to iterate through terms added to 
				 * allDownstreamNeighbours, which is cleared after every loop.
				 */
				iterateNeighbours.clear();
				iterateNeighbours.addAll(allDownstreamNeighbours);
				allDownstreamNeighbours.clear();
			}
			setOfLists.add(vertexBFS);
		}
		return setOfLists;
	}
	
	/**
	 * Follows the depth first search algorithm to search throughout the graph.
	 * Precondition: Graph can't be empty.
	 * @param graph to be searched
	 * @return a set of lists containing the BFS of each vertex in the graph.
	 */
	public static Set<ArrayList<Vertex>> DFS(Graph graph){
		
		Set<ArrayList<Vertex>> setOfLists = new HashSet<ArrayList<Vertex>>();
	
		List<Vertex> downstreamNeighbours;
		Vertex presentNode;
		
		List<Vertex> vertexList = graph.getVertices();
		//DFS for each vertex in the graph.
		for (Vertex startingPoint : vertexList){
			
			boolean noMoreNodes = false;
			ArrayList<Vertex> vertexDFS = new ArrayList<Vertex>();
			presentNode = startingPoint;
			vertexDFS.add(presentNode);
			
			while (!noMoreNodes){
					
				downstreamNeighbours = graph.getDownstreamNeighbors(presentNode);
					
				if (vertexDFS.containsAll(downstreamNeighbours) ||
						downstreamNeighbours.isEmpty()){
					/*
					 * When no unvisited neighbors are available(already in vertexBFS),
					 * goes to the previous index in the list until 0. Then signals that 
					 * there are no more nodes.
					 */
					for (int k = vertexDFS.size() - 1; k >= 0; k -= 1){
						if (!vertexDFS.containsAll(
								graph.getDownstreamNeighbors(vertexDFS.get(k)))){
						
							presentNode = vertexDFS.get(k);
							break;
						}
						if (k == 0){
							noMoreNodes = true;
						}
					}
					
				} else { 
					presentNode = listDoesNotContain(downstreamNeighbours, vertexDFS);
					vertexDFS.add(presentNode);
					}
			}
			
			setOfLists.add(vertexDFS);
		}
		return setOfLists;
	}
	
	/**
	 * Returns the shortest distance(downstream) between two vertices.
	 * Precondition: Graph must contain vertices.
	 * @param graph containing the vertices.
	 * @param Starting vertex.
	 * @param Ending vertex.
	 * @return Shortest distance between starting and ending vertex.
	 * @throws Exception if there is no connection between the vertices a and b.
	 */
	public static int shortestDownstreamDistance(Graph graph, Vertex a, Vertex b) throws Exception {
		
		if(a.equals(b)){
			return 0;
		}
		
		List<Vertex> unvisitedVertices = graph.getVertices();
		List<Vertex> allDownstreamNeighbours = new ArrayList<Vertex>();
		boolean bNotFound = true;
		int distanceCounter = 0;
		
		List<Vertex> iterateNeighbours = new ArrayList<Vertex>();
		iterateNeighbours.add(a);
		unvisitedVertices.remove(a);
		//Implements Dijkstra's algorithm.
		for (int i = 0; i < graph.getVertices().size(); i++){
			
			for (Vertex nodeLayer1 : iterateNeighbours){
				
				for (Vertex nodeLayer2 :
					graph.getDownstreamNeighbors(nodeLayer1)){
					/*
					 * If vertex/node is unvisited, adds to next iteration wave
					 * and removes from unvisited list.
					 */
					if (unvisitedVertices.contains(nodeLayer2)){
						allDownstreamNeighbours.add(nodeLayer2);
						unvisitedVertices.remove(nodeLayer2);
					}
					if (b.equals(nodeLayer2)){
						bNotFound = false;
					}
				}
			}
			/*
			 * iterateNeighbours is used to iterate through terms added to 
			 * allDownstreamNeighbours, which is cleared after every loop.
			 */
			iterateNeighbours.clear();
			iterateNeighbours.addAll(allDownstreamNeighbours);
			allDownstreamNeighbours.clear();
			distanceCounter++;	
			//Break once b has been reached.
			if (!bNotFound){
				break;
			}
		}
		if (bNotFound){
			throw new NoPathException();
		}
		return distanceCounter;
	}
	
	/**
	 * Returns the shortest distance(downstream) between two vertices,
	 * assuming that the graph is unweighted.
	 * Precondition: Graph must contain vertices.
	 * @param graph containing the vertices.
	 * @param Starting vertex.
	 * @param Ending vertex.
	 * @return Shortest distance between starting and ending vertex.
	 * @throws Exception if there is no connection between the vertices a and b.
	 */
	public static int shortestDownstreamDistance1(Graph graph, Vertex a, Vertex b) throws Exception {
		
		if(a.equals(b)){
			return 0;
		}
		
		List<Vertex> unvisitedVertices = graph.getVertices();
		List<Vertex> allDownstreamNeighbours = new ArrayList<Vertex>();
		boolean bNotFound = true;
		int distanceCounter = 0;
		
		List<Vertex> iterateNeighbours = new ArrayList<Vertex>();
		iterateNeighbours.add(a);
		unvisitedVertices.remove(a);
		//Implements Dijkstra's algorithm.
		for (int i = 0; i < graph.getVertices().size(); i++){
			
			for (Vertex nodeLayer1 : iterateNeighbours){
				
				for (Vertex nodeLayer2 :
					graph.getDownstreamNeighbors(nodeLayer1)){
					/*
					 * If vertex/node is unvisited, adds to next iteration wave
					 * and removes from unvisited list.
					 */
					if (unvisitedVertices.contains(nodeLayer2)){
						allDownstreamNeighbours.add(nodeLayer2);
						unvisitedVertices.remove(nodeLayer2);
					}
					if (b.equals(nodeLayer2)){
						bNotFound = false;
					}
				}
			}
			/*
			 * iterateNeighbours is used to iterate through terms added to 
			 * allDownstreamNeighbours, which is cleared after every loop.
			 */
			iterateNeighbours.clear();
			iterateNeighbours.addAll(allDownstreamNeighbours);
			allDownstreamNeighbours.clear();
			distanceCounter++;	
			//Break once b has been reached.
			if (!bNotFound){
				break;
			}
		}
		if (bNotFound){
			throw new NoPathException();
		}
		return distanceCounter;
	}
	
	/**
	 * Finds the common upstream vertices of vertex A and vertex B.
	 * @param graph which contains vertices A and B
	 * @param a vertex in graph G
	 * @param a vertex in graph G
	 * @return a list of common upstream vertices between A and B.
	 */
	public static List<Vertex> commonUpstreamVertices(Graph G, Vertex A, Vertex B) {
		List<Vertex> commonVertices = new ArrayList<Vertex>();
		List<Vertex> upstreamA = G.getUpstreamNeighbors(A);
		List<Vertex> upstreamB = G.getUpstreamNeighbors(B);
		
		for (Vertex upstreamOfA : upstreamA){
			if (upstreamB.contains(upstreamOfA)){
				commonVertices.add(upstreamOfA);
			}
		}
		return commonVertices;
	}
	
	/**
	 * Finds the common downstream vertices of vertex A and vertex B.
	 * @param graph which contains vertices A and B
	 * @param a vertex in graph G
	 * @param a vertex in graph G
	 * @return a list of common downstream vertices between A and B.
	 */
	public static List<Vertex> commonDownstreamVertices(Graph G, Vertex A, Vertex B) {
		List<Vertex> commonVertices = new ArrayList<Vertex>();
		List<Vertex> downstreamA = G.getDownstreamNeighbors(A);
		List<Vertex> downstreamB = G.getDownstreamNeighbors(B);
		
		for (Vertex downstreamOfA : downstreamA){
			if (downstreamB.contains(downstreamOfA)){
				commonVertices.add(downstreamOfA);
			}
		}
		return commonVertices;
	}
}
