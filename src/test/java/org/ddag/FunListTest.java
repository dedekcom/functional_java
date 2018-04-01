/*
 *
 * Dominik Dagiel 03.2018
 *
 */

package org.ddag;

import org.ddag.fun.Fumeric;
import org.ddag.fun.FunList;
import org.ddag.fun.FunObject;
import org.ddag.fun.FunTuple;
import org.ddag.fun.Tuple2;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FunListTest {

  @Test
  public void testBasicFunListOps() {

    FunList<Integer> i1 = FunList.of(1,2,5,-2,3,-10, 4);
    assertTrue( i1.fold(0, (acc, id) -> acc + id) == 3);
    assertTrue ( i1.sum().intValue() == 3);
    assertTrue (i1.partition(e -> e > 0)._2().equals(FunList.of(-2, -10)));

    assertTrue (i1.filter(e -> e > 0).sorted().map(e -> String.valueOf(e) + "k").equals(
            FunList.of("1k", "2k", "3k", "4k", "5k")));

    FunList<String> sl = FunList.of("a", "b", "2", "3", "d", "b", "2");

    assertEquals(sl.find(e -> e.equals("d")), Optional.of("d"));
    assertTrue(sl.exists(e -> e.equalsIgnoreCase("D")));
    assertTrue(sl.distinct().equals(FunList.of("a", "b", "2", "3", "d")));
    int sum2 = sl.foldLeft(0, (acc, id) ->
            Fumeric.getInteger(id).map(intVal -> acc + intVal).orElse(acc)
    );
    assertTrue(sum2 == 7);
    assertTrue (sl.pushed("new").head().equals("new"));
    assertTrue (sl.tail().zipWithIndex().head().equals(new Tuple2<>("b", 0)));
    assertTrue(FunList.of(2).tail().isEmpty());

    FunList<Object> l2 = FunList.of(1, 2, Optional.of(5), Optional.empty(), FunList.of(1, 2, 3), FunList.of(new FunList<String>()));
    assertTrue(l2.flatten().splitAt(100)._1().equals(
            FunList.of(1, 2, 5, 1, 2, 3, new FunList<String>())
    ));

    assertEquals(l2.pushed("x").mPushed(Optional.of("x")).map( e -> {
      if (FunObject.matchesObject(e, Integer.class))              return (Integer)e;
      else if (FunObject.matchesObject(e, Optional.empty()))      return -10;
      else if (FunObject.matchesOptionalOf(e, Integer.class))     return (Integer)(((Optional)e).get());
      else if (FunObject.matchesObject(e, FunList.class, 1, FunList.class))         return -3;
      else if (FunObject.matchesObject(e, FunList.class))         return -1;
      else if (FunObject.matchesObject(e, "x"))          return 100;
      else if (FunObject.matchesOptionalOf(e, "x"))         return -100;
      else return 0;
    }), FunList.of(-100, 100, 1, 2, 5, -10, -3, -1) );

    assertTrue (l2.drop(20).isEmpty());
    assertTrue (l2.drop(3).size() == 3);
    assertTrue (l2.slice(-5, 100).size() == l2.size());

    l2.mkString("---").print();

    assertTrue(FunList.ofSize(5, 0).mapWithIndex((el, id) -> id).equals(FunList.of(0, 1, 2, 3, 4)));

  }

  @Test
  public void testFunListMatches() {
    FunList<String> sl = FunList.of("a", "b", "2", "3", "d", "b", "2");

    assertTrue (sl.matches(FunList.class)) ;
    assertTrue (sl.matches(FunList.class, String.class, FunList.class));
    assertTrue (sl.matches(FunList.class, FunList.class));
    assertTrue (sl.matches(FunList.class, "a", "b", FunList.class));
    assertTrue (!sl.matches(FunList.class, "a", "b", FunList.of()));

    assertTrue (FunList.of(1).tail().matches(FunList.class, FunList.of()));

    assertTrue (FunList.of(1).matches(FunList.class, 1, FunList.of()));

    assertTrue(FunList.of(1, 2, "3", new Tuple2<>("a", 5)).
            matches(FunList.class, Integer.class, Integer.class, String.class, FunTuple.class, FunList.of()));
  }

  @Test
  public void testFunListPerformance() {
    FunList<String> src = FunList.ofSize(100000, "").mapWithIndex((e, id) -> Integer.toString(id));

    int loops = 10;
    Performance.testPerform("\n\nPrepare FunList performance test", loops, () -> { ; });
    Performance.testPerform("reversed.sorted", loops, () -> { new FunList<>(src).reversed().sorted(); });
    Performance.testPerform("mReversed.mSortWith", loops, () -> { new FunList<>(src).mReversed().mSortWith((e1, e2) -> e1.compareTo(e2)); });

    Performance.testPerform("\nslice(1/4-3/4)", loops, () -> { new FunList<>(src).slice(src.size()/4,
            3*src.size()/4); });

    Performance.testPerform("\nzipWithIndex.map.sum", loops, () -> { new FunList<>(src).zipWithIndex().map(t -> t._2()).sum(); });
    Performance.testPerform("mapWithIndex.sum", loops, () -> { new FunList<>(src).mapWithIndex((el, id) -> id).sum(); });
    Performance.testPerform("map.foldLeft", loops, () -> {
      new FunList<>(src).map(s -> Fumeric.getInteger(s).get()).foldLeft(0, (acc, e) -> acc + e);
    });

    Performance.testPerform("pattern matching on list", loops, () -> {
      new FunList<Object>(src).map(e -> {
        if (FunObject.matchesObject(e, Integer.class)) return (Integer) e;
        else if (FunObject.matchesObject(e, Optional.empty())) return -10;
        else if (FunObject.matchesOptionalOf(e, Integer.class)) return (Integer) (((Optional) e).get());
        else if (FunObject.matchesObject(e, FunList.class, 1, FunList.class)) return -3;
        else if (FunObject.matchesObject(e, FunList.class)) return -1;
        else if (FunObject.matchesObject(e, "x")) return 100;
        else if (FunObject.matchesOptionalOf(e, "x")) return -100;
        else return 0;
      });
    });

  }

}
