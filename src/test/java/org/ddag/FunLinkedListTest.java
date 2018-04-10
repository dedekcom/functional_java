/*
 *
 * Dominik Dagiel 03.2018
 *
 */

package org.ddag;

import org.ddag.fun.Fumeric;
import org.ddag.fun.col.FunLinkedList;
import static org.ddag.fun.col.FunLinkedList.Nil;
import org.ddag.fun.FunString;
import org.ddag.fun.col.FunList;
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

public class FunLinkedListTest {

  @Test
  public void testBasicFunListOps() {

    FunLinkedList<Integer> i1 = FunLinkedList.of(1,2,5,-2,3,-10, 4);
    assertTrue( i1.foldLeft(0, (acc, id) -> acc + id) == 3);
    assertTrue ( i1.sum().intValue() == 3);
    assertTrue (i1.partition(e -> e > 0)._2().equals(FunLinkedList.of(-2, -10)));

    assertTrue (i1.filter(e -> e > 0).sorted().map(e -> String.valueOf(e) + "k").equals(
            FunLinkedList.of("1k", "2k", "3k", "4k", "5k")));

    FunLinkedList<String> sl = FunLinkedList.of("a", "b", "2", "3", "d", "b", "2");

    assertEquals(sl.count(el -> new FunString(el).getInteger().isPresent()), 3);

    assertEquals(sl.find(e -> e.equals("d")), Optional.of("d"));
    assertTrue(sl.exists(e -> e.equalsIgnoreCase("D")));
    assertTrue(sl.distinct().equals(FunLinkedList.of("a", "b", "2", "3", "d")));
    int sum2 = sl.foldLeft(0, (acc, id) ->
            Fumeric.getInteger(id).map(intVal -> acc + intVal).orElse(acc)
    );
    assertTrue(sum2 == 7);
    assertTrue (sl.pushed("new").head().equals("new"));
    assertTrue (sl.tail().zipWithIndex().head().equals(new Tuple2<>("b", 0)));
    assertTrue(FunLinkedList.of(2).tail().isEmpty());

    FunLinkedList<Object> l2 = FunLinkedList.of(1, 2, Optional.of(5), Optional.empty(), FunLinkedList.of(1, 2, 3), FunLinkedList.of(new FunLinkedList<String>()));
    assertEquals(FunLinkedList.of(1, 2, 5, 1, 2, 3, new FunLinkedList<String>()),
            l2.flatten().splitAt(100)._1());

    assertEquals(FunLinkedList.of(1,2,3,4,5,6,7,8), FunLinkedList.of(1, 2, 3, 4, Optional.empty(),
            Optional.of(5), FunLinkedList.of(6,7,8)).flatten());

    assertEquals(l2.pushed("x").mPushed(Optional.of("x")).map( e -> {
      if (matches(e, Integer.class))              return (Integer)e;
      else if (matches(e, Optional.empty()))      return -10;
      else if (matchesOptOf(e, Integer.class))     return (Integer)(((Optional)e).get());
      else if (matches(e, 1, FunLinkedList.class))         return -3;
      else if (matches(e, FunLinkedList.class))         return -1;
      else if (matches(e, "x"))          return 100;
      else if (matchesOptOf(e, "x"))         return -100;
      else return 0;
    }), FunLinkedList.of(-100, 100, 1, 2, 5, -10, -3, -1) );

    assertEquals(l2.pushed("x").mPushed(Optional.of("x")).map( e -> {
      if (matches(e, Integer.class))              return (Integer)e;
      else if (matches(e, Optional.empty()))      return -10;
      else if (matches(e, Optional.class, Integer.class))     return (Integer)(((Optional)e).get());
      else if (matches(e, 1, FunLinkedList.class))         return -3;
      else if (matches(e, FunLinkedList.class))         return -1;
      else if (matches(e, "x"))          return 100;
      else if (matches(e, Optional.class, "x"))      return -100;
      else return 0;
    }), FunLinkedList.of(-100, 100, 1, 2, 5, -10, -3, -1) );

    assertTrue (l2.drop(20).isEmpty());
    assertTrue (l2.drop(3).size() == 3);
    assertTrue (l2.slice(-5, 100).size() == l2.size());
    assertEquals(FunLinkedList.of(1,2,3,4).drop(2), FunLinkedList.of(3,4));
    assertEquals(FunLinkedList.of(1,2,3,4).take(2), FunLinkedList.of(1, 2));
    assertEquals(FunLinkedList.of(1,2,3,4).takeRight(2), FunLinkedList.of(3,4));
    assertTrue(FunLinkedList.of().slice(1,2).isEmpty());

    assertEquals(FunLinkedList.of("1","2","3").mkFunString("---").get(), "1---2---3");
    assertEquals(FunLinkedList.of().mkString("---"), "");

    assertTrue(FunLinkedList.ofSize(5, 0).mapWithIndex((el, id) -> id).equals(FunLinkedList.of(0, 1, 2, 3, 4)));

    assertEquals(FunLinkedList.of(1, 2, 3), FunLinkedList.of(3, 2, 1).reversed());

    assertEquals(FunLinkedList.of(T2(1, "a"), T2(2, "b")),  FunLinkedList.of(1, 2, 3).zip(FunLinkedList.of("a", "b")));
    assertEquals(FunLinkedList.of(T2(1, "a"), T2(2, "b")),  FunLinkedList.of(1, 2).zip(FunLinkedList.of("a", "b", "c")));
  }

