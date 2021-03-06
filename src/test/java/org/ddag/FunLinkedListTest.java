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
import org.ddag.fun.col.FunUnmodifArrayList;
import org.ddag.fun.tuple.FunTuple;

import static org.ddag.fun.tuple.FunTuple.T2;
import org.ddag.fun.tuple.Tuple2;
import org.junit.Test;
import static org.ddag.fun.match.FunMatch.match;
import static org.ddag.fun.match.FunMatch.matches;
import static org.ddag.fun.match.FunMatch.matchesOptOf;
import static org.ddag.fun.match.FunMatch.getIf;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FunLinkedListTest {

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

    FunList<Object> l2 = FunList.of(1, 2, Optional.of(5), Optional.empty(), FunList.of(1, 2, 3), FunList.of(new FunLinkedList<String>()));
    assertEquals(FunList.of(1, 2, 5, 1, 2, 3, new FunLinkedList<String>()),
            l2.flatten().splitAt(100)._1());

    assertEquals(FunList.of(1,2,3,4,5,6,7,8), FunList.of(1, 2, 3, 4, Optional.empty(),
            Optional.of(5), FunList.of(6,7,8)).flatten());

    assertEquals(l2.toFunLinkedList().mPushed("x").mPushed(Optional.of("x")).map( e -> {
      if (matches(e, Integer.class))              return (Integer)e;
      else if (matches(e, Optional.empty()))      return -10;
      else if (matchesOptOf(e, Integer.class))     return (Integer)(((Optional)e).get());
      else if (matches(e, 1, FunList.class))         return -3;
      else if (matches(e, FunList.class))         return -1;
      else if (matches(e, "x"))          return 100;
      else if (matchesOptOf(e, "x"))         return -100;
      else return 0;
    }), FunList.of(-100, 100, 1, 2, 5, -10, -3, -1) );

    assertEquals(l2.pushed("x").toFunLinkedList().mPushed(Optional.of("x")).map( e -> {
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

    assertEquals(FunList.of("a", "b", "z").min(), "a");
    assertTrue(FunList.of(10, 3, 333, 2, 15).max().equals(333));

    assertEquals(FunList.of(1,2,3,4,6).collect(
            getIf(e -> String.valueOf(e), 2),
            getIf(e -> "four", 4),
            getIf(e -> "nod", e -> e%2 != 0)
    ), FunList.of("nod", "2", "nod", "four"));

    assertEquals(FunList.of(1, 2), FunList.of(1,2,3,4,5).takeWhile( i -> i < 3));
    assertEquals(FunList.of(3,4,5), FunList.of(1,2,3,4,5).dropWhile( i -> i < 3));
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

    FunLinkedList<String> el = new FunLinkedList<>();
    String s = match(el, o -> {
      if ( matches(o, "x", Nil)) return "h::Nil";
      else if ( matches(o, "x", FunLinkedList.class)) return "h::tail";
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
    FunUnmodifArrayList<String> sl = FunList.of("a", "b", "2", "3", "d", "b", "2").toUnmodifArrayList();

    assertTrue (sl.matches(List.class)) ;
    assertTrue (sl.matches(String.class, List.class));
    assertTrue (sl.matches("a", "b", List.class));
    assertTrue (!sl.matches("a", "b", Nil));

    assertTrue(matches(Optional.empty(), Optional.empty()));

    assertTrue (FunList.of(1).toUnmodifArrayList().tail().matches(Nil));

    assertTrue (FunList.of(1).toUnmodifArrayList().matches(1, Nil));

    assertTrue(FunList.of(1, 2, "3", new Tuple2<>("a", 5)).toUnmodifArrayList().
            matches(Integer.class, Integer.class, String.class, FunTuple.class, Nil));

    assertTrue(FunList.of(1, 2, "3", new Tuple2<>("a", 5)).toUnmodifArrayList().
            matches(Integer.class, Integer.class, String.class, FunTuple.class, List.class));

    FunLinkedList<String> el = new FunLinkedList<>();
    String s = match(el.toUnmodifArrayList(), o -> {
      if ( matches(o, "x", Nil)) return "h::Nil";
      else if ( matches(o, "x", FunLinkedList.class)) return "h::tail";
      else if ( matches(o, Nil)) return "Nil";
      else return "unknown";
    } );
    assertEquals(s, "Nil");

    String s2 = match(FunList.of("x").toUnmodifArrayList(), o -> {
      if ( matches(o, Nil)) return "Nil";
      else if ( matches(o, "x", List.class)) return "h::tail";
      else return "unknown";
    } );
    assertEquals(s2, "h::tail");

    String s3 = match( FunList.of("x", 2).toUnmodifArrayList(), o -> {
      if ( matches(o, "x", Nil)) return "h::Nil";
      else if ( matches(o, "x", FunUnmodifArrayList.class)) return "h::tail";
      else if ( matches(o, Nil)) return "Nil";
      else return "unknown";
    } );
    assertEquals(s3, "h::tail");

    assertTrue(FunList.of(1, 2, 3, 4).toUnmodifArrayList().matches(FunList.of(2,3,4).pushed(1)));

  }

  @Test
  public void testFunSubList() {
    FunList<Integer> list = FunList.of(1, 2, 3, 4, 5);

    assertEquals(list.head(), list.toUnmodifArrayList().head());
    assertEquals(list.tail(), list.toUnmodifArrayList().tail());
    assertEquals(list.sum(), sum(list.toUnmodifArrayList(), 0));
  }

  private int sum(FunList<Integer> list, int sum) {
    return list.isEmpty() ? sum : sum(list.tail(), sum + list.head());
  }

  @Test(expected = NoSuchElementException.class)
  public void testLast() {
    FunList.of().toFunLinkedList().last();
  }

  @Test
  public void testSublists() {
    FunList<Integer> list = FunList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );
    FunList<Integer> list2 = list.toFunLinkedList();

    assertEquals(FunList.of(3, 4, 5, 6, 7, 8, 9, 10).toFunLinkedList(), list.toFunLinkedList().tail().tail());

    assertEquals(FunList.of(4, 5, 6), list.toFunLinkedList().tail().tail().subList(1,4));

    assertEquals(FunList.of(), list.toFunLinkedList().subList(8,8));

    assertEquals(list.toFunLinkedList().toString(), list.toString());

    assertEquals(list.toFunLinkedList().filterNot(p -> p >= 2), list.subList(0, 1));

    assertTrue(!list.toFunLinkedList().contains(0));

    assertTrue(list.toFunLinkedList().containsAll(FunList.of(4,5,6,7)));

    assertTrue(list.toFunLinkedList().indexOf(3) == 2);

    assertEquals(FunList.of(3,4,5,6), FunList.of(4, 5, 6).toFunLinkedList().pushed(3));

    assertEquals(FunList.of(3,5,6), FunList.of(3, 4, 5, 6).toFunLinkedList().removed(4));
    assertEquals(FunList.of(4,5,6), FunList.of(3, 4, 5, 6).toFunLinkedList().removed(3));
    assertEquals(FunList.of(3,4,5), FunList.of(3, 4, 5, 6).toFunLinkedList().removed(6));

    assertEquals(FunList.of(1, 2, 3,4,5), FunList.of(5,4,3,2,1).toFunLinkedList().reversed());

    assertEquals(list, list.toFunLinkedList().slice(0,list.size()-2).added(9).added(10));
    assertEquals(list2, list);

    FunLinkedList<Integer> l1 = FunList.of(1,2,3,4).toFunLinkedList();
    FunList<Integer> l2 = l1.added(5);
    FunList<Integer> l3 = l1.added(6);
    FunList<Integer> l4 = l1.addedCol(FunList.of(5,6,7));
    assertEquals(FunList.of(1,2,3,4), l1);
    assertEquals(FunList.of(1,2,3,4,5), l2);
    assertEquals(FunList.of(1,2,3,4,6), l3);
    assertEquals(FunList.of(1,2,3,4,5,6,7), l4);
    assertEquals(FunList.of(), l1.tail().tail().tail().tail());
  }
}
