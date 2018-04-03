/*
 *
 * Dominik Dagiel 04.2018
 *
 */
package org.ddag;

import static org.ddag.fun.FunList.Nil;
import static org.ddag.fun.match.FunMatch.match;
import static org.ddag.fun.match.FunMatch.caseObject;
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
            caseObject(o, Integer.class), () -> "is int",
            caseObject(o, Any), () -> "any"
    );
    assertEquals (s, "is int");

    FunList<Integer> list = FunList.of(1, 2, 3, 4, 5);
    assertEquals("head::tail",
            match(
              caseObject(list, 5), () -> "5",
              caseObject(list, FunString.class), () -> "fun string",
              caseObject(list, FunList.class, 1, 2, Nil), () -> "1::2::Nil",
              caseObject(list, FunList.class, Any, FunList.class), () -> "head::tail"
            )
    );
  }

  @Test (expected = FunMatchException.class)
  public void testMatchException() {
    Double o = 0.0;
    match(
            caseObject(o, 1.0), () -> o.intValue() + 1,
            caseObject(o, 2.0), () -> o.intValue() + 2
    );
  }
}
