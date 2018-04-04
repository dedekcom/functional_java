/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag;

import static org.ddag.fun.FunList.Nil;
import static org.ddag.fun.match.FunMatch.caseObject;
import static org.ddag.fun.match.FunMatch.caseOptOf;
import static org.ddag.fun.match.FunMatch.match;
import static org.ddag.fun.match.FunMatch.caseOf;
import static org.ddag.fun.match.FunMatch.Case;
import static org.ddag.fun.FunObject.Any;

import org.ddag.fun.FunList;
import org.ddag.fun.FunString;
import org.ddag.fun.match.FunMatchException;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class FunMatchTest {

  @Test
  public void testMatch() {
    Integer o = 5;
    String s = match(
            caseOf(o, Integer.class), () -> "is int",
            caseOf(o, Any), () -> "any"
    );
    assertEquals (s, "is int");

    FunList<Integer> list = FunList.of(1, 2, 3, 4, 5);
    assertEquals("head::tail",
            match(
                    caseOf(list, 5), () -> "5",
                    caseOf(list, FunString.class), () -> "fun string",
                    caseOf(list, 1, 2, Nil()), () -> "1::2::Nil",
                    caseOf(list, Any, FunList.class), () -> "head::tail"
            )
    );

    Double d = 0.0;
    int res = match(
            caseOf(d, 1.0), () -> d.intValue() + 1,
            caseOf(d, 2.0), () -> d.intValue() + 2,
            caseOf(d, Any), () -> 0
      );
    assertEquals(0, res);
  }

  @Test (expected = FunMatchException.class)
  public void testMatchException() {
    Double o = 0.0;
    match(
            caseOf(o, 1.0), () -> o.intValue() + 1,
            caseOf(o, 2.0), () -> o.intValue() + 2
    );
  }

  @Test
  public void testMatch2() {
    String s = match( 5,
            Case((i) -> "is int", Integer.class),
            Case((i) -> "any", Any)
      );
    assertEquals (s, "is int");

    assertEquals("head::tail",
            match( FunList.of(1, 2, 3, 4, 5),
                    Case((i) -> "5", 5),
                    Case((sl) -> "fun string", FunString.class),
                    Case((l) -> "1::2::Nil", 1, 2, Nil()),
                    Case((l) -> "head::tail", Any, FunList.class)
            )
    );

    int res = match( 0.0,
            Case((dd) -> dd.intValue() + 1, 1.0),
            Case((dd) -> dd.intValue() + 2, 2.0),
            Case((dd) -> 0, Any)
      );
    assertEquals(0, res);
  }

  @Test (expected = FunMatchException.class)
  public void testMatchException2() {
    match( 0.0,
            Case((d) -> d.intValue() + 1, 1.0),
            Case((d) -> d.intValue() + 2, 2.0)
    );
  }

  @Test
  public void testMatchPerformance() {
    FunList<String> src = FunList.ofSize(100000, "").mapWithIndex((e, id) -> Integer.toString(id));

    int loops = 10;
    Performance.testPerform("pattern matching based on if", loops, () -> {
      new FunList<Object>(src).map(e -> {
        if (caseObject(e, Integer.class)) return (Integer) e;
        else if (caseObject(e, Optional.empty())) return -10;
        else if (caseOptOf(e, Integer.class)) return (Integer) (((Optional) e).get());
        else if (caseObject(e, 1, FunList.class)) return -3;
        else if (caseObject(e, FunList.class)) return -1;
        else if (caseObject(e, "x")) return 100;
        else if (caseOptOf(e, "x")) return -100;
        else return 0;
      });
    });

    Performance.testPerform("pattern matching based on caseOf", loops, () -> {
      new FunList<Object>(src).map(e ->
        match(
          caseOf(e, Integer.class),                () -> (Integer)e,
          caseOf(e, Optional.empty()),             () -> -10,
          caseOf(e, Optional.class, Integer.class),() -> (Integer) (((Optional) e).get()),
          caseOf(e, 1, FunList.class),    () -> -3,
          caseOf(e, FunList.class),                () -> -1,
          caseOf(e, "x"),                 () -> 100,
          caseOf(e, Optional.class, "x"), () -> -100,
          caseOf(e, Any),                          () -> 0
        )
      );
    });

    Performance.testPerform("pattern matching based on Case", loops, () -> {
      new FunList<Object>(src).map(e ->
        match(e,
          Case( (i) -> i,                             Integer.class),
          Case( (i) -> -10,                           Optional.empty()),
          Case( (o) -> (((Optional) o).get()),        Optional.class, Integer.class),
          Case( (i) -> -3,                   1, FunList.class),
          Case( (l) -> -1,                            FunList.class),
          Case( (s) -> 100,                 "x"),
          Case( (sopt) -> -100,              Optional.class, "x"),
          Case( (a) -> 0,                             Any))
        );
    });
  }

}
