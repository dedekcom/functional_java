/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag;

import static org.ddag.fun.FunList.Nil;
import static org.ddag.fun.match.FunMatch.matches;
import static org.ddag.fun.match.FunMatch.matchesOptOf;
import static org.ddag.fun.match.FunMatch.doIf;
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
    String s = match(5,
            caseOf(Integer.class), (o) -> "is int",
            caseOf(Any), (o) -> "any"
    );
    assertEquals (s, "is int");

    FunList<Integer> list = FunList.of(1, 2, 3, 4, 5);
    assertEquals("head::tail",
            match( list,
                    caseOf(5), (o) -> "5",
                    caseOf(FunString.class), (o) -> "fun string",
                    caseOf(1, 2, Nil()), (o) -> "1::2::Nil",
                    caseOf(Any, FunList.class), (o) -> "head::tail"
            )
    );

    Double d = 0.0;
    int res = match(d,
            caseOf(1.0), (o) -> d.intValue() + 1,
            caseOf(2.0), (o) -> d.intValue() + 2,
            caseOf(Any), (o) -> 0
      );
    assertEquals(0, res);
  }

  @Test (expected = FunMatchException.class)
  public void testMatchException() {
    match(0.0,
            caseOf(1.0), (o) -> o.intValue() + 1,
            caseOf(2.0), (o) -> o.intValue() + 2
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

    res = match( 0.0,
            doIf((dd) -> (double)dd + 1, 1.0),
            doIf((dd) -> (double)dd + 2, 2.0),
            doIf((dd) -> 0, Any)
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
        if (matches(e, Integer.class)) return (Integer) e;
        else if (matches(e, Optional.empty())) return -10;
        else if (matchesOptOf(e, Integer.class)) return (Integer) (((Optional) e).get());
        else if (matches(e, 1, FunList.class)) return -3;
        else if (matches(e, FunList.class)) return -1;
        else if (matches(e, "x")) return 100;
        else if (matchesOptOf(e, "x")) return -100;
        else return 0;
      });
    });

    Performance.testPerform("pattern matching based on Case", loops, () -> {
      new FunList<Object>(src).map(e ->
        match(e,
          Case( (i) -> i,                             Integer.class),
          Case( (i) -> -10,                           Optional.empty()),
          Case( (o) -> (((Optional) o).get()),        Optional.class, Integer.class),
          Case( (i) -> -3,                   FunList.class),
          Case( (l) -> -1,                            FunList.class),
          Case( (s) -> 100,                 "x"),
          Case( (sopt) -> -100,              Optional.class, "x"),
          Case( (a) -> 0,                             Any))
        );
    });

    Performance.testPerform("pattern matching based on match-Object", loops, () -> {
      new FunList<Object>(src).map(e ->
              match(e,
                      doIf( (i) -> i,                             Integer.class),
                      doIf( (i) -> -10,                           Optional.empty()),
                      doIf( (o) -> (((Optional) o).get()),        Optional.class, Integer.class),
                      doIf( (i) -> -3,                   FunList.class),
                      doIf( (l) -> -1,                            FunList.class),
                      doIf( (s) -> 100,                 "x"),
                      doIf( (sopt) -> -100,              Optional.class, "x"),
                      doIf( (a) -> 0,                             Any))
      );
    });

    Performance.testPerform("pattern matching based on caseOf", loops, () -> {
      new FunList<Object>(src).map(e ->
              match(e,
                      caseOf(Integer.class),                (o) -> (Integer)o,
                      caseOf(Optional.empty()),             (o) -> -10,
                      caseOf(Optional.class, Integer.class),(o) -> (Integer) (((Optional) o).get()),
                      caseOf(1, FunList.class),    (o) -> -3,
                      caseOf(FunList.class),                (o) -> -1,
                      caseOf("x"),                 (o) -> 100,
                      caseOf(Optional.class, "x"), (o) -> -100,
                      caseOf(Any),                          (o) -> 0
              )
      );
    });
  }

}
