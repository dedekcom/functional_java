/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag;

import org.ddag.fun.Fumeric;
import org.ddag.fun.col.FunLinkedList;
import org.ddag.fun.col.FunList;
import org.junit.Test;

import java.util.Optional;

public class FumericTest {

  @Test
  public void testBasicFumericOperations() {
    FunList<Number> l = FunList.of(3.14, 1, 0, 2);
    Number sum = l.sum();
    System.out.println(sum.getClass() + ", " + sum);
  }

  @Test
  public void testIntegerParserPerformance() {
    int loops = 1000000;
    Performance.testPerform("\n\nPrepare fumeric performance test", loops, () -> { ; });

    Performance.testPerform("Integer.parseInt(23883)", loops, () -> { int i = Integer.parseInt("23883"); });
    Performance.testPerform("Fumeric.getInt(23883)", loops, () -> { Optional<Integer> iopt = Fumeric.getInteger("23883"); });

    Performance.testPerform("Long.parseLong(-00000000009676765767)", loops, () -> { long i = Long.parseLong("-0000000000967676576"); });
    Performance.testPerform("Fumeric.getLong(-00000000009676765767)", loops, () -> { Optional<Long> iopt = Fumeric.getLong("-0000000000967676576"); });

    Performance.testPerform("Integer.parseInt(d)", loops, () -> {
      try {
        int i = Integer.parseInt("d");
      } catch (Exception e) {
      }
    });
    Performance.testPerform("Fumeric.getInt(d)", loops, () -> { Optional<Integer> iopt = Fumeric.getInteger("d"); });

  }

  @Test
  public void testDoubleParserPerformance() {

    int loops = 1000000;
    System.out.println("\n\nDouble:");
    Performance.testPerform("Double.parseDouble(23883)", loops, () -> { double d = Double.parseDouble("23883"); });
    Performance.testPerform("Fumeric.getDouble(23883)", loops, () -> { Optional<Double> dopt = Fumeric.getDouble("23883"); });

    Performance.testPerform("Double.parseDouble(-00000.42424234242342)", loops, () -> { double d = Double.parseDouble("-00000.42424234242342"); });
    Performance.testPerform("Fumeric.getDouble(-00000.42424234242342)", loops, () -> { Optional<Double> dopt = Fumeric.getDouble("238-00000.4242423424234283"); });

    Performance.testPerform("Double.parseDouble(d)", loops, () -> {
      try {
        double d = Double.parseDouble("d");
      } catch (Exception e) {
      }
    });
    Performance.testPerform("Fumeric.getDouble(d)", loops, () -> { Optional<Double> dopt = Fumeric.getDouble("d"); });
  }

}
