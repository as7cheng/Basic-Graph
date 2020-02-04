//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: Graph.java
// Files: Graph.java PackageManager.java GraphTest.java PackageManagerTest
// Course: Comp Sci 400, section 002
//
// Author: Shihan Cheng
// Email: scheng93@wisc.edu
// Lecturer's Name: Debra Deppeler
// Description: PackageManager is used to process json package dependency files
// and provide function that make that information available to other users.
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * This class is a undirected and unweighted graph implementation
 * 
 * @author A-Team 21
 *
 */
public class Graph implements GraphADT {

  // Fields
  private HashMap<String, ArrayList<String>> graph;
  private int numVertices;
  private int numEdges;

  /**
   * Default no-argument constructor
   */
  public Graph() {
    this.graph = new HashMap<String, ArrayList<String>>();
    this.numVertices = 0;
    this.numEdges = 0;
  }

  /**
   * Add new vertex to the graph.
   *
   * If vertex is null or already exists, method ends without adding a vertex or
   * throwing an exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already
   * in the graph
   * 
   * @param vertex Vertex to add
   */
  public void addVertex(String vertex) {
    if (vertex == null || this.contains(vertex))
      return;

    this.graph.put(vertex, new ArrayList<String>());
    this.numVertices++;
  }

  /**
   * Remove a vertex and all associated edges from the graph.
   * 
   * If vertex is null or does not exist, method ends without removing a vertex,
   * edges, or throwing an exception.
   * 
   * Valid argument conditions: 1. vertex is non-null 2. vertex is not already
   * in the graph
   * 
   * @param vertex Vertex we want to remove from the graph
   */
  public void removeVertex(String vertex) {
    // Check if we have the valid condition to remove the vertex
    if (this.graph.isEmpty())
      return;
    if (vertex == null || !this.contains(vertex))
      return;

    // Remove it from other all vertices' adjacent list
    for (String ver2 : this.graph.get(vertex)) {
      this.removeEdge(ver2, vertex);
    }
    // Remove the vertex from the graph
    this.graph.remove(vertex, this.graph.get(vertex));
    this.numVertices--;
  }

  /**
   * Add the edge of vertex1 and vertex2 to this graph. (edge is undirected and
   * unweighted) If either vertex does not exist, add vertex, and add edge, no
   * exception is thrown. If the edge exists in the graph, no edge is added and
   * no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are
   * in the graph 3. the edge is not in the graph
   * 
   * @param vertex1 Vertex1 which is the source/destination
   * @param vertex2 Vertex2 which is the source/destination
   */
  public void addEdge(String vertex1, String vertex2) {
    if (vertex1 == null || vertex2 == null)
      return;

    // If either vertex1 or vertex2 is not in the graph, add them in
    if (!this.contains(vertex1))
      this.addVertex(vertex1);
    if (!this.contains(vertex2))
      this.addVertex(vertex2);

    // Check if there already is an edge from vertex1 to vertex2 in the graph
    if (this.containEdge(vertex1, vertex2))
      return;

    // Add the edge in and increase the number of edges
    this.graph.get(vertex1).add(vertex2);
    this.graph.get(vertex2).add(vertex1);
    this.numEdges++;
  }

  /**
   * Remove the edge from vertex1 to vertex2 from this graph. (edge is directed
   * and unweighted) If either vertex does not exist, or if an edge from vertex1
   * to vertex2 does not exist, no edge is removed and no exception is thrown.
   * 
   * Valid argument conditions: 1. neither vertex is null 2. both vertices are
   * in the graph 3. the edge from vertex1 to vertex2 is in the graph
   * 
   * @param vertex1 Vertex1 which is the source
   * @param vertex2 Vertex2 which is the destination
   */
  public void removeEdge(String vertex1, String vertex2) {
    // First check if we have valid conditions to remove the edge
    if (vertex1 == null || vertex2 == null)
      return;
    if (!this.contains(vertex1) || !this.contains(vertex2))
      return;
    if (!this.containEdge(vertex1, vertex2)
        || !this.containEdge(vertex2, vertex1))
      return;

    // If so, remove ver2 from the adjacent list of ver1 and decrease the number
    // of edges
    this.graph.get(vertex1).remove(vertex2);
    this.graph.get(vertex2).remove(vertex1);
    this.numEdges--;
  }

  /**
   * Helper method to check if the graph contains a specific vertex
   * 
   * @param vertex Vertex to check
   * @return true if graph contains vertex. False otherwise
   */
  private boolean contains(String vertex) {
    return this.graph.containsKey(vertex);
  }

  /**
   * Helper method to check if the graph contains an edge
   * 
   * @param vertex1 Vertex1 which is the source / destination
   * @param vertex2 Vertex2 which is the source / destination
   * @return true if the graph contains the edge. False otherwise
   */
  private boolean containEdge(String vertex1, String vertex2) {
    return this.graph.get(vertex1).contains(vertex2)
        && this.graph.get(vertex2).contains(vertex1);
  }

  /**
   * This method is designed for getting all the vertices of graph
   * 
   * @return Set that contains all the vertices
   */
  public Set<String> getAllVertices() {
    return this.graph.keySet();
  }

  /**
   * This method is designed for getting all the neighbor (adjacent) vertices of
   * a vertex
   *
   * @return List that contains all the neighbor (adjacent) vertices
   */
  public List<String> getAdjacentVerticesOf(String vertex) {
    return this.graph.get(vertex);
  }

  /**
   * Returns the number of edges in this graph.
   */
  public int size() {
    return this.numEdges;
  }

  /**
   * This method is designed for getting the number of vertices in this graph.
   * 
   * @return Number of vertices
   */
  public int order() {
    return this.numVertices;
  }

}
