/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.ddag.fun.col.FunLinkedList.Nil;
import static org.ddag.fun.match.FunMatch.matches;
import static org.ddag.fun.match.FunMatch.matchesOptOf;
import static org.ddag.fun.match.FunMatch.getIf;
import static org.ddag.fun.match.FunMatch.runIf;
import static org.ddag.fun.match.FunMatch.match;
import static org.ddag.fun.match.FunMatch.Case;
import static org.ddag.fun.FunObject.Any;

import org.ddag.fun.col.FunLinkedList;
import org.ddag.fun.FunString;
import org.ddag.fun.col.FunList;
import org.ddag.fun.match.FunMatchException;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class FunMatchTest {

  @Test
  public void testMatch() {
    String s = match(5,
            Case(Integer.class), (o) -> "is int",
            Case(Any), (o) -> "any"
    );
    assertEquals (s, "is int");

    FunList<Integer> list = FunList.of(1, 2, 3, 4, 5);
    assertEquals("head::tail",
            match( list,
                    Case(5), (o) -> "5",
                    Case(FunString.class), (o) -> "fun string",
                    Case(1, 2, Nil), (o) -> "1::2::Nil",
                    Case(Any, FunList.class), (o) -> "head::tail"
            )
    );

    Double d = 0.0;
    int res = match(d,
            Case(1.0), (o) -> o.intValue() + 1,
            Case(2.0), (o) -> o.intValue() + 2,
            Case(Any), (o) -> 0
      );
    assertEquals(0, res);
  }

  @Test (expected = FunMatchException.class)
  public void testMatchException() {
    match(0.0,
            Case(1.0), (o) -> o.intValue() + 1,
            Case(2.0), (o) -> o.intValue() + 2
    );
  }

  @Test
  public void testMatch2() {

    int res = match( 0.0,
            getIf((dd) -> dd.intValue() + 1, 1.0),
            getIf((dd) -> dd.intValue() + 2, 2.0),
            getIf((dd) -> 0, Any)
    );
    assertEquals(0, res);
  }

  @Test
  public void testMatchPerformance() {
    FunLinkedList<String> src = FunList.ofSize(100000, "").mapWithIndex((e, id) -> Integer.toString(id));

    int loops = 10;
    Performance.testPerform("pattern matching based on if", loops, () -> {
      new FunLinkedList<Object>(src).map(e -> {
        if (matches(e, Integer.class)) return (Integer) e;
        else if (matches(e, Optional.empty())) return -10;
        else if (matchesOptOf(e, Integer.class)) return (Integer) (((Optional) e).get());
        else if (matches(e, 1, FunLinkedList.class)) return -3;
        else if (matches(e, FunLinkedList.class)) return -1;
        else if (matches(e, "x")) return 100;
        else if (matchesOptOf(e, "x")) return -100;
        else return 0;
      });
    });

    Performance.testPerform("pattern matching based on getIf", loops, () -> {
      new FunLinkedList<>(src).map(e ->
              match(e,
                      getIf ( i -> i,                             Integer.class),
                      getIf ( i -> -10,                           Optional.empty()),
                      getIf ( o -> -2,        Optional.class, Integer.class),
                      getIf ( i -> -3,                   FunLinkedList.class),
                      getIf ( l -> -1,                            FunLinkedList.class),
                      getIf ( s -> 100,                 "x"),
                      getIf ( sopt -> -100,              Optional.class, "x"),
                      getIf ( a -> 0,                             Any))
      );
    });

    Performance.testPerform("pattern matching based on runIf", loops, () -> {
      new FunLinkedList<>(src).forEach(e ->
              match(e,
                      runIf ( i -> { int len = i.length();},                             Integer.class),
                      runIf ( i -> {},                           Optional.empty()),
                      runIf ( o -> {},        Optional.class, Integer.class),
                      runIf ( i -> {},                   FunLinkedList.class),
                      runIf ( l -> {},                            FunLinkedList.class),
                      runIf ( s -> {},                 "x"),
                      runIf ( sopt -> {},              Optional.class, "x"),
                      runIf ( a -> {},                             Any))
      );
    });

    Performance.testPerform("pattern matching based on Case", loops, () -> {
      new FunLinkedList<>(src).map(e ->
              match(e,
                      Case (Integer.class),                 o -> o,
                      Case (Optional.empty()),              o -> -10,
                      Case (Optional.class, Integer.class), o -> -30,
                      Case (1, FunLinkedList.class), o -> -3,
                      Case (FunLinkedList.class), o -> -1,
                      Case ("x"),                 o -> 100,
                      Case (Optional.class, "x"),  o -> -100,
                      Case (Any),                           o -> 0
              )
      );
    });
  }

  @Test
  public void testNulls() {
    FunList<String> ls = FunList.of(null, "5", "z", null);
    FunList<String> lsll = ls.toFunLinkedList();
    FunList<String> lsual = ls.toUnmodifArrayList();

    assertEquals(FunList.of("5", "z", null), ls.removed(null));
    assertEquals(FunList.of("a", null, "5", "z", null), ls.pushed("a"));
    assertEquals(FunList.of(null, "5", "z", null, null), ls.added(null));
    assertTrue(ls.matches(null, FunList.class));
    assertTrue(ls.matches(null, "5", "z", null, Nil));
    assertFalse(ls.matches(null, null, FunList.class));

    assertEquals(FunList.of("5", "z", null), lsll.removed(null));
    assertEquals(FunList.of("a", null, "5", "z", null), lsll.pushed("a"));
    assertEquals(FunList.of(null, "5", "z", null, null), lsll.added(null));
    assertTrue(lsll.matches(null, FunList.class));
    assertTrue(lsll.matches(null, "5", "z", null, Nil));
    assertFalse(lsll.matches(null, null, FunList.class));

    assertEquals(FunList.of("5", "z", null), lsual.removed(null));
    assertEquals(FunList.of("a", null, "5", "z", null), lsual.pushed("a"));
    assertEquals(FunList.of(null, "5", "z", null, null), lsual.added(null));
    assertTrue(lsual.matches(null, FunList.class));
    assertTrue(lsual.matches(null, "5", "z", null, Nil));
    assertFalse(lsual.matches(null, null, FunList.class));
  }


}
