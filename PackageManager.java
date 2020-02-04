//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: PackageManager.java
// Files: Graph.java PackageManager.java GraphTest.java PackageManagerTest
// Course: Comp Sci 400, section 002
//
// Author: Shihan Cheng
// Email: scheng93@wisc.edu
// Lecturer's Name: Debra Deppeler
// Description: PackageManager is used to process json package dependency files
// and provide function that make that information available to other users.
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Filename: PackageManager.java Project: p4 Authors:
 * 
 * PackageManager is used to process json package dependency files and provide
 * function that make that information available to other users.
 * 
 * Each package that depends upon other packages has its own entry in the json
 * file.
 * 
 * Package dependencies are important when building software, as you must
 * install packages in an order such that each package is installed after all of
 * the packages that it depends on have been installed.
 * 
 * For example: package A depends upon package B, then package B must be
 * installed before package A.
 * 
 * This program will read package information and provide information about the
 * packages that must be installed before any given package can be installed.
 * all of the packages in
 * 
 * You may add a main method, but we will test all methods with our own Test
 * classes.
 */

/**
 * This class is an application to read and parse data from json file and store
 * them in a directed graph data structure
 * 
 * @author Shihan Cheng
 *
 */
public class PackageManager {

  private Graph graph;

  /*
   * Package Manager default no-argument constructor.
   */
  public PackageManager() {
    this.graph = new Graph();
  }

  /**
   * Takes in a file path for a json file and builds the package dependency
   * graph from it.
   * 
   * @param jsonFilepath the name of json data file with package dependency
   *                     information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException           if the give file cannot be read
   * @throws ParseException        if the given json cannot be parsed
   */
  public void constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {

    // Initialization
    String depen;
    Package[] packs = null;

    // Parse the json File
    Object obj = new JSONParser().parse(new FileReader(jsonFilepath));
    JSONObject jo = (JSONObject) obj;
    JSONArray packages = (JSONArray) jo.get("packages");
    packs = new Package[packages.size()];

    // Iterate the array and add packages and their dependencies in the graph
    for (int i = 0; i < packs.length; i++) {
      JSONObject packageItem = (JSONObject) packages.get(i);
      String pack = (String) packageItem.get("name");
      JSONArray dependencies = (JSONArray) packageItem.get("dependencies");

      this.graph.addVertex(pack);
      // Add the edges
      for (int j = 0; j < dependencies.size(); j++) {
        depen = (String) dependencies.get(j);
        this.graph.addEdge(pack, depen);
      }
    }
  }

  /**
   * Helper method to get all packages in the graph.
   * 
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return this.graph.getAllVertices();
  }

  /**
   * Given a package name, returns a list of packages in a valid installation
   * order.
   * 
   * Valid installation order means that each package is listed before any
   * packages that depend upon that package.
   * 
   * @return List<String>, order in which the packages have to be installed
   * 
   * @throws CycleException           if you encounter a cycle in the graph
   *                                  while finding the installation order for a
   *                                  particular package. Tip: Cycles in some
   *                                  other part of the graph that do not affect
   *                                  the installation order for the specified
   *                                  package, should not throw this exception.
   * 
   * @throws PackageNotFoundException if the package passed does not exist in
   *                                  the dependency graph.
   */
  public List<String> getInstallationOrder(String pkg)
      throws CycleException, PackageNotFoundException {

    if (!this.graph.getAllVertices().contains(pkg))
      throw new PackageNotFoundException();

    // Create three lists to track all vertices and their adjacent list
    // stack is used to backup all vertices that be visited
    // instOrder is used to store all vertices that be visited
    // visited is used to store all vertices that be visited
    LinkedList<String> stack = new LinkedList<>();
    LinkedList<String> instOrder = new LinkedList<String>();
    List<String> visited = new ArrayList<String>();

    // Call helper mthod to do the recursion
    getInstOrderHelper(pkg, visited, stack);

    // Then add the argument to stack to iterate
    stack.addFirst(pkg);

    // Iterate the stack and do not add the duplicates in instOrder
    while (stack.peek() != null) {
      String pop = stack.pop();
      if (!instOrder.contains(pop)) {
        instOrder.addFirst(pop);
      }
    }

    return instOrder;
  }