  @Test
  public void testFunListMatches() {
    FunLinkedList<String> sl = FunLinkedList.of("a", "b", "2", "3", "d", "b", "2");

    assertTrue (sl.matches(FunLinkedList.class)) ;
    assertTrue (sl.matches(String.class, FunLinkedList.class));
    assertTrue (sl.matches(FunLinkedList.class));
    assertTrue (sl.matches("a", "b", FunLinkedList.class));
    assertTrue (!sl.matches("a", "b", Nil));

    assertTrue(matches(Optional.empty(), Optional.empty()));

    assertTrue (FunLinkedList.of(1).tail().matches(Nil));

    assertTrue (FunLinkedList.of(1).matches(1, Nil));

    assertTrue(FunLinkedList.of(1, 2, "3", new Tuple2<>("a", 5)).
            matches(Integer.class, Integer.class, String.class, FunTuple.class, Nil));

    assertTrue(FunLinkedList.of(1, 2, "3", new Tuple2<>("a", 5)).
            matches(Integer.class, Integer.class, String.class, FunTuple.class, FunLinkedList.class));

    FunLinkedList<String> el = new FunLinkedList<>();
    String s = match(el, o -> {
      if ( matches(o, "x", Nil)) return "h::Nil";
      else if ( matches(o, "x", FunLinkedList.class)) return "h::tail";
      else if ( matches(o, Nil)) return "Nil";
      else return "unknown";
      } );
    assertEquals(s, "Nil");

    String s2 = match(FunLinkedList.of("x"), o -> {
      if ( matches(o, Nil)) return "Nil";
      else if ( matches(o, "x", FunLinkedList.class)) return "h::tail";
      else return "unknown";
    } );
    assertEquals(s2, "h::tail");

    String s3 = match( FunLinkedList.of("x", 2), o -> {
      if ( matches(o, "x", Nil)) return "h::Nil";
      else if ( matches(o, "x", FunLinkedList.class)) return "h::tail";
      else if ( matches(o, Nil)) return "Nil";
      else return "unknown";
    } );
    assertEquals(s3, "h::tail");

    assertTrue(FunLinkedList.of(1, 2, 3, 4).matches(FunLinkedList.of(2,3,4).pushed(1)));

    assertEquals(FunLinkedList.of(), Nil);
  }

