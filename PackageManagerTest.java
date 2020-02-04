//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: PackageManagerTest.java
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
import java.io.FileNotFoundException;
import java.io.IOException; 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

/**
 * This class tests all the methods from PackageManager.java class
 * 
 * @author Shihan Cheng
 *
 */
class PackageManagerTest {

  private PackageManager newPM;

  /**
   * This method tests if we can successfully use constructor to create a graph
   * of packages
   */
  @Test
  void test000_ConstructGraph() {

    newPM = new PackageManager();

    try {
      newPM.constructGraph("valid.json");
    } catch (FileNotFoundException e) {
      fail("FileNotFoundException happens");
    } catch (IOException e) {
      e.printStackTrace();
      fail("IOException happens");
    } catch (ParseException e) {
      fail("ParseException happens");
    }
  }

  /**
   * This method tests if the FileNotFoundException will be thrown when we use
   * the invalid jsonfile path
   */
  @Test
  void test001_Parse_invalid_jsonfile_path() {

    newPM = new PackageManager();

    try {
      newPM.constructGraph(" ");
      fail("FileNotFoundException should have happened");
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
    } catch (ParseException e) {
    }
  }

  /**
   * This method tests if we can successfully get all packages in graph
   */
  @Test
  void test002_getAllPackages() {

    // Get the valid results from valid.json
    Set<String> valid = new HashSet<String>();
    valid.add("A");
    valid.add("B");
    valid.add("C");
    valid.add("D");
    valid.add("E");

    newPM = new PackageManager();

    try {
      newPM.constructGraph("valid.json");

      if (!valid.equals(newPM.getAllPackages()))
        fail("The result does not match the exceptation");
    } catch (Exception e) {
      fail("Unexpected exception occurs");
    }
  }

  /**
   * This method tests if we can get correct installation order of a package
   */
  @Test
  void test003_getInstallationOrder_of_A() {

    // The expected valid result from valid.json
    List<String> valid = new ArrayList<String>();
    valid.add("C");
    valid.add("D");
    valid.add("B");
    valid.add("A");

    newPM = new PackageManager();

    try {
      newPM.constructGraph("valid.json");

      if (!newPM.getInstallationOrder("A").equals(valid))
        fail("The result does not match the exceptation");
    } catch (Exception e) {
      fail("Unexpected exception occurs");
    }
  }

  /**
   * This method tests if toInstall method successfully works
   */
  @Test
  void test004_Test_toInstall() {

    // The expected valid result from valid.json
    List<String> valid = new ArrayList<String>();
    valid.add("A");

    try {
      newPM = new PackageManager();
      newPM.constructGraph("valid.json");


      if (!newPM.toInstall("A", "B").equals(valid))
        fail("The result does not match the exceptation");
    } catch (Exception e) {
      fail("Unexpected exception occurs");
    }
  }

  /**
   * This method tests if we can get correct installation order of all packages
   */
  @Test
  void test005_Test_getInstallationOrderForAllPackages() {

    // The expected valid result from valid.json
    List<String> valid = new ArrayList<String>();
    valid.add("C");
    valid.add("D");
    valid.add("B");
    valid.add("A");
    valid.add("E");

    try {
      newPM = new PackageManager();
      newPM.constructGraph("valid.json");

      if (!newPM.getInstallationOrderForAllPackages().equals(valid))
        fail("The result does not match the exceptation");
    } catch (Exception e) {
      fail("Unexpected exception occurs");
    }
  }

  /**
   * This method tests if we can find the package with maximum dependencies in
   * the graph
   */
  @Test
  void test006_Test_getPackageWithMaxDependencies() {
    try {
      newPM = new PackageManager();
      newPM.constructGraph("valid.json");

      // "A" is what we expect
      if (!newPM.getPackageWithMaxDependencies().equals("A"))
        fail("The result does not match the exceptation");
    } catch (Exception e) {
      fail("Unexpected exception occurs");
    }
  }

}