  /**
   * Helper method to get installment order and check CycleException
   * 
   * @param pkg     Package to get the installment order
   * @param visited List stores all adjacent vertices in the recursion
   * @param stack   List backups all visited vertices
   * @throws CycleException If cycles found
   */
  private void getInstOrderHelper(String pkg, List<String> visited,
      LinkedList<String> stack) throws CycleException {

    // Get all dependencies of current package
    List<String> depen = this.graph.getAdjacentVerticesOf(pkg);
    // Store the current package
    visited.add(pkg);


    // For all packages in graph, if we found duplicate package as current one
    // in the path, throw the CycleException
    // Otherwise, recurse its dependencies
    for (String ver : depen) {
      if (visited.contains(ver)) {
        throw new CycleException();
      } else {
        getInstOrderHelper(ver, visited, stack);
      }
    }
    // When the for loop ends, we remove the current package from visited list
    // and backup it in stack
    visited.remove(pkg);
    stack.addFirst(pkg);
  }

  /**
   * Given two packages - one to be installed and the other installed, return a
   * List of the packages that need to be newly installed.
   * 
   * For example, refer to shared_dependecies.json - toInstall("A","B") If
   * package A needs to be installed and packageB is already installed, return
   * the list ["A", "C"] since D will have been installed when B was previously
   * installed.
   * 
   * @return List<String>, packages that need to be newly installed.
   * 
   * @throws CycleException           if you encounter a cycle in the graph
   *                                  while finding the dependencies of the
   *                                  given packages. If there is a cycle in
   *                                  some other part of the graph that doesn't
   *                                  affect the parsing of these dependencies,
   *                                  cycle exception should not be thrown.
   * 
   * @throws PackageNotFoundException if any of the packages passed do not exist
   *                                  in the dependency graph.
   */
  public List<String> toInstall(String newPkg, String installedPkg)
      throws CycleException, PackageNotFoundException {
    // We get two installation-order of them and remove the intersection from
    // the longer path, then return the list contains remain packages
    List<String> prev = getInstallationOrder(installedPkg);
    List<String> retList = getInstallationOrder(newPkg);
    retList.removeAll(prev);

    return retList;
  }

  /**
   * Return a valid global installation order of all the packages in the
   * dependency graph.
   * 
   * assumes: no package has been installed and you are required to install all
   * the packages
   * 
   * returns a valid installation order that will not violate any dependencies
   * 
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph
   */
  public List<String> getInstallationOrderForAllPackages()
      throws CycleException {
    List<String> retList = new ArrayList<>();

    // Iterate all packages in the graph
    for (String ver : graph.getAllVertices()) {
      // If the first package is not in retList yet
      if (!retList.contains(ver)) {
        try {
          // Get its installation order
          List<String> list = getInstallationOrder(ver);
          // Add all the packages in its installation order into the retList
          // Avoid the duplicates
          for (String str : list) {
            if (!retList.contains(str)) {
              retList.add(str);
            }
          }
        } catch (PackageNotFoundException e) {
        }
      }
    }

    return retList;
  }

  /**
   * Find and return the name of the package with the maximum number of
   * dependencies.
   * 
   * Tip: it's not just the number of dependencies given in the json file. The
   * number of dependencies includes the dependencies of its dependencies. But,
   * if a package is listed in multiple places, it is only counted once.
   * 
   * Example: if A depends on B and C, and B depends on C, and C depends on D.
   * Then, A has 3 dependencies - B,C and D.
   * 
   * @return String, name of the package with most dependencies.
   * @throws CycleException if you encounter a cycle in the graph
   */
  public String getPackageWithMaxDependencies() throws CycleException {
    if (this.graph.size() == 0)
      return "";
    // Get all packages
    Set<String> list = this.graph.getAllVertices();
    String max = "";
    int maxNum = 0;
    // Iterate all of them and find which has the maximum number of the
    // installation order
    for (String ver : list) {
      try {
        if (maxNum < this.getInstallationOrder(ver).size()) {
          maxNum = this.getInstallationOrder(ver).size();
          max = ver;
        }
      } catch (PackageNotFoundException e) {
      }
    }

    return max;
  }

  /**
   * Main method
   * 
   * @param args Any type
   */
  public static void main(String[] args) {
    System.out.println("PackageManager.main()");
  }

}
