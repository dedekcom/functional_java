/*
 *
 * Dominik Dagiel 03.2018
 *
 */

import static test.FumericTest.testFumeric;
import static test.FunListTest.testScalaList;
import static test.FunMapTest.testScalaMap;
import static test.FunStringTest.testScalaString;
import static test.FunTupleTest.testScalaTuple;


public class MainApp {

  public static void main(String[] args) {
    testScalaList();
    testScalaMap();
    testScalaString();
    testScalaTuple();
    testFumeric();
  }

}
