/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag;

import org.ddag.fun.FunString;
import org.ddag.fun.col.FunList;

import static junit.framework.TestCase.assertEquals;
import static org.ddag.fun.func.FunRecFunc.runRec;
import static org.ddag.fun.func.FunRecFunc.Return;
import static org.ddag.fun.func.FunRecFunc.Continue;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class FunRecFunTest {

  @Test
  public void testRecFun() {
    int s = runRec(0, FunList.of(1, 2, 3, 4).toSharedList(),
              (sum, col) ->
                    col.isEmpty() ? Return(sum) : Continue(col.head() + sum, col.tail()) );

    assertTrue(10 == s);
  }

  @Test
  public void testRecFun2() {
    assertEquals( FunList.of("a1", "a2", "a3", "a4"),
            runRec(FunList.of(), FunList.of(1, 2, 3, 4).toSharedList(), "a",
              (result, col, s) ->
                      col.isEmpty() ? Return(result) : Continue(result.mAdded(s + col.head()), col.tail(), s) ) );
  }

}
