/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag;

import static org.ddag.fun.FunList.Nil;
import static org.ddag.fun.match.FunMatch.match;
import static org.ddag.fun.match.FunMatch.caseOf;
import static org.ddag.fun.FunObject.Any;

import org.ddag.fun.FunList;
import org.ddag.fun.FunString;
import org.ddag.fun.match.FunMatchException;
import org.junit.Test;
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
                    caseOf(list, FunList.class, 1, 2, Nil), () -> "1::2::Nil",
                    caseOf(list, FunList.class, Any, FunList.class), () -> "head::tail"
            )
    );
  }

  @Test (expected = FunMatchException.class)
  public void testMatchException() {
    Double o = 0.0;
    match(
            caseOf(o, 1.0), () -> o.intValue() + 1,
            caseOf(o, 2.0), () -> o.intValue() + 2
    );
  }
}
