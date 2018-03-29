/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package test;

import functional.FunList;

public class FumericTest {
  public static void testFumeric() {
    FunList<Number> l = FunList.of(3.14, 1, 0, 2);
    Number sum = l.sum();
    System.out.println(sum.getClass() + ", " + sum);
  }
}
