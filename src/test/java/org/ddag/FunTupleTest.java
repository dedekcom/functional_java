/*
 *
 * Dominik Dagiel 03.2018
 *
 */
package org.ddag;

import org.ddag.fun.col.FunLinkedList;
import static org.ddag.fun.match.FunMatch.match;
import static org.ddag.fun.match.FunMatch.matches;
import org.ddag.fun.FunObject;
import org.ddag.fun.FunString;
import org.ddag.fun.tuple.FunTuple;
import org.ddag.fun.tuple.Tuple1;
import org.ddag.fun.tuple.Tuple2;
import org.ddag.fun.tuple.Tuple3;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
    FunLinkedList<FunTuple> list = FunLinkedList.of(new Tuple2<>(1, "a"), new Tuple1<>(2), new Tuple3<>(3, "c", "cc"),
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
    assertTrue(new Tuple3<>(1,2,3).matches(FunTuple.class, 1, Integer.class, FunObject.Any));
    assertTrue(! list.head().matches(String.class));

    assertEquals(match(new Tuple2<>(1, "a"), o -> {
      if (matches(o, FunTuple.class, 1, "a")) {
        return "t2 1-a";
      } else if (matches(o, Tuple1.class, Integer.class)) {
        return "single int";
      } else if (matches(o, Tuple2.class)) {
        return "other tuple2";
      } else return "unknown";
    }), "t2 1-a");

    assertEquals(match(new Tuple2<>(1, "a"), o -> {
      if (matches(o, FunTuple.class, 1, "b")) {
        return "t2 1-a";
      } else if (matches(o, Tuple1.class, Integer.class)) {
        return "single int";
      } else if (matches(o, Tuple2.class)) {
        return "other tuple2";
      } else return "unknown";
    }), "other tuple2");
  }
}
