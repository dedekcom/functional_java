/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag;

import org.ddag.fun.col.FunList;

import static junit.framework.TestCase.assertEquals;
import static org.ddag.fun.func.TailRecursive.tailRec;
import static org.ddag.fun.func.TailRecursive.Return;
import static org.ddag.fun.func.TailRecursive.Continue;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TailRecTest {

  @Test
  public void testRecFun() {
    int s = tailRec(0, FunList.of(1, 2, 3, 4).toSharedList(),
              (sum, col) ->
                    col.isEmpty() ? Return(sum) : Continue(col.head() + sum, col.tail()) );

    assertTrue(10 == s);

    assertTrue(-1 == tailRec(10,  i -> i < 0 ? Return(i) : Continue(i-1) ));
  }

  @Test
  public void testRecFun2() {
    assertEquals( FunList.of("a1", "a2", "a3", "a4"),
            tailRec(FunList.of(), FunList.of(1, 2, 3, 4).toSharedList(), "a",
              (result, col, s) ->
                      col.isEmpty() ? Return(result) : Continue(result.mAdded(s + col.head()), col.tail(), s) ) );
  }

}
