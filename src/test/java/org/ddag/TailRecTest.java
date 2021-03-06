/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag;

import org.ddag.fun.FunString;
import org.ddag.fun.col.FunLinkedList;

import static junit.framework.TestCase.assertEquals;
import static org.ddag.fun.func.TailRecursive.tailRec;
import static org.ddag.fun.func.TailRecursive.Return;
import static org.ddag.fun.func.TailRecursive.Continue;

import org.ddag.fun.col.FunList;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TailRecTest {

  @Test
  public void testRecFun() {
    int s = tailRec(0, FunList.of(1, 2, 3, 4).toUnmodifArrayList(),
              (sum, col) ->
                    col.isEmpty() ? Return(sum) : Continue(col.head() + sum, col.tail()) );

    assertTrue(10 == s);

    assertTrue(-1 == tailRec(10,  i -> i < 0 ? Return(i) : Continue(i-1) ));
  }

  @Test
  public void testRecFun2() {
    assertEquals( FunList.of("a1", "a2", "a3", "a4"),
            tailRec(new FunLinkedList<>(), FunList.of(1, 2, 3, 4).toUnmodifArrayList(), "a",
              (result, col, s) ->
                      col.isEmpty() ? Return(result) : Continue(result.mAdded(s + col.head()), col.tail(), s) ) );
  }

  private int failedFibo = -1;
  private int countFibos;
  @Test
  public void tailRecPerformance() {
    int loops = 100000;
    int n = 100;
    FunString.of("fibo number ", n, " is ", fibo(n)).print();

    Performance.testPerform("tail rec fibonacci", loops, () -> {
      int f = fibo(n);
    } );

    Performance.testPerform("classic rec fibonacci", loops, () -> {
      int f = 0;
      try {
        countFibos = 0;
        f = fiboClassic(n);
      } catch (StackOverflowError sof) {
        if (failedFibo == -1)
          failedFibo = countFibos;
      }
    } );

    if (failedFibo > -1) {
      System.out.println("classic recursive fibo failed on n = " + failedFibo);
    }

  }

  private int fibo(int n) {
    return tailRec(0, 1, n, (first, second, limit) ->
            limit > 0 ? Continue( second, first + second, limit -1) : Return(first)
    );
  }

  private int fiboClassic(int n) { return fiboClassic(0, 1, n); }

  private int fiboClassic(int first, int second, int n) {
      countFibos++;
      return (n > 0) ? fiboClassic(second, first + second, n - 1) : first;
  }
}
