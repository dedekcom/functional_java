/*
 *
 * Dominik Dagiel 03.2018
 *
 */

import static test.FumericTest.testFumeric;
import static test.FunListTest.testFunList;
import static test.FunMapTest.testScalaMap;
import static test.FunStringTest.testScalaString;
import static test.FunTupleTest.testScalaTuple;


public class MainApp {

  public static void main(String[] args) {
    testFunList();
    testScalaMap();
    testScalaString();
    testScalaTuple();
    testFumeric();
  }

}