  @Test
  public void testSharedListMatch() {
    FunSharedList<String> sl = FunLinkedList.of("a", "b", "2", "3", "d", "b", "2").toSharedList();

    assertTrue (sl.matches(List.class)) ;
    assertTrue (sl.matches(String.class, List.class));
    assertTrue (sl.matches("a", "b", List.class));
    assertTrue (!sl.matches("a", "b", Nil));

    assertTrue(matches(Optional.empty(), Optional.empty()));

    assertTrue (FunLinkedList.of(1).toSharedList().tail().matches(Nil));

    assertTrue (FunLinkedList.of(1).toSharedList().matches(1, Nil));

    assertTrue(FunLinkedList.of(1, 2, "3", new Tuple2<>("a", 5)).toSharedList().
            matches(Integer.class, Integer.class, String.class, FunTuple.class, Nil));

    assertTrue(FunLinkedList.of(1, 2, "3", new Tuple2<>("a", 5)).toSharedList().
            matches(Integer.class, Integer.class, String.class, FunTuple.class, List.class));

    FunLinkedList<String> el = new FunLinkedList<>();
    String s = match(el.toSharedList(), o -> {
      if ( matches(o, "x", Nil)) return "h::Nil";
      else if ( matches(o, "x", FunLinkedList.class)) return "h::tail";
      else if ( matches(o, Nil)) return "Nil";
      else return "unknown";
    } );
    assertEquals(s, "Nil");

    String s2 = match(FunLinkedList.of("x").toSharedList(), o -> {
      if ( matches(o, Nil)) return "Nil";
      else if ( matches(o, "x", List.class)) return "h::tail";
      else return "unknown";
    } );
    assertEquals(s2, "h::tail");

    String s3 = match( FunLinkedList.of("x", 2).toSharedList(), o -> {
      if ( matches(o, "x", Nil)) return "h::Nil";
      else if ( matches(o, "x", FunSharedList.class)) return "h::tail";
      else if ( matches(o, Nil)) return "Nil";
      else return "unknown";
    } );
    assertEquals(s3, "h::tail");

    assertTrue(FunLinkedList.of(1, 2, 3, 4).toSharedList().matches(FunLinkedList.of(2,3,4).pushed(1)));

  }

  @Test
  public void testFunListPerformance() {
    FunLinkedList<String> src = FunLinkedList.ofSize(100000, "").mapWithIndex((e, id) -> Integer.toString(id));

    int loops = 10;
    Performance.testPerform("\n\nPrepare FunList performance test", loops, () -> { ; });
    Performance.testPerform("reversed.sorted", loops, () -> { new FunLinkedList<>(src).reversed().sorted(); });
    Performance.testPerform("mReversed.mSortWith", loops, () -> { new FunLinkedList<>(src).mReversed().mSortWith((e1, e2) -> e1.compareTo(e2)); });

    Performance.testPerform("flatten", loops, () -> { new FunLinkedList<>(src).flatten(); });

    Performance.testPerform("\nslice(1/4-3/4)", loops, () -> { new FunLinkedList<>(src).slice(src.size()/4,
            3*src.size()/4); });

    Performance.testPerform("\nzipWithIndex.map.sum", loops, () -> { new FunLinkedList<>(src).zipWithIndex().map(t -> t._2()).sum(); });
    Performance.testPerform("mapWithIndex.sum", loops, () -> { new FunLinkedList<>(src).mapWithIndex((el, id) -> id).sum(); });
    Performance.testPerform("map.foldLeft", loops, () -> {
      new FunLinkedList<>(src).map(s -> Fumeric.getInteger(s).get()).foldLeft(0, (acc, e) -> acc + e);
    });

    Performance.testPerform("tail recursive", loops, () -> {
        tailRec(0, new FunLinkedList<>(src).toSharedList(),
              (sum, col) -> col.isEmpty() ? Return(sum) : Continue(Integer.parseInt(col.head()) + sum, col.tail()) );
    });

  }

  @Test
  public void testFunSubList() {
    FunLinkedList<Integer> list = FunLinkedList.of(1, 2, 3, 4, 5);

    assertEquals(list.head(), list.toSharedList().head());
    assertEquals(list.tail(), list.toSharedList().tail());
    assertEquals(list.sum(), sum(list.toSharedList(), 0));
  }

  private int sum(FunList<Integer> list, int sum) {
    return list.isEmpty() ? sum : sum(list.tail(), sum + list.head());
  }
}
