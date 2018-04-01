/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag;

import org.ddag.fun.FunList;
import org.ddag.fun.FunString;
import org.ddag.fun.FunTuple;
import org.ddag.fun.Tuple1;
import org.ddag.fun.Tuple2;
import org.ddag.fun.Tuple3;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FunTupleTest {

  @Test
  public void testScalaTuple() {
    FunString fs = new FunString();
    Tuple2<String, Integer> tp1 = new Tuple2<>("dd", 8);
    Tuple2<String, Integer> tp2 = new Tuple2<>("dd", 8);
    Tuple3<String, Integer, Character> tp3 = new Tuple3<>("dd", 8, 'c');
    assertTrue (tp1.equals(tp2));
    assertTrue (!tp1.equals(tp3));
    tp2.swap().print();
  }

  @Test
  public void testTupleMatch() {
    FunList<FunTuple> list = FunList.of(new Tuple2<>(1, "a"), new Tuple1<>(2), new Tuple3<>(3, "c", "cc"),
            new Tuple2<>("4", 'd'), new Tuple1<>(10));
    list.map(e -> {
      if (e.matches(FunTuple.class, Integer.class, String.class)) {
        return "int-string";
      } else if (e.matches(Tuple1.class, Integer.class)) {
        return "single int";
      } else if (e.matches(Tuple2.class)) {
        return "other tuple2";
      } else return "unknown";
    }).print();

    assertTrue(new Tuple3<>(1,2,3).matches(FunTuple.class, 1, 2, 3));
    assertTrue(new Tuple3<>(1,2,3).matches(FunTuple.class, 1, Integer.class, Object.class));
    assertTrue(! list.head().matches(String.class));
  }
}
