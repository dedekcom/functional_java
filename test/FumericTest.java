/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package test;

import functional.Fumeric;
import functional.FunList;

import java.util.Optional;

public class FumericTest {

  public static void testFumeric() {
    FunList<Number> l = FunList.of(3.14, 1, 0, 2);
    Number sum = l.sum();
    System.out.println(sum.getClass() + ", " + sum);
    testFumericPerformance();
  }

  public static void testFumericPerformance() {
    Performance.testPerform("\n\nPrepare fumeric performance test", 100000, () -> { ; });

    Performance.testPerform("Integer.parseInt(23883)", 10000000, () -> { int i = Integer.parseInt("23883"); i++; });
    Performance.testPerform("Fumeric.getInt(23883)", 10000000, () -> { Optional<Integer> iopt = Fumeric.getInteger("23883"); });

    Performance.testPerform("Long.parseLong(-00000000009676765767)", 10000000, () -> { long i = Long.parseLong("-0000000000967676576"); i++; });
    Performance.testPerform("Fumeric.getLong(-00000000009676765767)", 10000000, () -> { Optional<Long> iopt = Fumeric.getLong("-0000000000967676576"); });

    Performance.testPerform("Integer.parseInt(d)", 10000000, () -> {
      try {
        int i = Integer.parseInt("d");
        i++;
      } catch (Exception e) {
      }
    });
    Performance.testPerform("Fumeric.getInt(d)", 10000000, () -> { Optional<Integer> iopt = Fumeric.getInteger("d"); });

  }

}
