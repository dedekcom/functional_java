/*
 *
 * Dominik Dagiel 04.2018
 *
 */

package org.ddag;

import org.ddag.fun.Fumeric;
import org.ddag.fun.col.FunLinkedList;
import org.ddag.fun.col.FunList;
import org.ddag.fun.col.FunUnmodifArrayList;
import org.ddag.fun.col.FunUnmodifLinkedList;
import org.junit.Test;

import static org.ddag.fun.func.TailRecursive.Continue;
import static org.ddag.fun.func.TailRecursive.Return;
import static org.ddag.fun.func.TailRecursive.tailRec;

public class ListsPerformanceTest {

  @Test
  public void testListsPerformance() {
    FunLinkedList<String> src = FunList.ofSize(100000, "").mapWithIndex((e, id) -> Integer.toString(id));

    int loops = 10;
    Performance.testPerform("\n\nPrepare FunList performance test", loops, () -> { ; });
    Performance.testPerform("reversed.sorted LL", loops, () -> { new FunLinkedList<>(src).reversed().sorted(); });
    Performance.testPerform("mReversed.mSortWith LL", loops, () -> { new FunLinkedList<>(src).mReversed().mSortWith((e1, e2) -> e1.compareTo(e2)); });
    Performance.testPerform("reversed.sorted UAL", loops, () -> { new FunUnmodifArrayList<>(src).reversed().sorted(); });
    Performance.testPerform("reversed.sorted ULL", loops, () -> { new FunUnmodifLinkedList<>(src).reversed().sorted(); });

    Performance.testPerform("\nflatten LL", loops, () -> { new FunLinkedList<>(src).flatten(); });
    Performance.testPerform("flatten UAL", loops, () -> { new FunUnmodifArrayList<>(src).flatten(); });
    Performance.testPerform("flatten ULL", loops, () -> { new FunUnmodifLinkedList<>(src).flatten(); });

    Performance.testPerform("\nslice(1/4-3/4) LL", loops, () -> { new FunLinkedList<>(src).slice(src.size()/4,
            3*src.size()/4); });
    Performance.testPerform("slice(1/4-3/4) UAL", loops, () -> { new FunUnmodifArrayList<>(src).slice(src.size()/4,
            3*src.size()/4); });
    Performance.testPerform("slice(1/4-3/4) ULL", loops, () -> { new FunUnmodifLinkedList<>(src).slice(src.size()/4,
            3*src.size()/4); });

    Performance.testPerform("\nzipWithIndex.map.sum", loops, () -> { new FunLinkedList<>(src).zipWithIndex().map(t -> t._2()).sum(); });
    Performance.testPerform("mapWithIndex.sum", loops, () -> { new FunLinkedList<>(src).mapWithIndex((el, id) -> id).sum(); });
    Performance.testPerform("map.foldLeft", loops, () -> {
      new FunLinkedList<>(src).map(s -> Fumeric.getInteger(s).get()).foldLeft(0, (acc, e) -> acc + e);
    });

    Performance.testPerform("\ntail recursive - UAL", loops, () -> {
      tailRec(0, new FunUnmodifArrayList<>(src),
              (sum, col) -> col.isEmpty() ? Return(sum) : Continue(Integer.parseInt(col.head()) + sum, col.tail()) );
    });
    Performance.testPerform("tail recursive - ULL", loops, () -> {
      tailRec(0, new FunUnmodifLinkedList<>(src),
              (sum, col) -> col.isEmpty() ? Return(sum) : Continue(Integer.parseInt(col.head()) + sum, col.tail()) );
    });

  }

}
