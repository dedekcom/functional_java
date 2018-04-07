/*
 *
 * Dominik Dagiel 03.2018
 *
 */

package org.ddag;

import org.ddag.fun.Fumeric;
import org.ddag.fun.col.FunList;
import static org.ddag.fun.col.FunList.Nil;
import org.ddag.fun.FunString;
import org.ddag.fun.col.FunSharedList;
import org.ddag.fun.tuple.FunTuple;

import static org.ddag.fun.func.TailRecursive.Continue;
import static org.ddag.fun.func.TailRecursive.Return;
import static org.ddag.fun.func.TailRecursive.tailRec;
import static org.ddag.fun.tuple.FunTuple.T2;
import org.ddag.fun.tuple.Tuple2;
import org.junit.Test;
import static org.ddag.fun.match.FunMatch.match;
import static org.ddag.fun.match.FunMatch.matches;
import static org.ddag.fun.match.FunMatch.matchesOptOf;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FunListTest {

  @Test
  public void testBasicFunListOps() {

    FunList<Integer> i1 = FunList.of(1,2,5,-2,3,-10, 4);
    assertTrue( i1.foldLeft(0, (acc, id) -> acc + id) == 3);
    assertTrue ( i1.sum().intValue() == 3);
    assertTrue (i1.partition(e -> e > 0)._2().equals(FunList.of(-2, -10)));

    assertTrue (i1.filter(e -> e > 0).sorted().map(e -> String.valueOf(e) + "k").equals(
            FunList.of("1k", "2k", "3k", "4k", "5k")));

    FunList<String> sl = FunList.of("a", "b", "2", "3", "d", "b", "2");

    assertEquals(sl.count(el -> new FunString(el).getInteger().isPresent()), 3);

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
    assertEquals(FunList.of(1, 2, 5, 1, 2, 3, new FunList<String>()),
            l2.flatten().splitAt(100)._1());

    assertEquals(FunList.of(1,2,3,4,5,6,7,8), FunList.of(1, 2, 3, 4, Optional.empty(),
            Optional.of(5), FunList.of(6,7,8)).flatten());

    assertEquals(l2.pushed("x").mPushed(Optional.of("x")).map( e -> {
      if (matches(e, Integer.class))              return (Integer)e;
      else if (matches(e, Optional.empty()))      return -10;
      else if (matchesOptOf(e, Integer.class))     return (Integer)(((Optional)e).get());
      else if (matches(e, 1, FunList.class))         return -3;
      else if (matches(e, FunList.class))         return -1;
      else if (matches(e, "x"))          return 100;
      else if (matchesOptOf(e, "x"))         return -100;
      else return 0;
    }), FunList.of(-100, 100, 1, 2, 5, -10, -3, -1) );

    assertEquals(l2.pushed("x").mPushed(Optional.of("x")).map( e -> {
      if (matches(e, Integer.class))              return (Integer)e;
      else if (matches(e, Optional.empty()))      return -10;
      else if (matches(e, Optional.class, Integer.class))     return (Integer)(((Optional)e).get());
      else if (matches(e, 1, FunList.class))         return -3;
      else if (matches(e, FunList.class))         return -1;
      else if (matches(e, "x"))          return 100;
      else if (matches(e, Optional.class, "x"))      return -100;
      else return 0;
    }), FunList.of(-100, 100, 1, 2, 5, -10, -3, -1) );

    assertTrue (l2.drop(20).isEmpty());
    assertTrue (l2.drop(3).size() == 3);
    assertTrue (l2.slice(-5, 100).size() == l2.size());
    assertEquals(FunList.of(1,2,3,4).drop(2), FunList.of(3,4));
    assertEquals(FunList.of(1,2,3,4).take(2), FunList.of(1, 2));
    assertEquals(FunList.of(1,2,3,4).takeRight(2), FunList.of(3,4));
    assertTrue(FunList.of().slice(1,2).isEmpty());

    assertEquals(FunList.of("1","2","3").mkFunString("---").get(), "1---2---3");
    assertEquals(FunList.of().mkString("---"), "");

    assertTrue(FunList.ofSize(5, 0).mapWithIndex((el, id) -> id).equals(FunList.of(0, 1, 2, 3, 4)));

    assertEquals(FunList.of(1, 2, 3), FunList.of(3, 2, 1).reversed());

    assertEquals(FunList.of(T2(1, "a"), T2(2, "b")),  FunList.of(1, 2, 3).zip(FunList.of("a", "b")));
    assertEquals(FunList.of(T2(1, "a"), T2(2, "b")),  FunList.of(1, 2).zip(FunList.of("a", "b", "c")));
  }

  @Test
  public void testFunListMatches() {
    FunList<String> sl = FunList.of("a", "b", "2", "3", "d", "b", "2");

    assertTrue (sl.matches(FunList.class)) ;
    assertTrue (sl.matches(String.class, FunList.class));
    assertTrue (sl.matches(FunList.class));
    assertTrue (sl.matches("a", "b", FunList.class));
    assertTrue (!sl.matches("a", "b", Nil));

    assertTrue(matches(Optional.empty(), Optional.empty()));

    assertTrue (FunList.of(1).tail().matches(Nil));

    assertTrue (FunList.of(1).matches(1, Nil));

    assertTrue(FunList.of(1, 2, "3", new Tuple2<>("a", 5)).
            matches(Integer.class, Integer.class, String.class, FunTuple.class, Nil));

    assertTrue(FunList.of(1, 2, "3", new Tuple2<>("a", 5)).
            matches(Integer.class, Integer.class, String.class, FunTuple.class, FunList.class));

    FunList<String> el = new FunList<>();
    String s = match(el, o -> {
      if ( matches(o, "x", Nil)) return "h::Nil";
      else if ( matches(o, "x", FunList.class)) return "h::tail";
      else if ( matches(o, Nil)) return "Nil";
      else return "unknown";
      } );
    assertEquals(s, "Nil");

    String s2 = match(FunList.of("x"), o -> {
      if ( matches(o, Nil)) return "Nil";
      else if ( matches(o, "x", FunList.class)) return "h::tail";
      else return "unknown";
    } );
    assertEquals(s2, "h::tail");

    String s3 = match( FunList.of("x", 2), o -> {
      if ( matches(o, "x", Nil)) return "h::Nil";
      else if ( matches(o, "x", FunList.class)) return "h::tail";
      else if ( matches(o, Nil)) return "Nil";
      else return "unknown";
    } );
    assertEquals(s3, "h::tail");

    assertTrue(FunList.of(1, 2, 3, 4).matches(FunList.of(2,3,4).pushed(1)));

    assertEquals(FunList.of(), Nil);
  }

  @Test
  public void testSharedListMatch() {
    FunSharedList<String> sl = FunList.of("a", "b", "2", "3", "d", "b", "2").toSharedList();

    assertTrue (sl.matches(List.class)) ;
    assertTrue (sl.matches(String.class, List.class));
    assertTrue (sl.matches("a", "b", List.class));
    assertTrue (!sl.matches("a", "b", Nil));

    assertTrue(matches(Optional.empty(), Optional.empty()));

    assertTrue (FunList.of(1).toSharedList().tail().matches(Nil));

    assertTrue (FunList.of(1).toSharedList().matches(1, Nil));

    assertTrue(FunList.of(1, 2, "3", new Tuple2<>("a", 5)).toSharedList().
            matches(Integer.class, Integer.class, String.class, FunTuple.class, Nil));

    assertTrue(FunList.of(1, 2, "3", new Tuple2<>("a", 5)).toSharedList().
            matches(Integer.class, Integer.class, String.class, FunTuple.class, List.class));

    FunList<String> el = new FunList<>();
    String s = match(el.toSharedList(), o -> {
      if ( matches(o, "x", Nil)) return "h::Nil";
      else if ( matches(o, "x", FunList.class)) return "h::tail";
      else if ( matches(o, Nil)) return "Nil";
      else return "unknown";
    } );
    assertEquals(s, "Nil");

    String s2 = match(FunList.of("x").toSharedList(), o -> {
      if ( matches(o, Nil)) return "Nil";
      else if ( matches(o, "x", List.class)) return "h::tail";
      else return "unknown";
    } );
    assertEquals(s2, "h::tail");

    String s3 = match( FunList.of("x", 2).toSharedList(), o -> {
      if ( matches(o, "x", Nil)) return "h::Nil";
      else if ( matches(o, "x", FunSharedList.class)) return "h::tail";
      else if ( matches(o, Nil)) return "Nil";
      else return "unknown";
    } );
    assertEquals(s3, "h::tail");

    assertTrue(FunList.of(1, 2, 3, 4).toSharedList().matches(FunList.of(2,3,4).pushed(1)));

  }

  @Test
  public void testFunListPerformance() {
    FunList<String> src = FunList.ofSize(100000, "").mapWithIndex((e, id) -> Integer.toString(id));

    int loops = 10;
    Performance.testPerform("\n\nPrepare FunList performance test", loops, () -> { ; });
    Performance.testPerform("reversed.sorted", loops, () -> { new FunList<>(src).reversed().sorted(); });
    Performance.testPerform("mReversed.mSortWith", loops, () -> { new FunList<>(src).mReversed().mSortWith((e1, e2) -> e1.compareTo(e2)); });

    Performance.testPerform("flatten", loops, () -> { new FunList<>(src).flatten(); });

    Performance.testPerform("\nslice(1/4-3/4)", loops, () -> { new FunList<>(src).slice(src.size()/4,
            3*src.size()/4); });

    Performance.testPerform("\nzipWithIndex.map.sum", loops, () -> { new FunList<>(src).zipWithIndex().map(t -> t._2()).sum(); });
    Performance.testPerform("mapWithIndex.sum", loops, () -> { new FunList<>(src).mapWithIndex((el, id) -> id).sum(); });
    Performance.testPerform("map.foldLeft", loops, () -> {
      new FunList<>(src).map(s -> Fumeric.getInteger(s).get()).foldLeft(0, (acc, e) -> acc + e);
    });

    Performance.testPerform("tail recursive", loops, () -> {
        tailRec(0, new FunList<>(src).toSharedList(),
              (sum, col) -> col.isEmpty() ? Return(sum) : Continue(Integer.parseInt(col.head()) + sum, col.tail()) );
    });

  }

  @Test
  public void testFunSubList() {
    FunList<Integer> list = FunList.of(1, 2, 3, 4, 5);

    assertEquals(list.head(), list.toSharedList().head());
    assertEquals(list.tail(), list.toSharedList().tail().toFunList());
    assertEquals(list.sum(), sum(list.toSharedList(), 0));
  }

  private int sum(FunSharedList<Integer> list, int sum) {
    return list.isEmpty() ? sum : sum(list.tail(), sum + list.head());
  }
}
