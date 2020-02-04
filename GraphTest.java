//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: GraphTest.java
// Files: Graph.java PackageManager.java GraphTest.java PackageManagerTest
// Course: Comp Sci 400, section 002
//
// Author: Shihan Cheng
// Email: scheng93@wisc.edu
// Lecturer's Name: Debra Deppeler
// Description: PackageManager is used to process json package dependency files
// and provide function that make that information available to other users.
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * This class tests all the methods from Graph.java class
 * 
 * @author Shihan Cheng
 *
 */
class GraphTest {

  private Graph graph;

  /**
   * This method tests if we can successfully add a single vertex into graph
   */
  @Test
  public void test000_addSingleVertex() {

    try {
      graph = new Graph();
      graph.addVertex("A");

      if (!graph.getAllVertices().contains("A"))
        fail("graph should contain A");
    } catch (Exception e) {
      fail("Exception occurs");
    }
  }

  /**
   * This method tests if we can successfully add multiple vertices into graph
   */
  @Test
  public void test001_addMultleVertices() {

    try {
      graph = new Graph();
      graph.addVertex("A");
      graph.addVertex("B");
      graph.addVertex("C");

      if (!graph.getAllVertices().contains("A"))
        fail("graph should contain A");
      if (!graph.getAllVertices().contains("B"))
        fail("graph should contain B");
      if (!graph.getAllVertices().contains("C"))
        fail("graph should contain C");
      if (graph.order() != 3)
        fail("graph's order should be 3");
    } catch (Exception e) {
      fail("Exception occurs");
    }
  }

  /**
   * This method tests if we can successfully add a single vertex into graph and
   * remove it out
   */
  @Test
  public void test002_removeSingleVertex() {
    try {
      graph = new Graph();

      graph.addVertex("A");
      graph.removeVertex("A");

      if (graph.getAllVertices().contains("A"))
        fail("graph should not contain A");
      if (graph.size() != 0)
        fail("graph's order should be 0");

    } catch (Exception e) {
      fail("Exception occurs");
    }

  }

  /**
   * This method tests if we can successfully add multiple vertices into graph
   * and remove them out
   */
  @Test
  public void test003_removeMultVertices() {
    try {
      graph = new Graph();
      graph.addVertex("A");
      graph.addVertex("B");
      graph.addVertex("C");
      graph.removeVertex("A");
      graph.removeVertex("B");

      if (graph.getAllVertices().contains("A"))
        fail("graph should not contain A");
      if (graph.getAllVertices().contains("B"))
        fail("graph should not contain B");
      if (graph.order() != 1)
        fail("graph's order should be 1");
    } catch (Exception e) {
      fail("Exception occurs");
    }
  }

  /**
   * This method tests if we can successfully add an edge
   */
  @Test
  public void test004_addEdge() {
    try {
      graph = new Graph();
      graph.addVertex("A");
      graph.addEdge("A", "B");

      if (!graph.getAdjacentVerticesOf("A").get(0).equals("B"))
        fail("A's neighbors should contain B");
    } catch (Exception e) {
      fail("Exception occurs");
    } ;
  }

  /**
   * This method tests if we can successfully add an edge
   */
  @Test
  public void test005_removeEdge() {
    try {
      graph = new Graph();
      graph.addVertex("A");
      graph.addEdge("A", "B");
      graph.removeEdge("A", "B");

      if (graph.getAdjacentVerticesOf("A").contains("B"))
        fail("A's neighbors should not contain B");
    } catch (Exception e) {
      fail("Exception occurs");
    }
  }

  /**
   * This method tests if we can get all vertices from graph
   */
  @Test
  public void test006_getAllVertices() {
    try {

      // Expectation
      Set<String> list = new HashSet<String>();
      list.add("A");
      list.add("B");
      list.add("C");
      list.add("D");

      graph = new Graph();
      graph.addVertex("A");
      graph.addVertex("B");
      graph.addVertex("C");
      graph.addVertex("D");

      if (!graph.getAllVertices().equals(list))
        fail("graph and list should be same");
    } catch (Exception e) {
      fail("Exception occurs");
    }
  }

  /**
   * This method tests if we can get all adjacent vertices of a specific vertex
   */
  @Test
  public void test007_getAdjacentVerticesOf() {
    try {

      // Expectation
      List<String> list = new ArrayList<String>();
      list.add("B");
      list.add("C");
      list.add("D");

      graph = new Graph();
      graph.addVertex("A");
      graph.addEdge("A", "B");
      graph.addEdge("A", "C");
      graph.addEdge("A", "D");

      if (!graph.getAdjacentVerticesOf("A").equals(list))
        fail("graph and list should be same");
    } catch (Exception e) {
      fail("Exception occurs");
    }
  }

}
